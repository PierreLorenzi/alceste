package com.plo.alceste.model;

import lombok.Data;

@Data
public abstract sealed class Vertex permits Object, DependencyLink, SmallValue, MultiplicationLink, LargeValue {

    private String id;
    private String name;
}
