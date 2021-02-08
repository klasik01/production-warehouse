package klasik.group.core.production.events;/**
 * @author pc00275
 * @since 06.02.2021
 */

import klasik.group.core.production.models.Project;
import klasik.group.core.production.models.MetaInfo;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * UserRegisteredEvent
 *
 * @author pc00275
 * @since 06.02.2021
 */
@Data
@Builder
public class ProjectCreatedEvent {
    private UUID uuid;
    private Project project;
    private MetaInfo meta;
}
