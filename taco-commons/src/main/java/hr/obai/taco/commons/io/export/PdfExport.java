package hr.obai.taco.commons.io.export;

public
class PdfExport /*<EXPORT_TYPE extends ExportableEntity, ENTITY extends IdentifiableEntity> extends ExportTemplate<EXPORT_TYPE> */ {

  /*private Class<EXPORT_TYPE> tClass;
  private List<ENTITY> entities;

  public PdfExport(List<ENTITY> entities){
    this.entities=entities;
  }

  @Override
  public String fileName() {
    return tClass.getName().toUpperCase().replace("ENTITY", "")
        + "_"
        + LocalDateTime.now().toString()
        + ".pdf";
  }

  @Override
  public String contentType() {
    return "application/pdf";
  }

  @Override
  public byte[] content(@NonNull List<EXPORT_TYPE> items) {
    val output = new ByteArrayOutputStream();
    try {
      if (items.size() > 1) {
        generatePdfTable(items, output);
      } else {
        generatePdfPage(items.stream().findFirst().orElseThrow(RuntimeException::new), output);
      }

      output.close();
    } catch (IOException | DocumentException e) {
      ExceptionUtils.rethrow(e);
    }

    return output.toByteArray();
  }

  private void generatePdfPage(EXPORT_TYPE item, ByteArrayOutputStream output) throws IOException {
    PDDocument document = new PDDocument();
    PDPage page = new PDPage();
    document.addPage(page);
    PDPageContentStream contentStream = new PDPageContentStream(document, page);
    // item.toPdfPage(contentStream);
    */
  /*
  contentStream.setFont(PDType1Font.COURIER, 12);
  contentStream.beginText();
  contentStream.showText("Hello World");
  contentStream.endText();
   */
  /*

    contentStream.close();
    document.save(output);
    document.close();
  }

  private void generatePdfTable(List<EXPORT_TYPE> items, ByteArrayOutputStream output)
      throws DocumentException {
    Document document = new Document();
    PdfWriter.getInstance(document, output);
    document.open();
    val columns = FieldUtils.getAllFieldsList(tClass);
    PdfPTable table = new PdfPTable(columns.size());
    writeHeader(table, columns);
    writeContent(table, items);
    document.add(table);
    document.close();
  }

  private void writeHeader(PdfPTable table, List<Field> columns) {
    columns.forEach(
        columnTitle -> {
          PdfPCell header = new PdfPCell();
          header.setBackgroundColor(BaseColor.LIGHT_GRAY);
          header.setBorderWidth(2);
          header.setPhrase(new Phrase(String.valueOf(columnTitle)));
          table.addCell(header);
        });
  }

  private void writeContent(PdfPTable table, List<EXPORT_TYPE> items) {
    items.forEach(EXPORT_TYPE::content);
    */
  /**
   * table.addCell("row 1, col 1"); table.addCell("row 1, col 2"); table.addCell("row 1, col 3");
   */
  /*
  }*/
}
