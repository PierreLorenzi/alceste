package com.plo.alceste.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Object extends GraphElement implements Vertex {

    private final String name;
}
