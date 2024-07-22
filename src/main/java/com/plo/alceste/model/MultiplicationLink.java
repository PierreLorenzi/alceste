package com.plo.alceste.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public final class MultiplicationLink extends Vertex {

    private final Vertex source;
    private final Vertex destination;
    private final LargeValue ratio;

    @Override
    public String toString() {
        return destination + " = " + ratio + " * " + source;
    }
}
