package hr.obai.tacoapplication.ingredient.domain.repository;

import hr.obai.commons.repository.criteria.CriteriaEnhancedSpecificationBuilderTemplate;
import hr.obai.commons.repository.criteria.CriteriaUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IngredientSpecification extends CriteriaEnhancedSpecificationBuilderTemplate<Ingredient, IngredientCriteria> {

    @Override
    public Specification<Ingredient> buildSpecification(IngredientCriteria criteria) {
        List<Specification<Ingredient>> specifications = new ArrayList<>();
        specifications.add(buildSpecification());
        specifications.add(buildSpecification(criteria.getName(), Ingredient_.name));
        specifications.add(buildSpecification(criteria.getId(), Ingredient_.id));
        return specifications.stream().reduce(Specification::and).orElse(buildSpecification());
    }
}
