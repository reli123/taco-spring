package hr.obai.tacoapplication.ingredient.domain.repository;

import hr.obai.commons.repository.criteria.BaseCriteria;
import hr.obai.commons.repository.filter.LongFilter;
import hr.obai.commons.repository.filter.StringFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientCriteria extends BaseCriteria {
    private LongFilter id;
    private StringFilter name;
}
