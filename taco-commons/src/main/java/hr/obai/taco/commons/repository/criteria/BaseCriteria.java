package hr.obai.taco.commons.repository.criteria;

import hr.obai.taco.commons.dto.PageableDto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base class for all top level criteria.
 *
 * <p>A {@code XxxCriteria} class is needed to be able to filter a jpa repository.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseCriteria implements Serializable {
  private PageableDto pageableDto = new PageableDto();
}
