/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

import java.util.ArrayList;

/**
 *
 * @author paulaceccon
 */
public class Data {

    private static Point2 mainPoint = new Point2(0F, 0F);
    private static Point2 vertexPoint = new Point2(0F, 0F);
    private static ArrayList<Point2> polygonVertex = new ArrayList<>();
    private static ArrayList<Line> polygonEdges = new ArrayList<>();
    private static ArrayList<Line> triangulationEdges = new ArrayList<>();
    private static ArrayList<Point2> convexHullVertex = new ArrayList<>();
    private static ArrayList<Line> convexHullEdges = new ArrayList<>();

    public static void clearData() {
        mainPoint = new Point2(0F, 0F);
        polygonVertex.clear();
        polygonEdges.clear();
        triangulationEdges.clear();
        convexHullVertex.clear();
        convexHullEdges.clear();
    }

    public static void clearTriangulation() {
        triangulationEdges.clear();
    }

    public static void clearConvexHull() {
        convexHullVertex.clear();
        convexHullEdges.clear();
    }

    public static Point2 getMainPoint() {
        return mainPoint;
    }

    public static void setMainPoint(Point2 mainPoint) {
        Data.mainPoint = mainPoint;
    }
    
    public static Point2 getVertexPoint() {
        return vertexPoint;
    }

    public static void setVertexPoint(Point2 vertexPoint) {
        Data.vertexPoint = vertexPoint;
    }

    public static ArrayList<Point2> getPolygonVertex() {
        return polygonVertex;
    }

    public static ArrayList<Line> getPolygonEdges() {
        return polygonEdges;
    }

    public static ArrayList<Line> getTriangulationEdges() {
        return triangulationEdges;
    }

    public static ArrayList<Line> getConvexHullEdges() {
        return convexHullEdges;
    }

    public static void addPolygonVertex(Point2 vertex) {
        Data.polygonVertex.add(vertex);
        Data.polygonEdges.clear();
        calculatePolygonEdges(polygonVertex);
    }

    public static void addTriangulationEdge(Line l) {
        Data.triangulationEdges.add(l);
    }

    public static void addConvexHullVertex(Point2 vertex) {
        Data.convexHullVertex.add(vertex);
        Data.convexHullEdges.clear();
        calculateConvexHullEdges(convexHullVertex);
    }

    public static void calculatePolygonEdges(ArrayList<Point2> polygonVertex) {
        for (int i = 0; i < polygonVertex.size(); i++) {
            if (i == polygonVertex.size() - 1) {
                polygonEdges.add(new Line(polygonVertex.get(i), polygonVertex.get(0)));
            } else {
                polygonEdges.add(new Line(polygonVertex.get(i), polygonVertex.get(i + 1)));
            }
        }
    }

    public static void calculateConvexHullEdges(ArrayList<Point2> convexHullVertex) {
        for (int i = 0; i < convexHullVertex.size(); i++) {
            if (i == convexHullVertex.size() - 1) {
                convexHullEdges.add(new Line(convexHullVertex.get(i), convexHullVertex.get(0)));
            } else {
                convexHullEdges.add(new Line(convexHullVertex.get(i), convexHullVertex.get(i + 1)));
            }
        }
    }
}
