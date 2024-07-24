package com.plo.alceste.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DependencyLink extends GraphElement implements Vertex {

    private final Vertex source;
    private final Vertex destination;
    @Builder.Default
    private final Proportion probability = new Proportion(1.0);

    public record Proportion(double value) implements Vertex {}
}
