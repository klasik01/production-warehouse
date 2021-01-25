package klasik.group.user.core.models;/**
 * @author pc00275
 * @since 23.01.2021
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

/**
 * UserClaims
 *
 * @author pc00275
 * @since 23.01.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class UserClaims {

    @Id
    @NotEmpty(message = "username is mandatory")
    private String uid;
    @NotEmpty(message = "name is mandatory")
    private String name;
    @NotEmpty(message = "firstname is mandatory")
    private String firstname;
    @NotEmpty(message = "lastname is mandatory")
    private String lastname;
    @NotEmpty(message = "email is mandatory")
    private String email;
    private Collection<GrantedAuthority> roles;
}
