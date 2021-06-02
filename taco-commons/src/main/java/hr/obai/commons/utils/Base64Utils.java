package hr.obai.commons.utils;

import java.util.Base64;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Base64Utils {

  public static String encode(@NonNull byte[] bytes) {
    return Base64.getEncoder().withoutPadding().encodeToString(bytes);
  }

  public static byte[] decode(@NonNull String str) {
    return Base64.getDecoder().decode(str);
  }
}
