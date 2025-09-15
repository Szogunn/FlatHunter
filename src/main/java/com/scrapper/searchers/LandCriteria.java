package com.scrapper.searchers;

import com.scrapper.entities.Point;

import java.util.List;
import java.util.Optional;

public class LandCriteria implements SearchCriteria {

    private final int daysSinceCreated;
    private final int maxPrice;
    private final int areaMin;
    private final List<String> plotTypes;
    private final Point [] mapBounds;

    private LandCriteria(Builder builder) {
        this.daysSinceCreated = builder.daysSinceCreated;
        this.maxPrice = builder.maxPrice;
        this.areaMin = builder.areaMin;
        this.plotTypes = builder.plotTypes;
        this.mapBounds = builder.mapBounds;
    }

    public Optional<Integer> getDaysSinceCreated() {
        return Optional.of(daysSinceCreated);
    }

    public Optional<Integer> getMaxPrice() {
        return Optional.of(maxPrice);
    }

    public Optional<Integer> getAreaMin() {
        return Optional.of(areaMin);
    }

    public Optional<List<String>>  getPlotTypes() {
        return Optional.ofNullable(plotTypes);
    }

    public Optional<Point []> getMapBounds() {
        return Optional.ofNullable(mapBounds);
    }


    public static final class Builder {
        private int daysSinceCreated;
        private int maxPrice;
        private int areaMin;
        private List<String> plotTypes;
        private Point [] mapBounds;

        public Builder() {
        }

        public Builder daysSinceCreated(int daysSinceCreated) {
            this.daysSinceCreated = daysSinceCreated;
            return this;
        }

        public Builder maxPrice(int maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public Builder areaMin(int areaMin) {
            this.areaMin = areaMin;
            return this;
        }

        public Builder plotType(List<String>  plotTypes) {
            this.plotTypes = plotTypes;
            return this;
        }

        public Builder mapBounds(Point [] mapBounds) {
            if (mapBounds == null || mapBounds.length < 2){
                return this;
            }

            this.mapBounds = mapBounds;
            return this;
        }

        public LandCriteria build() {
            return new LandCriteria(this);
        }

    }
}
