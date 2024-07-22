package com.plo.alceste.model;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class Object extends Vertex {

    @Override
    public String toString() {
        return "\"" + getName() + "\"";
    }
}
