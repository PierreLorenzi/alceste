package com.plo.alceste.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProportionValue extends GraphElement implements Vertex {

    @Builder.Default
    private double proportion = 1.0;
}
