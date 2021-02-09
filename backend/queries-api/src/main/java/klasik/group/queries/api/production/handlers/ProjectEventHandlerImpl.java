package klasik.group.queries.api.production.handlers;/**
 * @author pc00275
 * @since 09.02.2021
 */

import klasik.group.core.production.events.ProjectCreatedEvent;
import klasik.group.queries.api.production.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ProjectEventHandlerImpl
 *
 * @author pc00275
 * @since 09.02.2021
 */
@Component
@Slf4j
public class ProjectEventHandlerImpl implements ProjectEventHandler {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectEventHandlerImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @EventHandler
    @Override
    public void on(ProjectCreatedEvent event) {
        log.info("ListenerHandler {} for aggregate {}", event.getClass().getSimpleName(), event.getUuid());
        projectRepository.save(event.getProject());
    }
}
