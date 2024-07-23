package com.plo.alceste.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DependencyLink extends GraphElement implements Vertex {

    private final Vertex source;
    private final Vertex destination;
    private final ProportionValue probability;
}
