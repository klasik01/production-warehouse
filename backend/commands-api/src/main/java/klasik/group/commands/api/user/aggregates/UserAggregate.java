package klasik.group.commands.api.user.aggregates;

import klasik.group.commands.api.user.commands.RegisterUserCommand;
import klasik.group.commands.api.user.commands.RemoveUserCommand;
import klasik.group.core.user.events.UserRegisteredEvent;
import klasik.group.core.user.events.UserRemovedEvent;
import klasik.group.core.user.models.MetaInfo;
import klasik.group.core.user.models.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

/**
 * UserAggregate
 *
 * @author pc00275
 * @since 06.02.2021
 */
@Aggregate
@NoArgsConstructor
@Slf4j
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;
    private MetaInfo meta;

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public void on(RegisterUserCommand command) {
        log.info("RegisterUserCommand {} for aggregate {}", command.getClass().getSimpleName(), command.getId());
        var event = UserRegisteredEvent.builder()
                .id(command.getId())
                .user(command.getUser())
                .meta(command.getMeta())
                .build();
        apply(event);
    }

    @CommandHandler
    public void handle(RemoveUserCommand command) {
        var event = UserRemovedEvent.builder()
                .id(command.getId())
                .meta(command.getMeta())
                .build();

        apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event) {
        this.id = event.getId();
        this.user = event.getUser();
        this.meta = event.getMeta();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event) {
        markDeleted();
    }
}
