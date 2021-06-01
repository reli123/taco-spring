package hr.obai.taco.commons.model.field;

import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Wraps a {@link LongField}.
 *
 * <p>Default constructor wraps {@code null}.
 */
@NoArgsConstructor
public class LongField extends GenericField<Long> {
  public LongField(@NonNull Long value) {
    super(value);
  }
}
