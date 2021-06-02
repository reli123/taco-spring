package hr.obai.tacoapplication.taco.domain.service;

import hr.obai.tacoapplication.taco.domain.service.dto.TacoDto;

import java.util.List;

public interface TacoService {
    List<TacoDto> getAllTacos();
}
