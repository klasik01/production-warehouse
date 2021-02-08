package klasik.group.commands.api;

import klasik.group.core.configuration.AxonConfig;
import klasik.group.core.configuration.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SecurityConfig.class, AxonConfig.class})
public class CommandsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandsApiApplication.class, args);
    }

}
