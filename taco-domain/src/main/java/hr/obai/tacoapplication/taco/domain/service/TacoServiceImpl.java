package hr.obai.tacoapplication.taco.domain.service;

import hr.obai.tacoapplication.taco.domain.repository.TacoRepository;
import org.springframework.stereotype.Service;

@Service
public class TacoServiceImpl implements TacoService {

    private TacoRepository tacoRepository;

    public TacoServiceImpl(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }
}
