package hr.obai.commons.model.field;

import lombok.NoArgsConstructor;

/**
 * Wraps a {@link String}.
 *
 * <p>Default constructor wraps {@code null}.
 */
@NoArgsConstructor
public class JsonField extends GenericField<String> {
  public JsonField(String value) {
    super(value);
  }
}
