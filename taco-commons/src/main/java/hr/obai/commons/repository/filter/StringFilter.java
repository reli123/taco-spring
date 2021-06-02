package hr.obai.commons.repository.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for filtering attributes with {@link java.lang.String} type. It can be added to a criteria
 * class as a member, to support the following query parameters: <code>
 * fieldName.equals='something'
 * fieldName.notEquals='something'
 * fieldName.specified=true
 * fieldName.specified=false
 * fieldName.in='something','other'
 * fieldName.notIn='something','other'
 * fieldName.contains='thing'
 * fieldName.doesNotContain='thing'
 * </code>
 */
@Data
@NoArgsConstructor
public class StringFilter extends Filter<String> {

  private static final long serialVersionUID = 1L;

  private String contains;
  private String doesNotContain;
}
