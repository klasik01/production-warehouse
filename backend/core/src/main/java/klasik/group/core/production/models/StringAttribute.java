package klasik.group.core.production.models;/**
 * @author pc00275
 * @since 09.02.2021
 */

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * StringAttribute
 *
 * @author pc00275
 * @since 09.02.2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class StringAttribute extends AbstractAttribute {
    private String value;
}
