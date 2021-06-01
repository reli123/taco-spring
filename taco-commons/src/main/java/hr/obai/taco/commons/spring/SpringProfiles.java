package hr.obai.taco.commons.spring;

import lombok.experimental.UtilityClass;

/**
 * Exposes the standard profiles for an application: {@value #DEVELOPMENT}, and {@value #PRODUCTION}
 */
@UtilityClass
public final class SpringProfiles {
  public static final String TEST = "test";
  public static final String DEVELOPMENT = "dev";
  public static final String PRODUCTION = "prod";
}
