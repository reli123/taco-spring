package hr.obai.commons.io.export.utils;

import hr.obai.commons.enums.ExportType;
import hr.obai.commons.io.export.CsvExport;
import hr.obai.commons.io.export.ExcelExport;
import hr.obai.commons.io.export.XmlExport;
import hr.obai.commons.repository.entity.BaseEntity;
import hr.obai.commons.repository.entity.CsvExportableEntity;
import hr.obai.commons.repository.entity.ExcelExportableEntity;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExportFactory<ENTITY extends BaseEntity> {

  @Autowired private ExcelExportableEntity<ENTITY> excelExportableEntity;
  @Autowired private CsvExportableEntity<ENTITY> csvExportableEntity;

  public ExportTemplate getExport(ExportType type, List<ENTITY> entities) {
    switch (type) {
        /*case PDF:
        return new PdfExport<EXPORT_TYPE, ENTITY>(entities);*/
      case CSV:
        return new CsvExport<ENTITY>(entities, csvExportableEntity);
      case EXCEL:
        return new ExcelExport<ENTITY>(entities, excelExportableEntity);
        /*case TIFF:
        return new TiffExport<EXPORT_TYPE, ENTITY>(entities);*/
      case XML:
        return new XmlExport<ENTITY>(entities);
      default:
        val error = "File type for export not found!";
        log.error(error);
        throw new RuntimeException(error);
    }
  }
}
