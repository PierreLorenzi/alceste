package com.plo.alceste.model;

import com.plo.alceste.model.value.Direction;
import com.plo.alceste.model.value.Fraction;
import lombok.Data;

@Data
public final class LargeValue extends Vertex {

    private Fraction fraction;
    private Direction direction;
}
