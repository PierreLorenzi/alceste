package com.plo.alceste.graph;

import com.plo.alceste.model.ComparisonLink;
import com.plo.alceste.model.Vertex;

import java.util.ArrayList;
import java.util.List;

public record Graph(List<Vertex> vertices) {

    public Graph() {
        this(new ArrayList<>());
    }

    public static GraphBuilder builder() {
        return new GraphBuilder();
    }

    public ComparisonLink findLoopAround(Vertex vertex) {
        List<ComparisonLink> loops = vertices.stream()
                .filter(v -> v instanceof ComparisonLink)
                .map(v -> (ComparisonLink) v)
                .filter(link -> link.getSource() == vertex && link.getDestination() == vertex)
                .toList();
        return switch (loops.size()) {
            case 0 -> null;
            case 1 -> loops.get(0);
            default -> throw new RuntimeException("There are multiple loops: " + loops);
        };
    }
}
