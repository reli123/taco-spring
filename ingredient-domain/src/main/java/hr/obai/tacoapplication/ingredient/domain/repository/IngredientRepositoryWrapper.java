package hr.obai.tacoapplication.ingredient.domain.repository;

import hr.obai.commons.repository.CrudRepositoryWrapperTemplate;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

@Repository
public class IngredientRepositoryWrapper extends CrudRepositoryWrapperTemplate<
        Ingredient,
        IngredientRepository,
        IngredientCriteria
    > {

    @Autowired
    protected IngredientRepositoryWrapper(
            @NonNull IngredientRepository repository,
            IngredientSpecification specificationBuilder,
            @NonNull JpaContext jpaContext
    ) {
        super(repository, specificationBuilder, jpaContext);
    }
}
