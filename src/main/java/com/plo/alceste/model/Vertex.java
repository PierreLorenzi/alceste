package com.plo.alceste.model;

public sealed interface Vertex permits Object,
        DependencyLink, ProportionValue,
        IntersectionLink, RatioValue, SignValue {
}
