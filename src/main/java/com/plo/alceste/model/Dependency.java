package com.plo.alceste.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Dependency extends GraphElement {

    private final Vertex parent;
    private final Vertex child;
}
