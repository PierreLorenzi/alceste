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

        Dependency dependency = findDependencyBetween(v1, v2);
        List<ComparisonLink> comparisonLinks = findComparisonsBetween(v1, v2);
        List<ComparisonLink> loops1 = findLoops(v1);
        List<ComparisonLink> loops2 = findLoops(v2);


    }

    private Dependency findDependencyBetween(Vertex v1, Vertex v2) {
        if (isAncestor(v1, v2)) {
            return new Dependency(v1, v2);
        }
        else if (isAncestor(v2, v1)) {
            return new Dependency(v2, v1);
        }
        else {
            return null;
        }
    }

    private boolean isAncestor(Vertex possibleAncestor, Vertex possibleDescendant) {
        Stream<Vertex> ancestors = Stream.iterate(possibleDescendant, this::findParent);
        return ancestors.anyMatch(ancestor -> ancestor == possibleAncestor);
    }

    private Vertex findParent(Vertex vertex) {
        List<DependencyLink> links = streamElements(DependencyLink.class)
                .filter(l -> l.getDestination() == vertex && l.getProbability() == 1.0)
                .toList();
        return switch (links.size()) {
            case 0 -> null;
            case 1 -> links.get(0).getOrigin();
            default -> throw new RuntimeException("Vertex has multiple parents");
        };
    }

    private <T extends GraphElement> Stream<T> streamElements(Class<T> type) {
        return elements.stream()
                .filter(type::isInstance)
                .map(type::cast);
    }

    private boolean doesLinkHaveVertices(Link l, Vertex v1, Vertex v2) {
        return (l.getOrigin() == v1 && l.getDestination() == v2)
                || (l.getDestination() == v2 && l.getOrigin() == v1);
    }

    private List<ComparisonLink> findComparisonsBetween(Vertex v1, Vertex v2) {
        return streamElements(ComparisonLink.class)
                .filter(l -> doesLinkHaveVertices(l, v1, v2))
                .toList();
    }

    private List<ComparisonLink> findLoops(Vertex vertex) {
        return findComparisonsBetween(vertex, vertex);
    }

    public record Dependency(Vertex parent, Vertex child) {}

    public enum Relationship {
        DEPENDENCY,
        PORTION,
        IDENTIFICATION,
        COMPARISON,
        CONFORMATION,
        COLLECTION,
        DEFINITION,
    }
}
