package com.plo.alceste.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DependencyLink extends GraphElement implements Vertex, Link {

    private final Vertex origin;
    private final Vertex destination;
    @Builder.Default
    private final Proportion probability = new Proportion(1.0);

    public record Proportion(double value) implements Vertex {}
}
