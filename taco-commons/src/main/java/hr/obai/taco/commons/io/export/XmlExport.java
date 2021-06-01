package hr.obai.taco.commons.io.export;

import hr.obai.taco.commons.repository.entity.BaseEntity;
import hr.obai.taco.commons.io.export.utils.ExportTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.GenericTypeResolver;

@Slf4j
public class XmlExport<ENTITY extends BaseEntity> extends ExportTemplate {

  private Class<ENTITY> tClass;
  private List<ENTITY> entities;

  public XmlExport(List<ENTITY> entities) {
    this.tClass =
        (Class<ENTITY>) GenericTypeResolver.resolveTypeArgument(getClass(), XmlExport.class);
    this.entities = entities;
  }

  @Override
  public String fileName() {
    return tClass.getSimpleName().toUpperCase().replace("XMLEXPORT", "")
        + "_"
        + LocalDateTime.now().toString()
        + ".xml";
  }

  @Override
  public String contentType() {
    return "application/xml";
  }

  @Override
  public byte[] content() {
    val output = new ByteArrayOutputStream();
    try {
      JAXBContext context = JAXBContext.newInstance(tClass.getClass());

      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      entities.forEach(
          entity -> {
            marshalWrapper(marshaller, entity, output);
          });

      output.close();

    } catch (JAXBException | IOException e) {
      log.error(e.getMessage());
      ExceptionUtils.rethrow(e);
    }

    return output.toByteArray();
  }

  private void marshalWrapper(Marshaller marshaller, ENTITY entity, ByteArrayOutputStream output) {
    try {
      marshaller.marshal(entity, output);
    } catch (JAXBException e) {
      log.error(e.getMessage());
      ExceptionUtils.rethrow(e);
    }
  }
}
