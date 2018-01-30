package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Location entity.
 */
public class LocationDTO implements Serializable {

    private double lat;

    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationDTO)) return false;
        LocationDTO that = (LocationDTO) o;
        return Double.compare(that.lat, lat) == 0 &&
            Double.compare(that.lng, lng) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(lat, lng);
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
            "lat=" + lat +
            ", lng=" + lng +
            '}';
    }
}
