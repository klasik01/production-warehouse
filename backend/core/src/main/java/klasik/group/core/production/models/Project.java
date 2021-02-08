package klasik.group.core.production.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Project
 *
 * @author pc00275
 * @since 07.02.2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private UUID uuid;
    private String name;
    private String description;
    private String image;
    private String icon;
}
