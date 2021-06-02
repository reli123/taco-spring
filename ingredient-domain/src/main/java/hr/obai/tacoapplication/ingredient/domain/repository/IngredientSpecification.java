package hr.obai.tacoapplication.ingredient.domain.repository;

import hr.obai.commons.repository.criteria.CriteriaEnhancedSpecificationBuilderTemplate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IngredientSpecification extends CriteriaEnhancedSpecificationBuilderTemplate<Ingredient, IngredientCriteria> {

    @Override
    public Specification<Ingredient> buildSpecification(IngredientCriteria criteria) {
        return buildSpecification();
    }
}
