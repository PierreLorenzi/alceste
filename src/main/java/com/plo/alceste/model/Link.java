package com.plo.alceste.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Link extends GraphElement implements Vertex {

    private final LinkType type;
    private final Vertex origin;
    private final Vertex destination;
    private final RatioValue originValue;
    private final RatioValue destinationValue;
}
