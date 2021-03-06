package hr.obai.tacoapplication;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {"hr.obai.tacoapplication.*"}
)
public class JpaConfiguration {

}
