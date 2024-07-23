package com.plo.alceste.model;

import com.plo.alceste.model.value.Sign;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ComparisonLink extends GraphElement {

    private final GraphElement source;
    private final GraphElement destination;
    private final ProportionValue proportion;
    private final SignValue sign;

    private final ComparisonVertex positiveVertex = new ComparisonVertex(this, Sign.POSITIVE);
    private final ComparisonVertex negativeVertex = new ComparisonVertex(this, Sign.NEGATIVE);

    public record ComparisonVertex(ComparisonLink link, Sign sign) implements Vertex {}
}
