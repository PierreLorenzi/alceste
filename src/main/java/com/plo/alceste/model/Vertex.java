package com.plo.alceste.model;

public sealed interface Vertex permits Object, DependencyLink, DependencyLink.Proportion,
        ComparisonLink.LinkVertex, ComparisonLink.ValueVertex {
}
