package hr.obai.taco.commons.service;

import hr.obai.taco.commons.dto.BaseDto;
import hr.obai.taco.commons.enums.ExportType;
import hr.obai.taco.commons.io.export.utils.Base64Response;
import hr.obai.taco.commons.io.export.utils.ExportFactory;
import hr.obai.taco.commons.repository.BaseJpaRepository;
import hr.obai.taco.commons.repository.CrudRepositoryWrapperTemplate;
import hr.obai.taco.commons.repository.criteria.BaseCriteria;
import hr.obai.taco.commons.repository.criteria.CriteriaWrapper;
import hr.obai.taco.commons.repository.entity.BaseEntity;
import hr.obai.taco.commons.repository.mapper.DtoToEntityBidirectionalMapper;
import hr.obai.taco.commons.validation.PassThroughValidator;
import hr.obai.taco.commons.validation.ServiceValidator;
import hr.obai.taco.commons.validation.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.apachecommons.CommonsLog;
import lombok.val;

/**
 * Crud Service Template
 *
 * @param <DTO>
 * @param <ENTITY>
 * @param <REPOSITORY>
 * @param <CRITERIA>
 */
@CommonsLog
public abstract class ExportServiceTemplate<
    DTO extends BaseDto & Validatable,
    ENTITY extends BaseEntity,
    REPOSITORY extends BaseJpaRepository<ENTITY>,
    CRITERIA extends BaseCriteria> extends CrudServiceTemplate {


  @Getter(AccessLevel.PROTECTED)
  @NonNull
  private final ExportFactory exportFactory;

  protected ExportServiceTemplate(@NonNull CrudRepositoryWrapperTemplate repositoryWrapper, @NonNull DtoToEntityBidirectionalMapper mapper, @NonNull ExportFactory exportFactory) {
    this(repositoryWrapper, mapper, new PassThroughValidator<>(), exportFactory);
  }

  protected ExportServiceTemplate(@NonNull CrudRepositoryWrapperTemplate repositoryWrapper, @NonNull DtoToEntityBidirectionalMapper mapper, @NonNull ServiceValidator validator, @NonNull ExportFactory exportFactory) {
    super(repositoryWrapper, mapper, validator);
    this.exportFactory = exportFactory;
  }

  public Base64Response export(ExportType type, CriteriaWrapper criteria) {
    val page =
            repositoryWrapper.findByCriteria(
                    criteria, criteria.getStandardCriteria().getPageableDto().getPageable());
    return exportFactory.getExport(type, page.toList()).toBase64Response();
  }
}
