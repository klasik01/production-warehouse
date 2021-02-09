package klasik.group.commands.api.user.controllers;

import klasik.group.commands.api.user.commands.RegisterUserCommand;
import klasik.group.commands.api.user.dto.RegisterUserResponse;
import klasik.group.core.dto.BaseResponse;
import klasik.group.core.user.models.MetaInfo;
import klasik.group.core.user.models.User;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/registerUser")
@Slf4j
public class RegisterUserController {
    private final CommandGateway commandGateway;

    @Autowired
    public RegisterUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(value = "/anonymous", method = RequestMethod.GET)
    public ResponseEntity<String> getAnonymous(@AuthenticationPrincipal Jwt jwt) {
        User claims = User.builder()
                .id(jwt.getClaim("preferred_username"))
                .name(jwt.getClaim("name"))
                .firstname(jwt.getClaim("given_name"))
                .lastname(jwt.getClaim("family_name"))
                .email(jwt.getClaim("email"))
                .build();

        return ResponseEntity.ok(String.format("Hello, %s", claims.toString()));
    }

    @GetMapping
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<RegisterUserResponse> registerUser(@AuthenticationPrincipal Jwt jwt, @RequestHeader MultiValueMap<String, String> headers) {
        String id = jwt.getClaim(("preferred_username"));
        //String id = UUID.randomUUID().toString();
        headers.forEach((k, v) -> System.out.println(k+" : "+v));
        try {
            User user = User.builder()
                    .id(id)
                    .name(jwt.getClaim("name"))
                    .firstname(jwt.getClaim("given_name"))
                    .lastname(jwt.getClaim("family_name"))
                    .email(jwt.getClaim("email"))
                    .visibility(true)
                    .build();

            RegisterUserCommand command = RegisterUserCommand.builder()
                    .id(user.getId())
                    .user(user)
                    .meta(MetaInfo.builder()
                            .user(User.builder()
                                    .id(user.getId())
                                    .name(user.getName())
                                    .build())
                            .timestamp("timestamp")
                            .roles(null)
                            .build())
                    .build();

            log.info("RegisterUserCommand " + command.toString());
            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new RegisterUserResponse(id, "User successfully registered!"), HttpStatus.CREATED);

        } catch (CommandExecutionException e) {
            var safeErrorMessage = "User with username " + id + " already exists";
            System.out.println(e.toString());

            return new ResponseEntity<>(new RegisterUserResponse(id, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            var safeErrorMessage = "Error while processing register user request for id - " + id;
            System.out.println(e.toString());

            return new ResponseEntity<>(new RegisterUserResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<BaseResponse> accessDenied(HttpServletRequest request) {
        var safeErrorMessage = "You are not authorized doing this request";
        return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.FORBIDDEN);
    }
}
