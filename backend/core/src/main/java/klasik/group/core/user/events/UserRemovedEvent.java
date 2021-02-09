package klasik.group.core.user.events;

import klasik.group.core.user.models.MetaInfo;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.serialization.Revision;

@Data
@Builder
@Revision("1")
public class UserRemovedEvent {
    @TargetAggregateIdentifier
    private String id;
    private MetaInfo meta;
}
