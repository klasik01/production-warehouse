package klasik.group.core.production.models;/**
 * @author pc00275
 * @since 09.02.2021
 */

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Photo
 *
 * @author pc00275
 * @since 09.02.2021
 */
@Data
@Builder
public class Photo {

    private UUID uuid;
    private String type;
    private String url;
    private String name;
}
