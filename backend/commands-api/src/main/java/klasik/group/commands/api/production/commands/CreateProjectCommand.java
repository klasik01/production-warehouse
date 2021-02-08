package klasik.group.commands.api.production.commands;/**
 * @author pc00275
 * @since 07.02.2021
 */

import klasik.group.core.production.models.MetaInfo;
import klasik.group.core.production.models.Project;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import java.util.UUID;

/**
 * CreateProjectCommand
 *
 * @author pc00275
 * @since 07.02.2021
 */
@Data
@Builder
public class CreateProjectCommand {
    @TargetAggregateIdentifier
    private UUID uuid;
    private Project project;
    @Valid
    private MetaInfo meta;
}
