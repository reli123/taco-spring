package hr.obai.taco.commons.io.export.utils;

import java.util.Base64;
import lombok.val;

public abstract class ExportTemplate {

  protected abstract String fileName();

  protected abstract String contentType();

  protected abstract byte[] content();

  public Base64Response toBase64Response() {
    val base64Response = new Base64Response();
    base64Response.setContentType(contentType());
    base64Response.setFileName(fileName());
    base64Response.setContent(Base64.getEncoder().withoutPadding().encodeToString(content()));
    return base64Response;
  }
}
