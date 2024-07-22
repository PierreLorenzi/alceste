package com.plo.alceste.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract sealed class Vertex permits Object, DependencyLink, SmallValue, MultiplicationLink, LargeValue {

    private String id;
    private String name;
}
