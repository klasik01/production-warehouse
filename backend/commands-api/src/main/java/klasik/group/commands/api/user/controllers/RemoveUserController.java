package klasik.group.commands.api.user.controllers;

import klasik.group.commands.api.user.commands.RemoveUserCommand;
import klasik.group.commands.api.user.dto.RegisterUserResponse;
import klasik.group.core.dto.BaseResponse;
import klasik.group.core.user.models.MetaInfo;
import klasik.group.core.user.models.User;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1/removeUser")
public class RemoveUserController {
    private final CommandGateway commandGateway;

    @Autowired
    public RemoveUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseResponse> removeUser(@AuthenticationPrincipal Jwt jwt, @PathVariable(value = "id") String id) {
        try {
            User user = User.builder()
                    .id(jwt.getClaim("preferred_username"))
                    .name(jwt.getClaim("name"))
                    .build();

            commandGateway.sendAndWait(RemoveUserCommand.builder()
                    .id(id)
                    .meta(MetaInfo.builder()
                            .user(User.builder()
                                    .id(user.getId())
                                    .name(user.getName())
                                    .build())
                            .timestamp("timestamp")
                            .roles(null)
                            .build())
                    .build()
            );

            return new ResponseEntity<>(new BaseResponse("User successfully removed!"), HttpStatus.OK);
        } catch (CommandExecutionException e) {
            var safeErrorMessage = "User with username " + id + " does not exist";
            System.out.println(e.toString());

            return new ResponseEntity<>(new RegisterUserResponse(id, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            var safeErrorMessage = "Error while processing remove user request for id - " + id;
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
