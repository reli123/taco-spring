package hr.obai.taco.commons.repository.filter;

import java.time.LocalDate;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Filter class for {@link LocalDate} type attributes.
 *
 * @see RangeFilter
 */
@NoArgsConstructor
public class LocalDateFilter extends RangeFilter<LocalDate> {

  private static final long serialVersionUID = 1L;

  public LocalDateFilter(final LocalDateFilter filter) {
    super(filter);
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setEquals(LocalDate equals) {
    super.setEquals(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setNotEquals(LocalDate equals) {
    super.setNotEquals(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setIn(List<LocalDate> in) {
    super.setIn(in);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setNotIn(List<LocalDate> notIn) {
    super.setNotIn(notIn);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setGreaterThan(LocalDate equals) {
    super.setGreaterThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setLessThan(LocalDate equals) {
    super.setLessThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setGreaterThanOrEqual(LocalDate equals) {
    super.setGreaterThanOrEqual(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setLessThanOrEqual(LocalDate equals) {
    super.setLessThanOrEqual(equals);
    return this;
  }
}
