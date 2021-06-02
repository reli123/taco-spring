package hr.obai.tacoapplication.taco.domain.service.mapper;

import hr.obai.tacoapplication.taco.domain.repository.Taco;
import hr.obai.tacoapplication.taco.domain.service.dto.TacoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TacoMapper {
    public List<TacoDto> listToDto(List<Taco> all) {
        return all.stream().map(this::toDto).collect(Collectors.toList());
    }
    public TacoDto toDto(Taco taco) {
        TacoDto dto = new TacoDto();
        dto.setName(taco.getName());
        return dto;
    }
}
