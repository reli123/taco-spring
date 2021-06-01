package hr.obai.taco.commons.repository;

import hr.obai.taco.commons.repository.entity.BaseEntity;
import hr.obai.taco.commons.repository.entity.IdentifiableEntity;
import hr.obai.taco.commons.repository.criteria.BaseCriteria;
import hr.obai.taco.commons.repository.criteria.CriteriaEnhancedSpecificationBuilderTemplate;
import hr.obai.taco.commons.repository.criteria.CriteriaUtils;
import hr.obai.taco.commons.repository.criteria.CriteriaWrapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.NonNull;
import lombok.extern.apachecommons.CommonsLog;
import lombok.val;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * Crud Service class to query a jpa repository throught criteria.
 *
 * @param <ENTITY> the type of the jpa entity managed by the current implementation
 * @param <REPOSITORY> the type of the jpa repository
 * @param <CRITERIA> the type of the criteria used to filter
 */
@CommonsLog
public abstract class CrudRepositoryWrapperTemplate<
        ENTITY extends BaseEntity & IdentifiableEntity,
        REPOSITORY extends BaseJpaRepository<ENTITY>,
        CRITERIA extends BaseCriteria>
    extends BaseRepositoryWrapperTemplate<ENTITY, REPOSITORY, CRITERIA> {

  protected CrudRepositoryWrapperTemplate(
      @NonNull REPOSITORY repository,
      @NonNull CriteriaEnhancedSpecificationBuilderTemplate<ENTITY, CRITERIA> specificationBuilder,
      @NonNull JpaContext jpaContext) {
    super(repository, specificationBuilder, jpaContext);
  }

  @Transactional(readOnly = true)
  public List<ENTITY> findByCriteria(@NonNull CriteriaWrapper<CRITERIA> wrapper) {
    JpaUtils.enableHibernateFiltersOnEntity(jpaContext, entityClass);
    val specification = toSpecification(wrapper);
    return repository.findAll(specification);
  }

  protected Specification<ENTITY> toSpecification() {
    return CriteriaUtils.toSpecification(specificationBuilder);
  }

  protected Specification<ENTITY> toSpecification(CriteriaWrapper<CRITERIA> wrapper) {
    return CriteriaUtils.toSpecification(specificationBuilder, wrapper);
  }

  @Transactional(readOnly = true)
  public List<ENTITY> findByCriteria(
      @NonNull CriteriaWrapper<CRITERIA> criteria, @NonNull Sort sort) {
    JpaUtils.enableHibernateFiltersOnEntity(jpaContext, entityClass);
    val specification = toSpecification(criteria);
    return repository.findAll(specification, sort);
  }

  @Transactional(readOnly = true)
  public Page<ENTITY> findByCriteria(
      @NonNull CriteriaWrapper<CRITERIA> wrapper, @NonNull Pageable pageable) {
    JpaUtils.enableHibernateFiltersOnEntity(jpaContext, entityClass);
    val specification = toSpecification(wrapper);
    return repository.findAll(specification, pageable);
  }

  @Transactional(readOnly = true)
  public List<ENTITY> findAllById(@NonNull List<Long> ids) {
    return repository.findAllById(ids);
  }

  @Transactional
  public ENTITY save(@NonNull ENTITY entity) {
    Validate.isTrue(entity.getId() == null, "cannot save entity because it already exists");
    return doSaveOrUpdate(entity);
  }

  private ENTITY doSaveOrUpdate(ENTITY entity) {
    return repository.save(entity);
  }

  @Transactional
  public ENTITY update(@NonNull ENTITY entity) {
    Validate.isTrue(entity.getId() != null, "cannot update entity because it does not exist");
    return doSaveOrUpdate(entity);
  }

  @Transactional(readOnly = true)
  public Optional<ENTITY> findById(@NonNull Long id) {
    JpaUtils.enableHibernateFiltersOnEntity(jpaContext, entityClass);
    val specification = toSpecification();
    return repository.findOne(specification.and((root, query, cb) -> cb.equal(root.get("id"), id)));
  }

  @Transactional
  public void deleteById(@NonNull Long id) {
    deleteByIds(Collections.singletonList(id));
  }

  @Transactional
  public void deleteByIds(@NonNull List<Long> ids) {
    repository.deleteByIdIn(ids);
  }

  @Transactional(readOnly = true)
  public List<ENTITY> findAllById(@NonNull List<Long> ids, CriteriaWrapper<CRITERIA> wrapper) {
    JpaUtils.enableHibernateFiltersOnEntity(jpaContext, entityClass);
    val specification =
        toSpecification(wrapper).and((root, query, criteriaBuilder) -> root.get("id").in(ids));
    return repository.findAll(specification);
  }

  @Transactional
  public List<ENTITY> saveAll(@NonNull List<ENTITY> entities) {
    return repository.saveAll(entities);
  }
}
