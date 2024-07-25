package com.plo.alceste.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DependencyLink extends GraphElement implements Link {

    private final Vertex origin;
    private final Vertex destination;
    @Builder.Default
    private final double probability = 1.0;

    public record LinkVertex(DependencyLink link) implements Vertex {}
    public record ValueVertex(DependencyLink link) implements Vertex {}
}
