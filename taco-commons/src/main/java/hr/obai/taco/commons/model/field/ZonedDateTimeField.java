package hr.obai.taco.commons.model.field;

import java.time.ZonedDateTime;
import lombok.NoArgsConstructor;

/**
 * Wraps a {@link ZonedDateTime}.
 *
 * <p>Default constructor wraps {@code null}.
 */
@NoArgsConstructor
public class ZonedDateTimeField extends GenericField<ZonedDateTime> {
  public ZonedDateTimeField(ZonedDateTime value) {
    super(value);
  }
}
