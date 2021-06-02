package hr.obai.commons.model.field;

import java.math.BigDecimal;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

@UtilityClass
public final class FieldUtils {
  public static boolean isNotNull(GenericField<?> field) {
    return !isNull(field);
  }

  /** @return {@code true} iff field's <b>value</b> is {@code null} */
  public static boolean isNull(GenericField<?> field) {
    return field.getValue() == null;
  }

  public static boolean isNotBlank(StringField field) {
    return !isBlank(field);
  }

  public static boolean isBlank(StringField field) {
    return StringUtils.isBlank(field.getValue());
  }

  public static boolean isInRange(
      @NonNull NumericField field, @NonNull BigDecimal minValue, @NonNull BigDecimal maxValue) {
    Validate.isTrue(minValue.compareTo(maxValue) < 0, "Invalid range: must be minValue < maxValue");
    return field.getValue().compareTo(minValue) >= 0 && field.getValue().compareTo(maxValue) <= 0;
  }

  public static boolean isInRange(
      @NonNull LongField field, @NonNull Long minValue, @NonNull Long maxValue) {
    Validate.isTrue(minValue.compareTo(maxValue) < 0, "Invalid range: must be minValue < maxValue");
    Long value = field.getValue();
    return minValue <= value && value <= maxValue;
  }
}
