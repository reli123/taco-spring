/*
 * Copyright 2016-2020 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see https://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hr.obai.taco.commons.repository.filter;

import hr.obai.taco.commons.enums.LogicalOperator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Base class for the various attribute filters. It can be added to a criteria class as a member, to
 * support the following query parameters:
 *
 * <pre>
 *      fieldName.equals='something'
 *      fieldName.notEquals='somethingElse'
 *      fieldName.specified=true
 *      fieldName.specified=false
 *      fieldName.in='something','other'
 *      fieldName.notIn='something','other'
 * </pre>
 */
@NoArgsConstructor
public class Filter<FIELD_TYPE> implements Serializable {

  private static final long serialVersionUID = 1L;

  private FIELD_TYPE equals;
  private FIELD_TYPE notEquals;
  private Boolean specified;
  private List<FIELD_TYPE> in;
  private List<FIELD_TYPE> notIn;

  @Getter @Setter private LogicalOperator op = LogicalOperator.AND;

  public Filter(Filter<FIELD_TYPE> filter) {
    this.equals = filter.equals;
    this.notEquals = filter.notEquals;
    this.specified = filter.specified;
    this.in = filter.in == null ? null : new ArrayList<>(filter.in);
    this.notIn = filter.notIn == null ? null : new ArrayList<>(filter.notIn);
  }

  public FIELD_TYPE getEquals() {
    return equals;
  }

  public Filter<FIELD_TYPE> setEquals(FIELD_TYPE equals) {
    this.equals = equals;
    return this;
  }

  public FIELD_TYPE getNotEquals() {
    return notEquals;
  }

  public Filter<FIELD_TYPE> setNotEquals(FIELD_TYPE notEquals) {
    this.notEquals = notEquals;
    return this;
  }

  public Boolean getSpecified() {
    return specified;
  }

  public Filter<FIELD_TYPE> setSpecified(Boolean specified) {
    this.specified = specified;
    return this;
  }

  public List<FIELD_TYPE> getIn() {
    return in;
  }

  public Filter<FIELD_TYPE> setIn(List<FIELD_TYPE> in) {
    this.in = in;
    return this;
  }

  public List<FIELD_TYPE> getNotIn() {
    return notIn;
  }

  public Filter<FIELD_TYPE> setNotIn(List<FIELD_TYPE> notIn) {
    this.notIn = notIn;
    return this;
  }

  @Override
  public String toString() {
    return getFilterName()
        + " ["
        + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
        + (getNotEquals() != null ? "notEquals=" + getNotEquals() + ", " : "")
        + (getSpecified() != null ? "specified=" + getSpecified() + ", " : "")
        + (getIn() != null ? "in=" + getIn() + ", " : "")
        + (getNotIn() != null ? "notIn=" + getNotIn() : "")
        + "]";
  }

  protected String getFilterName() {
    return this.getClass().getSimpleName();
  }
}
