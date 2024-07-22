package com.plo.alceste.graph;

import com.plo.alceste.model.DependencyLink;
import com.plo.alceste.model.Object;
import com.plo.alceste.model.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphBuilder {

    private final List<Vertex> vertices = new ArrayList<>();

    public GraphBuilder object(String name) {
        vertices.add(Object.builder()
                .name(name)
                .build());
        return this;
    }

    public GraphBuilder dependency(String source, String destination) {
        Vertex sourceVertex = findVertexWithName(source);
        Vertex destinationVertex = findVertexWithName(destination);
        vertices.add(DependencyLink.builder()
                .source(sourceVertex)
                .destination(destinationVertex)
                .build());
        return this;
    }

    private Vertex findVertexWithName(String name) {
        List<Vertex> nameVertices = vertices.stream()
                .filter(v -> Objects.equals(v.getName(), name))
                .toList();
        return switch (nameVertices.size()) {
            case 0 -> throw new RuntimeException("No vertex with name " + name);
            case 1 -> nameVertices.get(0);
            default -> throw new RuntimeException("Multiple vertices with name " + name + ": " + nameVertices);
        };
    }
}
