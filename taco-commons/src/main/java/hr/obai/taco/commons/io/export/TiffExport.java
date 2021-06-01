package hr.obai.taco.commons.io.export;

import org.apache.poi.ss.formula.functions.T;

public class TiffExport /*<T extends ExportableEntity> extends ExportTemplate<T>*/ {

  private Class<T> tClass;

  /*@Override
  public String fileName() {
    return tClass.getName().toUpperCase().replace("ENTITY", "")
        + "_"
        + LocalDateTime.now().toString()
        + ".tiff";
  }

  @Override
  public String contentType() {
    return "image/tiff";
  }

  @Override
  public byte[] content(List<T> entities) {
    return null;
  }*/
}
