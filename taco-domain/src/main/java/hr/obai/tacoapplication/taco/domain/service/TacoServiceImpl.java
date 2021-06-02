package hr.obai.tacoapplication.taco.domain.service;

import hr.obai.tacoapplication.taco.domain.repository.Taco;
import hr.obai.tacoapplication.taco.domain.repository.TacoRepository;
import hr.obai.tacoapplication.taco.domain.service.dto.TacoDto;
import hr.obai.tacoapplication.taco.domain.service.mapper.TacoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TacoServiceImpl implements TacoService {

    private TacoRepository facadeRepository;
    private TacoMapper facadeMapper;

    public TacoServiceImpl(TacoRepository contextRepo, TacoMapper contextMapper) {
        this.facadeRepository = contextRepo;
        this.facadeMapper = contextMapper;
    }

    @Override
    public List<TacoDto> getAllTacos() {
        List<Taco> tacos = facadeRepository.findAll();
        List<TacoDto> tacoDtoList = tacos.stream().map(taco -> facadeMapper.toDto(taco)).collect(Collectors.toList());

        return tacoDtoList;
    }
}
