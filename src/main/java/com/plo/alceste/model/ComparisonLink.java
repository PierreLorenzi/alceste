package com.plo.alceste.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ComparisonLink extends GraphElement {

    private final Vertex source;
    private final Vertex destination;
    private final double proportion;
    private final Sign sign;

    public record DirectVertex(ComparisonLink link) implements Vertex {}
    public record IndirectVertex(ComparisonLink link) implements Vertex {}
    public record DirectValue(ComparisonLink link) implements Vertex {}
    public record IndirectValue(ComparisonLink link) implements Vertex {}

    public enum Sign {
        POSITIVE,
        NEGATIVE;
    }
}
