package com.plo.alceste.model;

import com.plo.alceste.model.value.Direction;
import com.plo.alceste.model.value.Fraction;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public final class LargeValue extends Vertex {

    private Fraction fraction;
    private Direction direction;

    @Override
    public String toString() {
        return switch (direction) {
            case DIRECT -> switch (fraction.graduation()) {
                case ONE -> "1";
                case ZERO -> "0";
                case RATIO -> fraction.ratio().toString();
            };
            case INDIRECT -> switch (fraction.graduation()) {
                case ONE -> "1";
                case ZERO -> "∞";
                case RATIO -> Double.toString(1.0 / fraction.ratio());
            };
        };
    }
}
