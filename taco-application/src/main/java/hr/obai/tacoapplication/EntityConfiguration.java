package hr.obai.tacoapplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"hr.obai.tacoapplication.*"})
public class EntityConfiguration {
}
