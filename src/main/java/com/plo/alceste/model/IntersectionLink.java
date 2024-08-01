package com.plo.alceste.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class IntersectionLink extends GraphElement implements Link, Vertex {

    private final Vertex origin;
    private final Vertex destination;
    private final RatioValue ratio;
}
