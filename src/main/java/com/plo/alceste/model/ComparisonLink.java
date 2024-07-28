package com.plo.alceste.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ComparisonLink extends GraphElement implements Link {

    private final Vertex origin;
    private final Vertex destination;
    private final double proportion;
    private final Sign sign;

    public record LinkVertex(ComparisonLink link, Direction direction) implements Vertex {}
    public record ValueVertex(ComparisonLink link, Direction direction) implements Vertex {}

    public enum Sign {
        POSITIVE,
        NEGATIVE;
    }

    public LinkVertex forwardVertex() {
        return new LinkVertex(this, Direction.FORWARD);
    }

    public LinkVertex backwardVertex() {
        return new LinkVertex(this, Direction.BACKWARD);
    }

    public double forwardValue() {
        return (sign == Sign.POSITIVE) ? proportion : 1.0/proportion;
    }
}
