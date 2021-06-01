package hr.obai.taco.commons.repository.criteria;

import hr.obai.taco.commons.repository.entity.BaseEntity;
import hr.obai.taco.commons.repository.entity.BaseEntity_;

import java.util.ArrayList;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.springframework.data.jpa.domain.Specification;

/**
 * Criteria-related utilities.
 *
 * @see BaseCriteria
 * @see CriteriaEnhancedSpecificationBuilderTemplate
 * @see CriteriaWrapper
 */
@UtilityClass
@SuppressWarnings("OptionalGetWithoutIsPresent")
public final class CriteriaUtils {

    public static <ENTITY extends BaseEntity, CRITERIA extends BaseCriteria>
    Specification<ENTITY> toSpecification(
            CriteriaEnhancedSpecificationBuilderTemplate<ENTITY, CRITERIA> specificationBuilder,
            CriteriaWrapper<CRITERIA> wrapper) {
        val list = new ArrayList<Specification<ENTITY>>();
        list.add(specificationBuilder.buildSpecification(wrapper.getStandardCriteria()));
        return list.stream().reduce(Specification::and).get();
    }

    public static <ENTITY extends BaseEntity, CRITERIA extends BaseCriteria>
    Specification<ENTITY> toSpecification(
            CriteriaEnhancedSpecificationBuilderTemplate<ENTITY, CRITERIA> specificationBuilder) {
        val list = new ArrayList<Specification<ENTITY>>();
        list.add(specificationBuilder.buildSpecification());
        return list.stream().reduce(Specification::and).get();
    }
}
