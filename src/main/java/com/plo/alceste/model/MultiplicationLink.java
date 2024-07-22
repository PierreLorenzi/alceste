package com.plo.alceste.model;

import lombok.Data;

@Data
public final class MultiplicationLink extends Vertex {

    private final Vertex source;
    private final Vertex destination;
    private final LargeValue ratio;
}
