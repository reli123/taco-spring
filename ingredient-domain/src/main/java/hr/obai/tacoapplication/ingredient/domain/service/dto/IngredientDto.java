package hr.obai.tacoapplication.ingredient.domain.service.dto;

import hr.obai.commons.dto.BaseDto;
import hr.obai.commons.validation.Validatable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDto extends BaseDto implements Validatable {

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void setValid(boolean valid) {

    }
}
