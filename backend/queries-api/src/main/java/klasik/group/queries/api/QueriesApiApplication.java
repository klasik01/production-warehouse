package klasik.group.queries.api;

import klasik.group.core.configuration.AxonConfig;
import klasik.group.core.configuration.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SecurityConfig.class, AxonConfig.class})
public class QueriesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueriesApiApplication.class, args);
    }

}
