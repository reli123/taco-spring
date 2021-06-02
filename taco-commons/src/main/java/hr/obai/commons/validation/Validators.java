package hr.obai.commons.validation;

import hr.obai.commons.model.field.GenericField;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public final class Validators {

  private static final String REQUIRED = "required";

  public static boolean invalidateRequiredField(@NonNull GenericField<?> field) {
    return invalidate(field, REQUIRED);
  }

  public static boolean invalidate(@NonNull GenericField<?> field, @NonNull String message) {
    field.invalidate();
    field.setMessage(message);
    log.warn(message);
    return false;
  }
}
