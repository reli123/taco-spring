package hr.obai.commons.repository.filter;

import hr.obai.commons.enums.DateRangeOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.chrono.ChronoLocalDateTime;

/**
 * Filter class for Comparable types, where less than / greater than / etc relations could be
 * interpreted. It can be added to a criteria class as a member, to support the following query
 * parameters:
 *
 * <pre>
 *      fieldName.equals=42
 *      fieldName.specified=true
 *      fieldName.specified=false
 *      fieldName.in=43,42
 *      fieldName.greaterThan=41
 *      fieldName.lessThan=44
 *      fieldName.greaterOrEqualThan=42
 *      fieldName.lessOrEqualThan=44
 * </pre>
 *
 * Due to problems with the type conversions, the descendant classes should be used, where the
 * generic type parameter is materialized.
 *
 * @param <FIELD_TYPE> the type of filter.
 * @see IntegerFilter
 * @see DoubleFilter
 * @see LongFilter
 * @see LocalDateFilter
 */
@ToString
public class DateRangeFilter<FIELD_TYPE extends Comparable<? super FIELD_TYPE>>
    extends Filter<FIELD_TYPE> {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  private boolean isClear;

  @Getter private FIELD_TYPE from;
  @Getter private FIELD_TYPE to;

  @Getter private DateRangeOperation operation;

  public DateRangeFilter() {
    operation = DateRangeOperation.ALL;
  }

  public DateRangeFilter(DateRangeFilter<FIELD_TYPE> filter) {
    this(filter, DateRangeOperation.ALL);
  }

  public DateRangeFilter(DateRangeFilter<FIELD_TYPE> filter, DateRangeOperation operation) {
    super(filter);
    from = filter.from;
    to = filter.to;
    this.operation = operation;
  }

  public DateRangeFilter(RangeFilter<FIELD_TYPE> filter, DateRangeOperation operation) {
    super(filter);
    from = filter.getFrom();
    to = filter.getTo();
    this.operation = operation;
  }

  public Filter<FIELD_TYPE> setFrom(FIELD_TYPE from) {
    this.from = from;
    return this;
  }

  public Filter<FIELD_TYPE> setTo(FIELD_TYPE to) {
    this.to = to;
    return this;
  }

  public Filter<FIELD_TYPE> setFromTo(FIELD_TYPE from, FIELD_TYPE to) {
    this.from = from;
    this.to = to;
    return this;
  }

  public static DateRangeFilter getOperation(DateRangeFilter filter, DateRangeOperation operation) {
    return new DateRangeFilter(filter, operation);
  }

  public static DateRangeFilter getOperation(RangeFilter filter, DateRangeOperation operation) {
    return new DateRangeFilter(filter, operation);
  }

  public ChronoLocalDateTime getFrom() {
    return (ChronoLocalDateTime) from;
  }

  public ChronoLocalDateTime getTo() {
    return (ChronoLocalDateTime) to;
  }
}
