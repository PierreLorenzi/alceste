package com.plo.alceste.model;

import com.plo.alceste.model.value.Fraction;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public final class SmallValue extends Vertex {

    private Fraction fraction;

    @Override
    public String toString() {
        return switch (fraction.graduation()) {
            case ONE -> "1";
            case ZERO -> "0";
            case RATIO -> fraction.ratio().toString();
        };
    }
}
