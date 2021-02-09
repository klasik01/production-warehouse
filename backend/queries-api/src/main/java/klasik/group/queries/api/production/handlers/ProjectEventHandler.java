package klasik.group.queries.api.production.handlers;

import klasik.group.core.production.events.ProjectCreatedEvent;
import klasik.group.core.user.events.UserRegisteredEvent;

/**
 * @author pc00275
 * @since 09.02.2021
 */
public interface ProjectEventHandler {
    void on(ProjectCreatedEvent event);
}
