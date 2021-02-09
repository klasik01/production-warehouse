package klasik.group.core.production.models;/**
 * @author pc00275
 * @since 09.02.2021
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Attribute
 *
 * @author pc00275
 * @since 09.02.2021
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class AbstractAttribute {
    protected UUID uuid;
    protected String name;

}
