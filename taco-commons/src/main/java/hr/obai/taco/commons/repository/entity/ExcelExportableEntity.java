package hr.obai.taco.commons.repository.entity;

import hr.obai.taco.commons.io.export.ExcelExport;

public interface ExcelExportableEntity<ENTITY extends BaseEntity> {
  void header(ExcelExport excel);

  void content(ExcelExport excel, ENTITY e);
}
