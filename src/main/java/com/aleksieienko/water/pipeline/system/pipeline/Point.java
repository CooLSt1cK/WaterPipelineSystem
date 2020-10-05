package com.aleksieienko.water.pipeline.system.pipeline;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Point implements Serializable {
    private static final long serialVersionUID = 6329051366186278615L;
    private Integer id;
    private Map<Point,Integer> neighbors = new HashMap<>();

    public Point(Integer id, Map<Point, Integer> neighbors) {
        this.id = id;
        this.neighbors = neighbors;
    }

    public Point() {
    }

    public Point(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<Point, Integer> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Map<Point, Integer> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return id.equals(point.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", neighbors=" + neighbors +
                '}';
    }
}
