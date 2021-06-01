package hr.obai.taco.commons.model.field;

import lombok.NoArgsConstructor;

/**
 * Wraps a {@link String}.
 *
 * <p>Default constructor wraps {@code null}.
 */
@NoArgsConstructor
public class StringField extends GenericField<String> {
  public StringField(String value) {
    super(value);
  }
}
