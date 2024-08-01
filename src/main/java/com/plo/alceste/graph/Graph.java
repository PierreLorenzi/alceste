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
        List<IntersectionLink> intersectionLinks = findIntersectionsBetween(v1, v2);
        IntersectionLink loop1 = findMainLoop(v1);
        IntersectionLink loop2 = findMainLoop(v2);
        // Wait, a relationship between the definitions can be in a sub-loop, or sub-identity


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
        List<Vertex> ancestors = findAncestors(possibleDescendant);
        return ancestors.contains(possibleAncestor);
    }

    private List<Vertex> findAncestors(Vertex vertex) {
        List<Vertex> ancestors = new ArrayList<>();
        List<Vertex> currentAncestors = List.of(vertex);
        while (!currentAncestors.isEmpty()) {
            currentAncestors = currentAncestors.stream()
                    .map(this::findParents)
                    .flatMap(List::stream)
                    .toList();
            for (Vertex currentAncestor: currentAncestors) {
                if (!ancestors.contains(currentAncestor)) {
                    ancestors.add(currentAncestor);
                }
            }
        }
        return ancestors;
    }

    private List<Vertex> findParents(Vertex vertex) {
        return streamElements(DependencyLink.class)
                .filter(l -> l.getDestination() == vertex && computeProportion(l.getProbability()).isEqualTo(1.0))
                .map(DependencyLink::getOrigin)
                .toList();
    }

    private <T extends GraphElement> Stream<T> streamElements(Class<T> type) {
        return elements.stream()
                .filter(type::isInstance)
                .map(type::cast);
    }

    private CertainValue<Double> computeProportion(ProportionValue proportionValue) {
        if (proportionValue == null) {
            return new CertainValue<>(1.0, null);
        }
        Ratio certainty = computeCertainty(proportionValue);
        return new CertainValue<>(proportionValue.getValue(), certainty);
    }

    private Ratio computeCertainty(Vertex vertex) {
        IntersectionLink mainLoop = findMainLoop(vertex);
        if (mainLoop == null) {
            return null;
        }
        return computeRatio(mainLoop.getRatio());
    }

    private Ratio computeRatio(RatioValue ratioValue) {
        CertainValue<Double> proportion = computeProportion(ratioValue == null ? null : ratioValue.getProportion());
        CertainValue<Sign> sign = computeSign(ratioValue == null ? null : ratioValue.getSign());
        return new Ratio(proportion, sign);
    }

    private CertainValue<Sign> computeSign(SignValue signValue) {
        if (signValue == null) {
            return new CertainValue<>(Sign.LESS, null);
        }
        Ratio certainty = computeCertainty(signValue);
        return new CertainValue<>(signValue.getValue(), certainty);
    }

    private IntersectionLink findMainLoop(Vertex vertex) {
        List<IntersectionLink> loops = findLoops(vertex);
        if (loops.isEmpty()) {
            return null;
        }
        return findMain(loops);
    }

    private <T extends Vertex> T findMain(List<T> vertices) {
        T vertex = vertices.get(0);
        List<Vertex> ancestors = streamAncestors(vertex) // TODO on peut garder le même algorithme
                .filter(vertices::contains)
                .toList();
        return (T)ancestors.get(ancestors.size()-1);
    }

    private boolean doesLinkHaveVertices(Link l, Vertex v1, Vertex v2) {
        return (l.getOrigin() == v1 && l.getDestination() == v2)
                || (l.getDestination() == v2 && l.getOrigin() == v1);
    }

    private List<IntersectionLink> findIntersectionsBetween(Vertex v1, Vertex v2) {
        return streamElements(IntersectionLink.class)
                .filter(l -> doesLinkHaveVertices(l, v1, v2))
                .toList();
    }

    private List<IntersectionLink> findLoops(Vertex vertex) {
        return findIntersectionsBetween(vertex, vertex);
    }

    private record CertainValue<T>(T value, Ratio certainty) {

        public boolean isEqualTo(T value) {
            return this.value.equals(value) &&
                    (certainty == null || certainty.isEqualTo(1.0));
        }
    }

    private record Ratio(CertainValue<Double> proportion, CertainValue<Sign> sign) {

        public boolean isEqualTo(double value) {
            if (sign.isEqualTo(Sign.LESS)) {
                return proportion.isEqualTo(value);
            }
            if (sign.isEqualTo(Sign.MORE)) {
                return proportion.isEqualTo(1/value);
            }
            if (value == 1.0) {
                return proportion().isEqualTo(1.0);
            }
            return false;
        }
    }

    public record Dependency(Vertex parent, Vertex child) {}

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
