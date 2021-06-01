package hr.obai.taco.commons.model.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

/**
 * Superclass for all custom fields needed by models.
 *
 * <p>Please don't create instances of this class: see subclasses for details.
 *
 * @see LongField
 * @see NumericField
 * @see StringField
 * @see LocalDateTimeField
 * @see ZonedDateTimeField
 * @see EnumField
 */
@Data
public abstract class GenericField<T> {
  /** The value wrapped by this field. */
  @JsonProperty protected T value;

  @JsonProperty protected boolean valid;

  @JsonProperty protected String message;

  GenericField() {
    this(null);
  }

  GenericField(T value) {
    this(value, true, "");
  }

  GenericField(T value, boolean valid, @NonNull String message) {
    this.value = value;
    this.valid = valid;
    this.message = message;
  }

  public static <T> T toValue(GenericField<T> field) {
    return field == null ? null : field.getValue();
  }

  public void invalidate() {
    this.valid = false;
  }
}
