package hr.obai.taco.commons.model.field;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

/**
 * Wraps a {@link LocalDateTime}.
 *
 * <p>Default constructor wraps {@code null}.
 */
@NoArgsConstructor
public class LocalDateTimeField extends GenericField<LocalDateTime> {
  public LocalDateTimeField(LocalDateTime value) {
    super(value);
  }
}
