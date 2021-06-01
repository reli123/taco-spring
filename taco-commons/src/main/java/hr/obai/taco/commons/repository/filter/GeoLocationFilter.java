package hr.obai.taco.commons.repository.filter;

import hr.obai.taco.commons.validation.Validatable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class GeoLocationFilter implements Validatable {

    private double rangeInMiles;
    private double rangeInKm;

    @Getter
    private Double lat;
    @Getter
    private Double lng;

    public Double getC1() {
        return 69.1D;
    }

    public Double getC2() {
        return 57.3D;
    }

    public Double getPower() {
        return 2D;
    }

    public Double getRangeInMilesSquare() {

        if (rangeInMiles <= 0) return Math.pow(rangeInMiles, 2);
        else return Math.pow(kmToMiles(rangeInKm), 2);
    }

    private static double kmToMiles(double distanceInKm) {
        return distanceInKm * 0.621371;
    }

    @Override
    public boolean isValid() {

        boolean valid = true;

        valid &= (rangeInKm <= 0) != (rangeInMiles <= 0);
        valid &= lat != null && lng != null;

        return valid;
    }

    @Override
    public void setValid(boolean valid) {

    }
}
