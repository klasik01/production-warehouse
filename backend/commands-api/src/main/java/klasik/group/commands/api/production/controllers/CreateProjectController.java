package klasik.group.commands.api.production.controllers;

import klasik.group.commands.api.production.commands.CreateProjectCommand;
import klasik.group.core.dto.BaseResponse;
import klasik.group.core.production.models.MetaInfo;
import klasik.group.core.production.models.Project;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/project")
public class CreateProjectController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreateProjectController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseResponse> createProject(@AuthenticationPrincipal Jwt jwt) {
        String user = jwt.getClaim(("preferred_username"));

        UUID uuid = UUID.randomUUID();
        try {
            CreateProjectCommand command = CreateProjectCommand.builder()
                    .uuid(uuid)
                    .project(Project.builder()
                            .uuid(uuid)
                            .name("New project")
                            .icon("hammer")
                            .build()
                    )
                    .meta(MetaInfo.builder()
                            .user(user)
                            .timestamp("timestamp")
                            .build())
                    .build();
            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new BaseResponse("Project successfully registered!"), HttpStatus.CREATED);

        } catch (CommandExecutionException e) {
            System.out.println(e.toString());

            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            var safeErrorMessage = "Error while processing create project";
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
