package hr.obai.ingredientdomain.controller;

import hr.obai.ingredientdomain.repository.IngredientCriteria;
import hr.obai.ingredientdomain.service.dto.IngredientDto;
import hr.obai.taco.commons.controller.CrudControllerTemplate;
import hr.obai.taco.commons.service.CrudServiceTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tacos")
public class IngredientController extends CrudControllerTemplate<IngredientDto, IngredientCriteria> {


    protected IngredientController(CrudServiceTemplate service) {
        super(service);
    }
}
