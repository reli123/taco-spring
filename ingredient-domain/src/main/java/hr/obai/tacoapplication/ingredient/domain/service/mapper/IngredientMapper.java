package hr.obai.tacoapplication.ingredient.domain.service.mapper;

import hr.obai.tacoapplication.ingredient.domain.repository.Ingredient;
import hr.obai.tacoapplication.ingredient.domain.service.dto.IngredientDto;
import hr.obai.commons.repository.mapper.DtoToEntityBidirectionalMapper;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper implements DtoToEntityBidirectionalMapper<IngredientDto, Ingredient> {

    @Override
    public IngredientDto toDto(Ingredient ingredient) {
        return null;
    }

    @Override
    public Ingredient toEntity(IngredientDto ingredientDto) {
        return null;
    }
}
