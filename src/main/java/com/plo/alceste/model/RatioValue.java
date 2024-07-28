package com.plo.alceste.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RatioValue implements Vertex {

    private final ProportionValue proportion;
    private final SignValue sign;
}
