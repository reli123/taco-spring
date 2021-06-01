package hr.obai.taco.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortColumn {
  private String column = "id";
  private boolean inverse = true;
}
