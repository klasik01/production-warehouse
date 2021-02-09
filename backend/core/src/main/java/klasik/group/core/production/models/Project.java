package klasik.group.core.production.models;

import klasik.group.core.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Project
 *
 * @author pc00275
 * @since 07.02.2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Document(collection = "projects")
public class Project extends AbstractItem {
    private HashMap<UUID, Integer> requirements;
    private List<String> users;
    private List<String> administrators;
}
