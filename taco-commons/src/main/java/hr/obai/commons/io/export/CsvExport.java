package hr.obai.commons.io.export;

import hr.obai.commons.io.export.utils.ExportTemplate;
import hr.obai.commons.repository.entity.BaseEntity;
import hr.obai.commons.repository.entity.CsvExportableEntity;
import com.opencsv.CSVWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Getter
@Setter
@Slf4j
public class CsvExport<ENTITY extends BaseEntity> extends ExportTemplate {

  private CsvExportableEntity<ENTITY> exportableEntity;
  private List<ENTITY> entities;
  private CSVWriter csvWriter;
  private char separator = CSVWriter.DEFAULT_SEPARATOR;
  private char noQuoteChar = CSVWriter.NO_QUOTE_CHARACTER;
  private char escapeChar = CSVWriter.DEFAULT_ESCAPE_CHARACTER;
  private String lineEnd = CSVWriter.DEFAULT_LINE_END;
  private boolean applyQuotesToAll = false;

  public CsvExport(List<ENTITY> entities, CsvExportableEntity<ENTITY> exportableEntity) {
    this.entities = entities;
    this.exportableEntity = exportableEntity;
  }

  @Override
  public String fileName() {
    return exportableEntity.getClass().getSimpleName().toUpperCase().replace("CSVEXPORT", "")
        + "_"
        + LocalDateTime.now().toString()
        + ".csv";
  }

  @Override
  public String contentType() {
    return "text/csv";
  }

  @Override
  public byte[] content() {
    val output = new ByteArrayOutputStream();
    this.csvWriter =
        new CSVWriter(
            new OutputStreamWriter(output),
            getSeparator(),
            getNoQuoteChar(),
            getEscapeChar(),
            getLineEnd());
    writeHeader();
    writeContent();

    try {
      csvWriter.close();
      output.close();
    } catch (IOException e) {
      log.error(e.getMessage());
      ExceptionUtils.rethrow(e);
    }

    return output.toByteArray();
  }

  private void writeHeader() {
    exportableEntity.header(this);
  }

  private void writeContent() {
    entities.forEach(entity -> exportableEntity.content(this, entity));
  }
}
