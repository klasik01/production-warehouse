package klasik.group.commands.api.production.aggregates;

import klasik.group.commands.api.production.commands.CreateProjectCommand;
import klasik.group.core.production.events.ProjectCreatedEvent;
import klasik.group.core.production.models.MetaInfo;
import klasik.group.core.production.models.Project;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

/**
 * ProjectAggregate
 *
 * @author pc00275
 * @since 06.02.2021
 */
@Aggregate
@NoArgsConstructor
public class ProjectAggregate {

    @AggregateIdentifier
    private UUID uuid;
    private Project project;
    private MetaInfo meta;

    @CommandHandler
    public ProjectAggregate(CreateProjectCommand command) {
        var event = ProjectCreatedEvent.builder()
                .uuid(command.getUuid())
                .project(command.getProject())
                .meta(command.getMeta())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ProjectCreatedEvent event) {
        this.uuid = event.getUuid();
        this.project = event.getProject();
        this.meta = event.getMeta();
    }
}
