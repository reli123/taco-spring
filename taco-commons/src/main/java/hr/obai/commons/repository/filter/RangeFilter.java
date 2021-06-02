package hr.obai.commons.repository.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public abstract class RangeFilter<FIELD_TYPE extends Comparable<? super FIELD_TYPE>>
    extends Filter<FIELD_TYPE> {

  private static final long serialVersionUID = 1L;

  private FIELD_TYPE greaterThan;
  private FIELD_TYPE lessThan;
  private FIELD_TYPE greaterThanOrEqual;
  private FIELD_TYPE lessThanOrEqual;

  @Getter private FIELD_TYPE from;
  @Getter private FIELD_TYPE to;

  public RangeFilter(RangeFilter<FIELD_TYPE> filter) {
    super(filter);
    this.greaterThan = filter.greaterThan;
    this.lessThan = filter.lessThan;
    this.greaterThanOrEqual = filter.greaterThanOrEqual;
    this.lessThanOrEqual = filter.lessThanOrEqual;
  }

  /**
   * Getter for the field <code>greaterThan</code>.
   *
   * @return a FIELD_TYPE object.
   */
  public FIELD_TYPE getGreaterThan() {
    return greaterThan;
  }

  public RangeFilter<FIELD_TYPE> setGreaterThan(FIELD_TYPE greaterThan) {
    this.greaterThan = greaterThan;
    return this;
  }

  public FIELD_TYPE getLessThan() {
    return lessThan;
  }

  public RangeFilter<FIELD_TYPE> setLessThan(FIELD_TYPE lessThan) {
    this.lessThan = lessThan;
    return this;
  }

  public FIELD_TYPE getGreaterThanOrEqual() {
    return greaterThanOrEqual;
  }

  public RangeFilter<FIELD_TYPE> setGreaterThanOrEqual(FIELD_TYPE greaterThanOrEqual) {
    this.greaterThanOrEqual = greaterThanOrEqual;
    return this;
  }

  public FIELD_TYPE getLessThanOrEqual() {
    return lessThanOrEqual;
  }

  public RangeFilter<FIELD_TYPE> setLessThanOrEqual(FIELD_TYPE lessThanOrEqual) {
    this.lessThanOrEqual = lessThanOrEqual;
    return this;
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

  public boolean isDateRange() {

    return from != null && to != null;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return getFilterName()
        + " ["
        + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
        + (getNotEquals() != null ? "notEquals=" + getNotEquals() + ", " : "")
        + (getSpecified() != null ? "specified=" + getSpecified() + ", " : "")
        + (getIn() != null ? "in=" + getIn() + ", " : "")
        + (getNotIn() != null ? "notIn=" + getNotIn() + ", " : "")
        + (getGreaterThan() != null ? "greaterThan=" + getGreaterThan() + ", " : "")
        + (getLessThan() != null ? "lessThan=" + getLessThan() + ", " : "")
        + (getGreaterThanOrEqual() != null
            ? "greaterThanOrEqual=" + getGreaterThanOrEqual() + ", "
            : "")
        + (getLessThanOrEqual() != null ? "lessThanOrEqual=" + getLessThanOrEqual() : "")
        + "]";
  }
}
