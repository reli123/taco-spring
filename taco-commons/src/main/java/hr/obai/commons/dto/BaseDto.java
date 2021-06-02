package hr.obai.commons.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Base class for all top level dtos. */
@Getter
@Setter
@ToString
public class BaseDto {
  @JsonProperty private Long id;
}
