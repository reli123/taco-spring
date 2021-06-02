package hr.obai.commons.model.field;

import lombok.NoArgsConstructor;

/**
 * Wraps a {@link IntegerField}.
 *
 * <p>Default constructor wraps {@code null}.
 */
@NoArgsConstructor
public class IntegerField extends GenericField<Integer> {
  public IntegerField(Integer value) {
    super(value);
  }
}
