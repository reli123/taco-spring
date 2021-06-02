package hr.obai.tacoapplication.ingredient.domain.controller;

import hr.obai.tacoapplication.ingredient.domain.repository.IngredientCriteria;
import hr.obai.tacoapplication.ingredient.domain.service.dto.IngredientDto;
import hr.obai.commons.controller.CrudControllerTemplate;
import hr.obai.commons.service.CrudServiceTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tacos")
public class IngredientController extends CrudControllerTemplate<IngredientDto, IngredientCriteria> {


    protected IngredientController(CrudServiceTemplate service) {
        super(service);
    }
}
