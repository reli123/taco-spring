package hr.obai.taco.commons.repository.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Wraps both standard criteria (based on front-end filters)
 *
 * @see BaseCriteria
 */
@Getter
@AllArgsConstructor
public class CriteriaWrapper<CRITERIA extends BaseCriteria> {
  private final CRITERIA standardCriteria;
}
