package com.plo.alceste.model;

import com.plo.alceste.model.value.Sign;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class SignValue implements Vertex {

    @Builder.Default
    private Sign sign = Sign.POSITIVE;
}
