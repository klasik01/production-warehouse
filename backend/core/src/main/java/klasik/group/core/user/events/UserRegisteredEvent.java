package klasik.group.core.user.events;/**
 * @author pc00275
 * @since 06.02.2021
 */

import klasik.group.core.user.models.MetaInfo;
import klasik.group.core.user.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.serialization.Revision;

/**
 * UserRegisteredEvent
 *
 * @author pc00275
 * @since 06.02.2021
 */
@Data
@Builder
@Revision("1")
public class UserRegisteredEvent {
    @TargetAggregateIdentifier
    private String id;
    private User user;
    private MetaInfo meta;
}
