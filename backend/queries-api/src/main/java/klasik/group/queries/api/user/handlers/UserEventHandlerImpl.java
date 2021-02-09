package klasik.group.queries.api.user.handlers;

import klasik.group.core.user.events.UserRegisteredEvent;
import klasik.group.core.user.events.UserRemovedEvent;
import klasik.group.core.user.events.UserUpdatedEvent;
import klasik.group.queries.api.user.repositories.UserRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserEventHandlerImpl
 *
 * @author pc00275
 * @since 06.02.2021
 */
@Component
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;

    @Autowired
    public UserEventHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    @Override
    public void on(UserRegisteredEvent event) {
        System.out.println(event.getMeta().toString());
        userRepository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserUpdatedEvent event) {
        userRepository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserRemovedEvent event) {
        userRepository.deleteById(event.getId());
    }
}
