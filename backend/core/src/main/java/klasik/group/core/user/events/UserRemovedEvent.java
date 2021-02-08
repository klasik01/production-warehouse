package klasik.group.core.user.events;

import klasik.group.core.user.models.MetaInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRemovedEvent {
    private String id;
    private MetaInfo meta;
}
