package com.plo.alceste.model;

public sealed interface Vertex permits Object,
        DependencyLink.LinkVertex, DependencyLink.ValueVertex,
        ComparisonLink.LinkVertex, ComparisonLink.ValueVertex {
}
