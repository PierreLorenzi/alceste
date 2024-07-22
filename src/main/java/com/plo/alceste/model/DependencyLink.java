package com.plo.alceste.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public final class DependencyLink extends Vertex {

    private final Vertex source;
    private final Vertex destination;
    private final SmallValue probability;

    @Override
    public String toString() {
        return source + " -> " + destination;
    }
}
