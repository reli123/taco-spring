package hr.obai.commons.service;

import hr.obai.commons.dto.BaseDto;
import hr.obai.commons.repository.BaseJpaRepository;
import hr.obai.commons.repository.CrudRepositoryWrapperTemplate;
import hr.obai.commons.repository.criteria.BaseCriteria;
import hr.obai.commons.repository.criteria.CriteriaWrapper;
import hr.obai.commons.repository.entity.BaseEntity;
import hr.obai.commons.repository.filter.LongFilter;
import hr.obai.commons.repository.filter.StringFilter;
import hr.obai.commons.repository.mapper.DtoToEntityBidirectionalMapper;
import hr.obai.commons.validation.PassThroughValidator;
import hr.obai.commons.validation.ServiceValidator;
import hr.obai.commons.validation.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.apachecommons.CommonsLog;
import lombok.val;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Crud Service Template
 *
 * @param <DTO>
 * @param <ENTITY>
 * @param <REPOSITORY>
 * @param <CRITERIA>
 */
@CommonsLog
public abstract class CrudServiceTemplate<
    DTO extends BaseDto & Validatable,
    ENTITY extends BaseEntity,
    REPOSITORY extends BaseJpaRepository<ENTITY>,
    CRITERIA extends BaseCriteria> {

  @NonNull protected String USER = "system";

  /** The repository wrapper used to access the db through criteria and filters. */
  @Getter(AccessLevel.PROTECTED)
  @NonNull
  protected final CrudRepositoryWrapperTemplate<ENTITY, REPOSITORY, CRITERIA> repositoryWrapper;

  /** The mapper used to convert dto to entity and vice-versa. */
  @Getter(AccessLevel.PROTECTED)
  @NonNull
  private final DtoToEntityBidirectionalMapper<DTO, ENTITY> mapper;

  @Getter(AccessLevel.PROTECTED)
  @NonNull
  private final ServiceValidator<DTO> validator;

  protected CrudServiceTemplate(
      @NonNull CrudRepositoryWrapperTemplate<ENTITY, REPOSITORY, CRITERIA> repositoryWrapper,
      @NonNull DtoToEntityBidirectionalMapper<DTO, ENTITY> mapper) {
    this(repositoryWrapper, mapper, new PassThroughValidator<>());
  }

  protected CrudServiceTemplate(
      @NonNull CrudRepositoryWrapperTemplate<ENTITY, REPOSITORY, CRITERIA> repositoryWrapper,
      @NonNull DtoToEntityBidirectionalMapper<DTO, ENTITY> mapper,
      @NonNull ServiceValidator<DTO> validator) {
    this.repositoryWrapper = repositoryWrapper;
    this.mapper = mapper;
    this.validator = validator;
  }

  public DTO create(@NonNull DTO input) {
    if (validate(input)) {
      val entityIn = mapper.toEntity(input);
      val entityOut = repositoryWrapper.save(entityIn);
      return mapper.toDto(entityOut);
    } else {
      return input;
    }
  }

  public DTO update(@NonNull DTO input) {
    if (validate(input)) {
      val entityIn = mapper.toEntity(input);
      val entityOut = repositoryWrapper.update(entityIn);
      return mapper.toDto(entityOut);
    } else {
      return input;
    }
  }

  public Page<DTO> search(@NonNull CriteriaWrapper<CRITERIA> criteria) {
    val page =
        repositoryWrapper.findByCriteria(
            criteria, criteria.getStandardCriteria().getPageableDto().getPageable());
    return page.map(mapper::toDto);
  }

  public List<DTO> findAllById(@NonNull List<Long> ids) {
    val entities = repositoryWrapper.findAllById(ids);
    return mapper.toDtos(entities);
  }

  public Optional<DTO> findById(@NonNull Long id) {
    return repositoryWrapper.findById(id).map(mapper::toDto);
  }

  public void deleteById(@NonNull Long id) {
    repositoryWrapper.deleteById(id);
  }

  public void deleteByIds(@NonNull List<Long> ids) {
    repositoryWrapper.deleteByIds(ids);
  }

  public boolean validate(@NonNull DTO item) {
    return validator.validate(item);
  }

  protected LongFilter filterByLongValues(List<Long> ids) {
    val longFilter = new LongFilter();
    longFilter.setIn(ids);
    return longFilter;
  }

  protected LongFilter filterByLongValue(Long id) {
    val longFilter = new LongFilter();
    longFilter.setEquals(id);
    return longFilter;
  }

  protected StringFilter filterByStringValue(String str) {
    val stringFilter = new StringFilter();
    stringFilter.setEquals(str);
    return stringFilter;
  }
}
