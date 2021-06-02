package hr.obai.commons.spring;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class SecurityTemplateConfig {

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(ImmutableList.of("*"));
    config.setAllowedHeaders(ImmutableList.of("*"));
    config.setAllowedMethods(ImmutableList.of("POST", "GET", "DELETE", "PUT", "OPTIONS", "PATCH"));
    config.setExposedHeaders(
        ImmutableList.of(
            "X-Current-Page, X-Num-Current-Page-Elements, X-Num-Total-Pages, X-Num-Total-Elements, X-Request-Outcome",
            "Content-Type",
            "api_key",
            "Authorization"));
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
