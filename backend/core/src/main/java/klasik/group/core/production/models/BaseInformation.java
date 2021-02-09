package klasik.group.core.production.models;/**
 * @author pc00275
 * @since 09.02.2021
 */

import lombok.Builder;
import lombok.Data;

/**
 * BaseInformation
 *
 * @author pc00275
 * @since 09.02.2021
 */
@Data
@Builder
public class BaseInformation {

    private String name;
    private String description;

}
