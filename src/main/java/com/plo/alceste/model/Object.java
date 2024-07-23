package com.plo.alceste.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Object extends GraphElement implements Vertex {

    private final String name;
}
