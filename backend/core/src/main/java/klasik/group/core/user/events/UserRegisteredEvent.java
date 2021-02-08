package klasik.group.core.user.events;/**
 * @author pc00275
 * @since 06.02.2021
 */

import klasik.group.core.user.models.MetaInfo;
import klasik.group.core.user.models.User;
import lombok.Builder;
import lombok.Data;

/**
 * UserRegisteredEvent
 *
 * @author pc00275
 * @since 06.02.2021
 */
@Data
@Builder
public class UserRegisteredEvent {
    private String id;
    private User user;
    private MetaInfo meta;
}
