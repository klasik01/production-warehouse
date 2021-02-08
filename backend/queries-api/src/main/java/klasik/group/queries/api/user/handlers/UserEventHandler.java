package klasik.group.queries.api.user.handlers;

import klasik.group.core.user.events.UserRegisteredEvent;
import klasik.group.core.user.events.UserRemovedEvent;

/**
 * @author pc00275
 * @since 06.02.2021
 */
public interface UserEventHandler {

    void on(UserRegisteredEvent event);
    void on(UserRemovedEvent event);

}
