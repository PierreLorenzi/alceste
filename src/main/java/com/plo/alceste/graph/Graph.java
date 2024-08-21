package com.plo.alceste.graph;

import com.plo.alceste.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Règles métier :
 * - entre deux objets, il n'y a pas plus d'un lien d'intersection et un lien de corrélation. Il peut y avoir les deux.
 * - conformation : un lien d'intersection 1 définitoire, avec une corrélation
 * - possession de phrase : à voir mais probablement avec une valeur 0+ ou une saturation 0
 */
public record Graph(List<GraphElement> elements) {

    public Graph() {
        this(new ArrayList<>());
    }

    public Relationship findRelationshipBetween(Vertex v1, Vertex v2) {

        Link intersectionLink = findLinkBetween(v1, v2, LinkType.INTERSECTION);
        Link correlationLink = findLinkBetween(v1, v2, LinkType.CORRELATION);
        List<Link> definitions1 = findDefinitions(v1);
        List<Link> definitions2 = findDefinitions(v2);

    }

    private <T extends GraphElement> Stream<T> streamElements(Class<T> type) {
        return elements.stream()
                .filter(type::isInstance)
                .map(type::cast);
    }

    private Link findLinkBetween(Vertex v1, Vertex v2, LinkType type) {
        return streamElements(Link.class)
                .filter(l -> doesLinkHaveVertices(l, v1, v2))
                .filter(l -> l.getType() == type)
                .findFirst().orElse(null);
    }

    private boolean doesLinkHaveVertices(Link l, Vertex v1, Vertex v2) {
        return (l.getOrigin() == v1 && l.getDestination() == v2)
                || (l.getOrigin() == v2 && l.getDestination() == v1);
    }

    private List<Link> findDefinitions(Vertex vertex) {
        return findLinksBetweenOfType(vertex, vertex, LinkType.CORRELATION);
    }

    private List<Link> findLinksBetweenOfType(Vertex v1, Vertex v2, LinkType type) {
        return streamElements(Link.class)
                .filter(l -> doesLinkHaveVertices(l, v1, v2))
                .filter(l -> l.getType() == type)
                .toList();
    }

    public enum Relationship {
        PORTION,
        INTERSECTION,
        IDENTIFICATION,
        CONFORMATION,
        COLLECTION,
        DEFINITION,
    }
}
