package klasik.group.commands.api.user.controllers;

import klasik.group.commands.api.user.commands.UpdateUserCommand;
import klasik.group.core.dto.BaseResponse;
import klasik.group.core.user.models.MetaInfo;
import klasik.group.core.user.models.User;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/updateUser")
@Slf4j
public class UpdateUserController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdateUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseResponse> updateUser(@AuthenticationPrincipal Jwt jwt,
            @PathVariable(value = "id") String id) {
        try {
            User user = User.builder()
                    .id(jwt.getClaim("preferred_username"))
                    .name(jwt.getClaim("name"))
                    .build();

            UpdateUserCommand command = UpdateUserCommand.builder()
                    .id(id)
                    .user(User.builder()
                            .firstname("František")
                            .lastname("Koubek")
                            .email("email@mail.com")
                            .build()
                    )
                    .meta(MetaInfo.builder()
                            .user(User.builder()
                                    .id(user.getId())
                                    .name(user.getName())
                                    .build())
                            .timestamp("timestamp")
                            .roles(null)
                            .build())
                    .build();

            log.info("UpdateUserCommand " + command.toString());

            command.setId(id);
            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new BaseResponse("User successfully updated!"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing update user request for id - " + id;
            System.out.println(e.toString());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<BaseResponse> accessDenied(HttpServletRequest request) {
        var safeErrorMessage = "You are not authorized doing this request";
        return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.FORBIDDEN);
    }
}
