package hr.obai.ingredientdomain.repository;

import hr.obai.taco.commons.repository.criteria.CriteriaEnhancedSpecificationBuilderTemplate;
import org.springframework.data.jpa.domain.Specification;

public class IngredientSpecification extends CriteriaEnhancedSpecificationBuilderTemplate<Ingredient, IngredientCriteria> {

    @Override
    public Specification<Ingredient> buildSpecification(IngredientCriteria criteria) {
        return null;
    }
}
