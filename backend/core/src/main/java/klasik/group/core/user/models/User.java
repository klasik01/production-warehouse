package klasik.group.core.user.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    @Id
    @NotEmpty(message = "username is mandatory")
    private String id;
    @NotEmpty(message = "name is mandatory")
    private String name;
    @NotEmpty(message = "firstname is mandatory")
    private String firstname;
    @NotEmpty(message = "lastname is mandatory")
    private String lastname;
    @NotEmpty(message = "email is mandatory")
    private String email;
    private boolean visibility;
}
