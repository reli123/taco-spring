package hr.obai.commons.io.export;

import hr.obai.commons.io.export.utils.ExportTemplate;
import hr.obai.commons.repository.entity.BaseEntity;
import hr.obai.commons.repository.entity.ExcelExportableEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Slf4j
@Getter
@Setter
public class ExcelExport<ENTITY extends BaseEntity> extends ExportTemplate {

  private ExcelExportableEntity<ENTITY> exportableEntity;

  private List<ENTITY> entities;

  private Workbook workbook;
  private Sheet sheet;
  private String sheetName = "Sheet1";

  public ExcelExport(List<ENTITY> entities, ExcelExportableEntity<ENTITY> exportableEntity) {
    this.exportableEntity = exportableEntity;
    this.entities = entities;
    this.workbook = new XSSFWorkbook();
    this.sheet = (XSSFSheet) workbook.createSheet(sheetName);
  }

  @Override
  public String fileName() {
    return exportableEntity.getClass().getSimpleName().toUpperCase().replace("EXCELEXPORT", "")
        + "_"
        + LocalDateTime.now().toString()
        + ".xlsx";
  }

  @Override
  public String contentType() {
    return "application/excel";
  }

  @Override
  public byte[] content() {
    val output = new ByteArrayOutputStream();
    try {
      writeHeader();
      writeContent();
      workbook.write(output);
      workbook.close();
    } catch (IOException e) {
      log.error(e.getMessage());
      ExceptionUtils.rethrow(e);
    }

    return output.toByteArray();
  }

  private void writeHeader() {
    Row row = sheet.createRow(0);
    FieldUtils.getAllFieldsList(exportableEntity.getClass())
        .forEach(
            field -> {
              val cell = row.createCell(sheet.getLastRowNum() + 1);
              cell.setCellValue(field.getName().toUpperCase());
            });
    // override header behaviour
    exportableEntity.header(this);
  }

  private void writeContent() {
    entities.forEach(entity -> exportableEntity.content(this, entity));
  }
}
