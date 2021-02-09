package klasik.group.core.production.models;/**
 * @author pc00275
 * @since 09.02.2021
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

/**
 * AbstractItem
 *
 * @author pc00275
 * @since 09.02.2021
 */
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractItem {
    @Id
    protected UUID uuid;
    protected BaseInformation baseInformation;
    protected List<NumberAttribute> numAttributes;
    protected List<StringAttribute> stringAttributes;
    protected List<Photo> photos;
}
