package com.aleksieienko.water.pipeline.system.pipeline;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph {
    private Map<Integer,Point> points = new HashMap<>();

    public Graph() {
    }

    public Map<Integer, Point> getPoints() {
        return points;
    }

    public void setPoints(Map<Integer, Point> points) {
        this.points = points;
    }

    public Graph(Map<Integer, Point> points) {
        this.points = points;
    }

    public void addPoint(Point point){
        if(points.containsKey(point.getId())){
            return;
        }
        points.put(point.getId(),point);
    }

    public void removePoint(Point point){
        points.remove(point.getId());
    }

    public void addEdge(Integer pointAId, Integer pointBId, Integer weight){
        if(!points.containsKey(pointAId)){
            points.put(pointAId, new Point(pointAId));
        }
        if(!points.containsKey(pointBId)){
            points.put(pointBId, new Point(pointBId));
        }
        Point a = points.get(pointAId);
        Point b = points.get(pointBId);
        a.getNeighbors().put(b,weight);
    }

    public void removeEdge(Integer pointAId, Integer pointBId){
        Point a = points.get(pointAId);
        if(a.getNeighbors().containsKey(pointBId)){
            a.getNeighbors().remove(pointBId);
        }
    }

    private boolean isRouteExist(Integer startPointId, Integer endPointId){
        Queue<Point> queue = new LinkedList();
        queue.add(points.get(startPointId));
        Set<Point> visitedPoints = new HashSet<>();
        Point point;

        while((point = queue.poll()) != null){

            if(visitedPoints.contains(point)){

                continue;
            }

            if(point.getId().equals(endPointId)){

                return true;
            }

            visitedPoints.add(point);
            queue.addAll(point.getNeighbors().keySet());
        }

        return false;
    }

    /**
     * @param startPointId
     * @param endPointId
     * @return return null if route does not exist
     */
    public Integer shortestRouteWeight(Integer startPointId, Integer endPointId){

        if(!isRouteExist(startPointId, endPointId)){

            return null;
        }

        Point point = points.get(startPointId);
        Map<Point, Integer> weightMap = new HashMap<>(point.getNeighbors());
        Queue<Point> queue = new LinkedList(point.getNeighbors().keySet());
        Set<Point> visitedPoints = new HashSet<>();

        while((point = queue.poll()) != null){

            if(visitedPoints.contains(point)){

                continue;
            }

            for(Map.Entry<Point,Integer> x : point.getNeighbors().entrySet()){

                if(!weightMap.containsKey(x.getKey())){

                    weightMap.put(x.getKey(),(weightMap.get(point)+x.getValue()));
                } else {

                    if(weightMap.get(x.getKey()) >= (weightMap.get(point) + x.getValue())){

                        weightMap.remove(x.getKey());
                        weightMap.put(x.getKey(),weightMap.get(point) + point.getNeighbors().get(x.getKey()));
                        queue.remove(x.getKey());
                    }
                }
            }

            visitedPoints.add(point);
            queue.addAll(point.getNeighbors().keySet());
        }

        return weightMap.get(points.get(endPointId));
    }

}
