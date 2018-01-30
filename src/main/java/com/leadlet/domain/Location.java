package com.leadlet.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Embeddable
public class Location {
    @Column(name = "lat")
    double lat;

    @Column(name = "lng")
    double lng;

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
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Double.compare(location.lat, lat) == 0 &&
            Double.compare(location.lng, lng) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(lat, lng);
    }

    @Override
    public String toString() {
        return "Location{" +
            "lat=" + lat +
            ", lng=" + lng +
            '}';
    }
}
