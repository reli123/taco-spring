package hr.obai.commons.repository.criteria;

import hr.obai.commons.enums.LogicalOperator;
import hr.obai.commons.repository.entity.BaseEntity;
import hr.obai.commons.repository.filter.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Enhances the {@link SpecificationBuilderTemplate} with CRITERIA support.
 *
 * @param <CRITERIA> the type of the criteria
 */
public abstract class CriteriaEnhancedSpecificationBuilderTemplate<
        ENTITY extends BaseEntity, CRITERIA extends BaseCriteria>
        extends SpecificationBuilderTemplate<ENTITY> {

    public abstract Specification<ENTITY> buildSpecification(CRITERIA criteria);

    public Specification<ENTITY> concatenateSpecification(LogicalOperator op, Specification<ENTITY>... specifications) {
        switch (op) {
            case OR:
                return Arrays.stream(specifications).reduce(Specification::or).get();
            default:
                return Arrays.stream(specifications).reduce(Specification::and).get();
        }
    }

    /**
     * Creates an empty {@link Specification}.
     *
     * @return an empty {@link Specification}.
     */
    public Specification<ENTITY> buildSpecification() {
        return Specification.where(null);
    }

    /**
     * StringFilter
     *
     * @param specification
     * @param filter
     * @param field
     * @return
     */
    protected Specification addStringFilterCriteria(
            Specification specification, StringFilter filter, SingularAttribute<ENTITY, String> field) {
        if (filter != null) {
            specification =
                    concatenateSpecification(
                            filter.getOp(), specification, buildStringSpecification(filter, field));
        }
        return specification;
    }

    /**
     * LongFilter
     *
     * @param specification
     * @param filter
     * @param field
     * @return
     */
    protected Specification addLongFilterCriteria(
            Specification specification, LongFilter filter, SingularAttribute<ENTITY, Long> field) {
        if (filter != null) {
            specification =
                    concatenateSpecification(
                            filter.getOp(), specification, buildSpecification(filter, field));
        }
        return specification;
    }

    protected Specification<ENTITY> buildGeoLocationSpecification(
            GeoLocationFilter filter,
            SingularAttribute<ENTITY, Double> lat,
            SingularAttribute<ENTITY, Double> lng) {

        if (filter != null) {
            return (root, criteriaQuery, criteriaBuilder) -> GeoLocationUtils.getPredicateFromCoordsAlg(filter, criteriaBuilder, root, lat, lng);
        }
        return null;
    }

    ;

    /**
     * Many-to-many specification helper
     *
     * @param filter
     * @param reference
     * @param eFieldValue         Entity Field Value
     * @param cFieldValue         Correlation Field Value
     * @param <CORRELATED_ENTITY>
     * @param <X>
     * @return
     */
    protected <CORRELATED_ENTITY extends BaseEntity, X> Specification<ENTITY> buildSpecification(
            Filter<X> filter,
            ListAttribute<? super ENTITY, CORRELATED_ENTITY> reference,
            SingularAttribute<ENTITY, X> eFieldValue,
            SingularAttribute<CORRELATED_ENTITY, X> cFieldValue) {

        if (filter.getIn() != null) {
            return containsAllValues(reference, filter.getIn(), eFieldValue, cFieldValue);
        }

        return null;
    }

    protected <CORRELATED_ENTITY extends BaseEntity, X> Specification<ENTITY> containsAllValues(
            ListAttribute<? super ENTITY, CORRELATED_ENTITY> reference,
            List<X> values,
            SingularAttribute<ENTITY, X> eFieldValue,
            SingularAttribute<CORRELATED_ENTITY, X> cFieldValue) {
        return (root, query, builder) -> {

            Subquery subQuery = builder.createQuery().subquery(reference.getBindableJavaType());
            Root<CORRELATED_ENTITY> subRoot = subQuery.from(root.getJavaType());
            Join<CORRELATED_ENTITY, ENTITY> join = subRoot.join(reference.getName());

            CriteriaBuilder.In in = builder.in(subRoot.get(cFieldValue));
            for (X value : values) {
                in.value(value);
            }

            Predicate equal = builder.equal(root.get(eFieldValue), join.get(cFieldValue.getName()));

            subQuery
                    .distinct(true)
                    .select(builder.count(subRoot.get(cFieldValue)))
                    .where(in, equal);

            return builder.equal(subQuery, values.size());
        };
    }


    protected <OTHER extends BaseEntity> Specification<ENTITY> buildReferringDateRangeSpecification(
            DateRangeFilter<LocalDateTime> filter,
            SetAttribute<ENTITY, OTHER> reference,
            Specification<OTHER> otherSpecification,
            SingularAttribute<OTHER, ENTITY> childReference
    ) {

        Function<CriteriaQuery<?>, Subquery<Long>> subqueryFunction = criteriaBuilder -> criteriaBuilder.subquery(Long.class);

        Function<Subquery<Long>, Root<OTHER>> rootFunction = longSubquery -> longSubquery.from(reference.getBindableJavaType());
        Function<Root<OTHER>, Join<OTHER, ENTITY>> parentJoinFunc = otherRoot -> otherRoot.join(childReference);
        Function<Join<OTHER, ENTITY>, Expression<?>> otherIdFromJoin = root -> root.get("id");
        Function<Subquery<Long>, Expression<?>> otherId = rootFunction.andThen(parentJoinFunc).andThen(otherIdFromJoin);
        Function<Root<ENTITY>, Expression<?>> entityId = root -> root.get("id");

        Specification<ENTITY> specification = (root, query, builder) -> {

            Subquery<Long> subquery = subqueryFunction.apply(query);
            Root<OTHER> subRoot = rootFunction.apply(subquery);
            Predicate joinPredicate = builder.equal(entityId.apply(root), otherId.apply(subquery));

            //Check if a stored from date is between from and to dates
            Predicate timePredicate = builder.between(subRoot.get("booked_from"), filter.getFrom(), filter.getTo());

            //Check if a stored to if is between from and to dates
            Predicate betweenTo = builder.between(subRoot.get("booked_to"), filter.getFrom(), filter.getTo());

            //Check if a stored to date is to date is after to date,
            //but the stored from date is before the from date
            Predicate outsideTimeFrame = builder.and(
                    builder.greaterThanOrEqualTo(subRoot.get("booked_to"), filter.getTo()),
                    builder.lessThanOrEqualTo(subRoot.get("booked_from"), filter.getFrom())
            );

            //Check if the a stored to date if before the to date and
            // the the stored from is after the from date
            Predicate insideTimeFrame = builder.and(
                    builder.lessThanOrEqualTo(subRoot.get("booked_to"), filter.getTo()),
                    builder.greaterThanOrEqualTo(subRoot.get("booked_from"), filter.getFrom())
            );

            //Combining the predicates
            if (timePredicate == null) {
                timePredicate = betweenTo;
                timePredicate = builder.or(timePredicate, outsideTimeFrame, insideTimeFrame);
            } else
                timePredicate = builder.or(timePredicate, betweenTo, outsideTimeFrame, insideTimeFrame);

            subquery.where(timePredicate, joinPredicate);

            return builder.equal(subquery, 0L);
        };

        return specification;
    }

    protected Specification<ENTITY> buildDateRangeSpecification(
            DateRangeFilter filter,
            String startDate,
            String endDate) {

        Function<Root<ENTITY>, Expression<ChronoLocalDateTime>> start = otherRoot -> otherRoot.get(startDate);
        Function<Root<ENTITY>, Expression<ChronoLocalDateTime>> end = otherRoot -> otherRoot.get(endDate);

        if (filter == null) {
            return null;
        }

        switch (filter.getOperation()) {
            case COVER:
                return concatenateSpecification(LogicalOperator.AND, lessThanOrEqualTo(start, filter.getFrom()), greaterThanOrEqualTo(end, filter.getTo()));
            case INSIDE:
                return concatenateSpecification(LogicalOperator.AND, greaterThanOrEqualTo(start, filter.getFrom()), lessThanOrEqualTo(end, filter.getTo()));
            case OUTSIDE_IN:
                return valueBetween(end, filter.getFrom(), filter.getTo());
            case INSIDE_OUT:
                return valueBetween(start, filter.getFrom(), filter.getTo());
            case ALL:
                return concatenateSpecification(
                        LogicalOperator.OR,
                        concatenateSpecification(LogicalOperator.AND, lessThanOrEqualTo(start, filter.getFrom()), greaterThanOrEqualTo(end, filter.getTo())),
                        concatenateSpecification(LogicalOperator.AND, greaterThanOrEqualTo(start, filter.getFrom()), lessThanOrEqualTo(end, filter.getTo())),
                        valueBetween(end, filter.getFrom(), filter.getTo()),
                        valueBetween(start, filter.getFrom(), filter.getTo()));
            default:
                return null;
        }
    }
}