package klasik.group.user.commands;

import klasik.group.user.core.configuration.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SecurityConfig.class})
public class UserCommandsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCommandsApplication.class, args);
    }

}
