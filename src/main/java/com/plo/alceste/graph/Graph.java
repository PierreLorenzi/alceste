package com.plo.alceste.graph;

import com.plo.alceste.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record Graph(List<GraphElement> elements) {

    public Graph() {
        this(new ArrayList<>());
    }

    public Relationship findRelationshipBetween(Vertex v1, Vertex v2) {

        IndirectDependency dependency = findDependencyBetween(v1, v2);
        List<Link> links = findLinksBetween(v1, v2);
        List<Link> definitions1 = findDefinitions(v1);
        List<Link> definitions2 = findDefinitions(v2);

    }

    private IndirectDependency findDependencyBetween(Vertex v1, Vertex v2) {
        if (isAncestor(v1, v2)) {
            return new IndirectDependency(v1, v2);
        }
        else if (isAncestor(v2, v1)) {
            return new IndirectDependency(v2, v1);
        }
        else {
            return null;
        }
    }

    private boolean isAncestor(Vertex possibleAncestor, Vertex possibleDescendant) {
        return streamAncestors(possibleDescendant)
                .anyMatch(ancestor -> ancestor == possibleAncestor);
    }

    private Stream<Vertex> streamAncestors(Vertex vertex) {
        return Stream.iterate(vertex, this::findParent);
    }

    private Vertex findParent(Vertex vertex) {
        return streamElements(Dependency.class)
                .filter(d -> d.getChild() == vertex)
                .map(Dependency::getParent)
                .findFirst().orElse(null);
    }

    private <T extends GraphElement> Stream<T> streamElements(Class<T> type) {
        return elements.stream()
                .filter(type::isInstance)
                .map(type::cast);
    }

    private List<Link> findLinksBetween(Vertex v1, Vertex v2) {
        return streamElements(Link.class)
                .filter(l -> doesLinkHaveVertices(l, v1, v2))
                .toList();
    }

    private boolean doesLinkHaveVertices(Link l, Vertex v1, Vertex v2) {
        return (l.getOrigin() == v1 && l.getDestination() == v2)
                || (l.getOrigin() == v2 && l.getDestination() == v1);
    }

    private List<Link> findDefinitions(Vertex vertex) {
        return findLinksBetweenOfType(vertex, vertex, LinkType.COMPLIANCE);
    }

    private List<Link> findLinksBetweenOfType(Vertex v1, Vertex v2, LinkType type) {
        return streamElements(Link.class)
                .filter(l -> doesLinkHaveVertices(l, v1, v2))
                .filter(l -> l.getType() == type)
                .toList();
    }

    private record IndirectDependency(Vertex ancestor, Vertex descendant) {}

    public enum Relationship {
        DEPENDENCY,
        PORTION,
        INTERSECTION,
        IDENTIFICATION,
        INTERSECTION,
        CONFORMATION,
        COLLECTION,
        DEFINITION,
    }
}
