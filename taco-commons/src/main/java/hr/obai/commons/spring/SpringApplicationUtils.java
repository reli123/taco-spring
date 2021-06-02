package hr.obai.commons.spring;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

/** Utility for {@link SpringApplication} and spring profiles. */
@CommonsLog
@UtilityClass
@Slf4j
public final class SpringApplicationUtils {
  private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

  /**
   * Set a default to use when no profile is configured.
   *
   * @param application the Spring application
   */
  public static void addDefaultProfile(@NonNull SpringApplication application) {
    application.setDefaultProperties(
        newHashMap(SPRING_PROFILE_DEFAULT, SpringProfiles.DEVELOPMENT));
  }

  public static void validateEnvironmentConfiguration(@NonNull Environment environment) {
    val activeProfiles = new ArrayList<>(Arrays.asList(environment.getActiveProfiles()));

    if (activeProfiles.contains(SpringProfiles.DEVELOPMENT)
        && activeProfiles.contains(SpringProfiles.PRODUCTION)) {
      String message =
          MessageFormat.format(
              "You have misconfigured your application! It should not run with both the '{0}' and '{1}' profiles at the same time.",
              SpringProfiles.DEVELOPMENT, SpringProfiles.PRODUCTION);
      throw new IllegalStateException(message);
    }
  }

  public static void showApplicationEnvironmentDetails(@NonNull Environment environment)
      throws UnknownHostException {
    String message =
        MessageFormat.format(
            "\n----------------------------------------------------------\n"
                + "\tApplication: {0} is running! Access URLS:\n"
                + "\tLocal:       http(s)://localhost:{1}\n"
                + "\tExternal:    http(s)://{2}:{1}\n"
                + "\tProfile(s):  {3}"
                + "\n----------------------------------------------------------\n",
            environment.getProperty("spring.application.name"),
            environment.getProperty("server.port"),
            InetAddress.getLocalHost().getHostAddress(),
            getActiveProfiles(environment));
    log.info(message);
  }

  private static List<String> getActiveProfiles(Environment environment) {
    String[] profiles = environment.getActiveProfiles();
    if (ArrayUtils.isEmpty(profiles)) {
      profiles = environment.getDefaultProfiles();
    }
    return Arrays.asList(profiles);
  }

  private static <K, V> HashMap newHashMap(K key, V value) {
    val map = new HashMap<K, V>();
    map.put(key, value);
    return map;
  }
}
