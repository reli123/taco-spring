package hr.obai.tacoapplication.taco.domain.controller;

import hr.obai.tacoapplication.taco.domain.service.TacoService;
import hr.obai.tacoapplication.taco.domain.service.TacoServiceImpl;
import hr.obai.tacoapplication.taco.domain.service.dto.TacoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tacos")
public class TacoController {

    private TacoService theFacadeOfTheService;

    @Autowired
    public TacoController(TacoServiceImpl fromContextService) {
        this.theFacadeOfTheService = fromContextService;
    }

    @GetMapping
    public ResponseEntity<List<TacoDto>> getTacos() {
        return ResponseEntity.ok(theFacadeOfTheService.getAllTacos());
    }

    @GetMapping("/sd")
    public String getTacdos() {
        return "test";
    }
}
