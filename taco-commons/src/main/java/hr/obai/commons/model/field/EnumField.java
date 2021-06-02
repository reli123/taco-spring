package hr.obai.commons.model.field;

import lombok.NoArgsConstructor;

/**
 * Wraps a {@link Enum}.
 *
 * <p>Default constructor wraps {@code null}.
 */
@NoArgsConstructor
public class EnumField<E extends Enum<E>> extends GenericField<E> {
  public EnumField(E value) {
    super(value);
  }
}
