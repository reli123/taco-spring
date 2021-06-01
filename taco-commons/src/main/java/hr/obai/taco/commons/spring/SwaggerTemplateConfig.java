package hr.obai.taco.commons.spring;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

import com.fasterxml.classmate.TypeResolver;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;

/** Swagger Template */
@Slf4j
public abstract class SwaggerTemplateConfig {

  @Autowired private TypeResolver typeResolver;

  protected abstract String title();

  protected abstract String basePackage();

  protected abstract Boolean swaggerEnabled();

  @Bean
  protected Docket apiDocket() {
    StopWatch watch = new StopWatch();
    watch.start();
    val docket =
        new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .forCodeGeneration(true)
            .enable(swaggerEnabled())
            .ignoredParameterTypes(Pageable.class)
            .alternateTypeRules(
                newRule(
                    typeResolver.resolve(List.class, LocalDate.class),
                    typeResolver.resolve(List.class, String.class),
                    Ordered.HIGHEST_PRECEDENCE),
                newRule(
                    typeResolver.resolve(List.class, LocalDateTime.class),
                    typeResolver.resolve(List.class, String.class),
                    Ordered.HIGHEST_PRECEDENCE))
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage()))
            .paths(PathSelectors.any())
            .build();
    watch.stop();
    log.info("Started Swagger in {} ms", watch.getTotalTimeMillis());
    return docket;
  }

  // Setting up api information
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(title())
        .description("Swagger Documentation")
        .version("2.0.0")
        .build();
  }

  protected SwaggerResource swaggerResource(String name, String location, String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }
}
