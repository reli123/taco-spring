package hr.obai.commons.repository.entity;

import hr.obai.commons.io.export.CsvExport;

public interface CsvExportableEntity<ENTITY extends BaseEntity> {
  void header(CsvExport csv);

  void content(CsvExport csv, ENTITY e);
}
