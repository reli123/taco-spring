package hr.obai.ingredientdomain.service;

import hr.obai.ingredientdomain.repository.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
}
