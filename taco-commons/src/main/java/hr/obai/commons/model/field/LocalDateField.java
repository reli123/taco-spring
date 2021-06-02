package hr.obai.commons.model.field;

import java.time.LocalDate;
import lombok.NoArgsConstructor;

/**
 * Wraps a {@link LocalDate}.
 *
 * <p>Default constructor wraps {@code null}.
 */
@NoArgsConstructor
public class LocalDateField extends GenericField<LocalDate> {
  public LocalDateField(LocalDate value) {
    super(value);
  }
}
