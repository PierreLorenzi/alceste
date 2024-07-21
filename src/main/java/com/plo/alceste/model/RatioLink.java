package com.plo.alceste.model;

import lombok.Data;

@Data
public final class RatioLink extends Vertex {

    private final Vertex source;
    private final Vertex target;
    private final LargeValue ratio;
}
