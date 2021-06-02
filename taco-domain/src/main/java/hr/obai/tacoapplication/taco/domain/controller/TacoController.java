package hr.obai.tacoapplication.taco.domain.controller;

import hr.obai.tacoapplication.taco.domain.service.TacoService;
import hr.obai.tacoapplication.taco.domain.service.TacoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tacos")
public class TacoController {

    private TacoService service;

    public TacoController(TacoServiceImpl service) {
        this.service = service;
    }

}
