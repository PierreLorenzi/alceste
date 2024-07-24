package com.plo.alceste.model;

public sealed interface Vertex permits Object, DependencyLink, DependencyLink.Proportion,
ComparisonLink.DirectVertex, ComparisonLink.IndirectVertex, ComparisonLink.DirectValue, ComparisonLink.IndirectValue {
}
