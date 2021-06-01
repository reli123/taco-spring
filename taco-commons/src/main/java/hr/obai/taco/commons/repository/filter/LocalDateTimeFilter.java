package hr.obai.taco.commons.repository.filter;

import java.time.LocalDateTime;
import java.util.List;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Filter class for {@link LocalDateTime} type attributes.
 *
 * @see RangeFilter
 */
@NoArgsConstructor
public class LocalDateTimeFilter extends RangeFilter<LocalDateTime> {

  public LocalDateTimeFilter(LocalDateTimeFilter filter) {
    super(filter);
  }

  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public LocalDateTimeFilter setEquals(LocalDateTime equals) {
    super.setEquals(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public LocalDateTimeFilter setNotEquals(LocalDateTime equals) {
    super.setNotEquals(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public LocalDateTimeFilter setIn(List<LocalDateTime> in) {
    super.setIn(in);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public LocalDateTimeFilter setNotIn(List<LocalDateTime> notIn) {
    super.setNotIn(notIn);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public LocalDateTimeFilter setGreaterThan(LocalDateTime equals) {
    super.setGreaterThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public LocalDateTimeFilter setLessThan(LocalDateTime equals) {
    super.setLessThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public LocalDateTimeFilter setGreaterThanOrEqual(LocalDateTime equals) {
    super.setGreaterThanOrEqual(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public LocalDateTimeFilter setLessThanOrEqual(LocalDateTime equals) {
    super.setLessThanOrEqual(equals);
    return this;
  }
}
