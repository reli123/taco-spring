package hr.obai.tacoapplication.ingredient.domain.service.dto;

import hr.obai.commons.dto.BaseDto;
import hr.obai.commons.validation.Validatable;
import hr.obai.tacoapplication.ingredient.domain.repository.IngredientType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDto extends BaseDto implements Validatable {

    private String name;
    private Float price;
    private IngredientType type;

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void setValid(boolean valid) {

    }
}
