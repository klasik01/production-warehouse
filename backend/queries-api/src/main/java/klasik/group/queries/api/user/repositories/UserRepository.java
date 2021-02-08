package klasik.group.queries.api.user.repositories;

import klasik.group.core.user.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author pc00275
 * @since 06.02.2021
 */
public interface UserRepository extends MongoRepository<User, String> {

}
