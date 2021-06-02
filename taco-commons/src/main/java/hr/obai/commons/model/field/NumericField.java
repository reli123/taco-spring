package hr.obai.commons.model.field;

import java.math.BigDecimal;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Wraps a {@link BigDecimal}.
 *
 * <p>Default constructor wraps {@code null}.
 */
@NoArgsConstructor
public class NumericField extends GenericField<BigDecimal> {
  public NumericField(@NonNull String value) {
    this(new BigDecimal(value));
  }

  public NumericField(@NonNull Integer value) {
    this(new BigDecimal(value));
  }

  public NumericField(BigDecimal value) {
    super(value);
  }
}
