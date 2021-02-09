package klasik.group.queries.api.production.repositories;

import klasik.group.core.production.models.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author pc00275
 * @since 09.02.2021
 */
public interface ProjectRepository extends MongoRepository<Project, String> {
}
