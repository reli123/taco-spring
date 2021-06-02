package hr.obai.tacoapplication.ingredient.domain.service;

import hr.obai.tacoapplication.ingredient.domain.repository.Ingredient;
import hr.obai.tacoapplication.ingredient.domain.repository.IngredientCriteria;
import hr.obai.tacoapplication.ingredient.domain.repository.IngredientRepository;
import hr.obai.tacoapplication.ingredient.domain.repository.IngredientRepositoryWrapper;
import hr.obai.tacoapplication.ingredient.domain.service.dto.IngredientDto;
import hr.obai.tacoapplication.ingredient.domain.service.mapper.IngredientMapper;
import hr.obai.commons.service.CrudServiceTemplate;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl extends CrudServiceTemplate<
        IngredientDto,
        Ingredient,
        IngredientRepository,
        IngredientCriteria> {

    @Autowired
    protected IngredientServiceImpl(@NonNull IngredientRepositoryWrapper repositoryWrapper, @NonNull IngredientMapper mapper) {
        super(repositoryWrapper, mapper);
    }
}
