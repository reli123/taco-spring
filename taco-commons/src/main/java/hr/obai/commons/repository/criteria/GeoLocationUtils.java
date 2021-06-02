package hr.obai.commons.repository.criteria;

import hr.obai.commons.repository.filter.GeoLocationFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public final class GeoLocationUtils {

    /**
     *
     * Geolocation search formula util
     *
     * @param filter
     * @param builder
     * @param root
     * @param lat
     * @param lng
     * @param <ENTITY>
     * @return
     */
    public static <ENTITY> Predicate getPredicateFromCoordsAlg(
            GeoLocationFilter filter,
            CriteriaBuilder builder,
            Root<ENTITY> root,
            SingularAttribute<ENTITY, Double> lat,
            SingularAttribute<ENTITY, Double> lng
        ) {

        //Parameters
        Expression<Double> searchRadiusSquareInMiles = builder.literal(filter.getRangeInMilesSquare());
        Expression<Double> centerLat = builder.literal(filter.getLat());
        Expression<Double> centerLng = builder.literal(filter.getLng());
        Expression<Double> c1 = builder.literal(filter.getC1());
        Expression<Double> c2 = builder.literal(filter.getC2());
        Expression<Double> power = builder.literal(filter.getPower());

        //pow(c1 * (o.lat - centerLat), power)
        Expression<Double> ex1Step1 = builder.diff(root.get(lat), centerLat);
        Expression<Double> ex1Step2 = builder.prod(c1, ex1Step1);
        Expression<Double> ex1 = builder.function("pow", Double.class, ex1Step2, power);

        //pow(c1 * (centerLng - lng) * cos(lat / c2), power)
        Expression<Double> ex2Step1 = builder.diff(centerLng, root.get(lng));
        Expression<Double> ex2Step2 = builder.function("cos", Double.class, builder.quot(root.get(lat), c2));
        Expression<Double> ex2Step3 = builder.prod(c1, builder.prod(ex2Step1, ex2Step2));
        Expression<Double> ex2 = builder.function("pow", Double.class, ex2Step3, power);

        //pow(c1 * (o.lat - centerLat), power)) + (pow(c1 * (centerLng - lng) * cos(lat / c2), power)
        Expression<Double> distanceFromCoordsAlg = builder.sum(ex1, ex2);

        return builder.and(
                //(pow(c1 * (o.lat - centerLat), power) + pow(c1 * (centerLng - lng) * cos(lat / c2), power)) < pow(searchRadius,power)
                builder.lessThanOrEqualTo(
                        distanceFromCoordsAlg,
                        searchRadiusSquareInMiles
                )
        );
    }
}
