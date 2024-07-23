package com.plo.alceste.graph;

import com.plo.alceste.model.*;
import com.plo.alceste.model.Object;
import com.plo.alceste.model.value.Direction;
import com.plo.alceste.model.value.Fraction;
import com.plo.alceste.model.value.Graduation;

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

    public GraphBuilder comparison(String source, String destination, double factor) {
        Vertex sourceVertex = findVertexWithName(source);
        Vertex destinationVertex = findVertexWithName(destination);

        Direction direction = (factor <= 1.0) ? Direction.DIRECT : Direction.INDIRECT;
        double ratio = (direction == Direction.DIRECT) ? factor : 1.0 / factor;
        Graduation graduation = findGraduationForRatio(ratio);
        Fraction fraction = new Fraction(graduation, ratio);
        LargeValue value = LargeValue.builder()
                .fraction(fraction)
                .direction(direction)
                .build();
        ComparisonLink link = ComparisonLink.builder()
                .source(sourceVertex)
                .destination(destinationVertex)
                .ratio(value)
                .build();

        vertices.add(value);
        vertices.add(link);

        return this;
    }

    private Graduation findGraduationForRatio(double ratio) {
        if (ratio == 0.0) {
            return Graduation.ZERO;
        }
        else if (ratio == 1.0) {
            return Graduation.ONE;
        }
        else {
            return Graduation.RATIO;
        }
    }
}
