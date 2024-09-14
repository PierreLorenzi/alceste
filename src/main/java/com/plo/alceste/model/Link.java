package com.plo.alceste.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Quand il se développe, le développement se fait aussi sur les valeurs, ce qui permet de savoir le sens de corrélation.
 * <p>
 * Les deux valeurs sont plutôt ressenties comme un facteur.
 */
@Getter
@AllArgsConstructor
public final class Link extends GraphElement implements Vertex {

    private final LinkType type;
    private final Vertex origin;
    private final Vertex destination;
    private final RatioValue originValue;
    private final RatioValue destinationValue;
}
