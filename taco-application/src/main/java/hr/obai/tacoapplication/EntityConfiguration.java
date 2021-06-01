package hr.obai.tacoapplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@Configuration
@EntityScan(basePackages = "hr.obai.taco.domain")
public class EntityConfiguration {
}
