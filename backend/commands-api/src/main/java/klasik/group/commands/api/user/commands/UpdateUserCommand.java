package klasik.group.commands.api.user.commands;

import klasik.group.core.user.models.MetaInfo;
import klasik.group.core.user.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private String id;
    @NotNull(message = "no user details were supplied")
    @Valid
    private User user;
    @NotNull(message = "no meta info filled")
    @Valid
    private MetaInfo meta;
}
