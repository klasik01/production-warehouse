package klasik.group.core.production.models;/**
 * @author pc00275
 * @since 09.02.2021
 */

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Item
 *
 * @author pc00275
 * @since 09.02.2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class Item extends AbstractItem{
}
