package hr.obai.tacoapplication.ingredient.domain.service.mapper;

import hr.obai.tacoapplication.ingredient.domain.repository.Ingredient;
import hr.obai.tacoapplication.ingredient.domain.service.dto.IngredientDto;
import hr.obai.commons.repository.mapper.DtoToEntityBidirectionalMapper;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper implements DtoToEntityBidirectionalMapper<IngredientDto, Ingredient> {

    @Override
    public IngredientDto toDto(Ingredient ingredient) {
        IngredientDto dto = new IngredientDto();
        dto.setName(ingredient.getName());
        dto.setPrice(ingredient.getPrice());
        dto.setType(ingredient.getType());
        return dto;
    }

    @Override
    public Ingredient toEntity(IngredientDto dto) {
        Ingredient entity = new Ingredient();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setType(dto.getType());
        return entity;
    }
}
