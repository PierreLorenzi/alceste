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
        ComparisonLink loop1 = findMainLoop(v1);
        ComparisonLink loop2 = findMainLoop(v2);
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
        Stream<Vertex> ancestors = streamAncestors(possibleDescendant);
        return ancestors.anyMatch(ancestor -> ancestor == possibleAncestor);
    }

    private Stream<Vertex> streamAncestors(Vertex possibleDescendant) {
        return Stream.iterate(possibleDescendant, this::findParent);
    }

    private Vertex findParent(Vertex vertex) {
        List<DependencyLink> links = streamElements(DependencyLink.class)
                .filter(l -> l.getDestination() == vertex && computeProportion(l.getProbability()).isEqualTo(1.0))
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

    private CertainValue<Double> computeProportion(ProportionValue proportionValue) {
        if (proportionValue == null) {
            return new CertainValue<>(1.0, null);
        }
        Ratio certainty = computeCertainty(proportionValue);
        return new CertainValue<>(proportionValue.getValue(), certainty);
    }

    private Ratio computeCertainty(Vertex vertex) {
        ComparisonLink mainLoop = findMainLoop(vertex);
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

    private ComparisonLink findMainLoop(Vertex vertex) {
        List<ComparisonLink> loops = findLoops(vertex);
        if (loops.isEmpty()) {
            return null;
        }
        return findMain(loops);
    }

    private <T extends Vertex> T findMain(List<T> vertices) {
        T vertex = vertices.get(0);
        List<Vertex> ancestors = streamAncestors(vertex)
                .filter(vertices::contains)
                .toList();
        return (T)ancestors.get(ancestors.size()-1);
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
        IDENTIFICATION,
        COMPARISON,
        CONFORMATION,
        COLLECTION,
        DEFINITION,
    }
}
