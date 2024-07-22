package com.plo.alceste.model;

import lombok.Data;

@Data
public final class DependencyLink extends Vertex {

    private final Vertex source;
    private final Vertex destination;
    private final SmallValue probability;
}
