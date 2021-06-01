package hr.obai.taco.commons.io.export.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Base64Response {
  private String fileName;
  private String contentType;
  private String content;
}
