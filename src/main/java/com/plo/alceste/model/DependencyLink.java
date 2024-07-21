package com.plo.alceste.model;

import lombok.Data;

@Data
public final class DependencyLink extends Vertex {

    private final Vertex source;
    private final Vertex target;
    private final SmallValue probability;
}
