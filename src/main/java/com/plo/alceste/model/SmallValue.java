package com.plo.alceste.model;

import com.plo.alceste.model.value.Fraction;
import lombok.Data;

@Data
public final class SmallValue extends Vertex {

    private Fraction fraction;
}
