package hr.obai.taco.commons.repository;

import hr.obai.taco.commons.repository.entity.BaseEntity;
import hr.obai.taco.commons.repository.entity.BaseEntity_;
import hr.obai.taco.commons.repository.criteria.BaseCriteria;
import hr.obai.taco.commons.repository.criteria.CriteriaEnhancedSpecificationBuilderTemplate;
import hr.obai.taco.commons.repository.criteria.CriteriaUtils;
import hr.obai.taco.commons.repository.criteria.CriteriaWrapper;

import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.apachecommons.CommonsLog;
import lombok.val;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

@CommonsLog
public abstract class ReadRepositoryWrapperTemplate<
    ENTITY extends BaseEntity,
    REPOSITORY extends JpaRepository<ENTITY, Long> & JpaSpecificationExecutor<ENTITY>,
    CRITERIA extends BaseCriteria> {

  private static final int ENTITY_INDEX = 0;

  @Getter(AccessLevel.PROTECTED)
  protected final REPOSITORY repository;

  protected final CriteriaEnhancedSpecificationBuilderTemplate<ENTITY, CRITERIA>
      specificationBuilder;
  protected final JpaContext jpaContext;

  protected final Class<ENTITY> entityClass;

  protected ReadRepositoryWrapperTemplate(
      @NonNull REPOSITORY repository,
      @NonNull CriteriaEnhancedSpecificationBuilderTemplate<ENTITY, CRITERIA> specificationBuilder,
      @NonNull JpaContext jpaContext) {
    this.repository = repository;
    this.specificationBuilder = specificationBuilder;
    this.jpaContext = jpaContext;
    this.entityClass = getEntityClass();
  }

  @SuppressWarnings("unchecked")
  private Class<ENTITY> getEntityClass() {
    return (Class<ENTITY>) resolveTypeArguments()[ENTITY_INDEX];
  }

  private Class<?>[] resolveTypeArguments() {
    return GenericTypeResolver.resolveTypeArguments(
        getClass(), ReadRepositoryWrapperTemplate.class);
  }

  protected Specification<ENTITY> toSpecification(CriteriaWrapper<CRITERIA> wrapper) {
    return CriteriaUtils.toSpecification(specificationBuilder, wrapper);
  }

  @Transactional(readOnly = true)
  public Page<ENTITY> findByCriteria(
      @NonNull CriteriaWrapper<CRITERIA> wrapper, @NonNull Pageable pageable) {
    val specification = toSpecification(wrapper);
    return repository.findAll(specification, pageable);
  }

  @Transactional(readOnly = true)
  public List<ENTITY> findAllById(@NonNull List<Long> ids) {
    return repository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<ENTITY> findById(@NonNull Long id, @NonNull CriteriaWrapper<CRITERIA> wrapper) {
    val specification = toSpecification(wrapper);
    return repository.findOne(specification.and((root, query, cb) -> cb.equal(root.get("id"), id)));
  }

  @Transactional(readOnly = true)
  public List<ENTITY> findByCriteria(
      @NonNull CriteriaWrapper<CRITERIA> criteria, @NonNull Sort sort) {
    val specification = toSpecification(criteria);
    return repository.findAll(specification, sort);
  }
}
