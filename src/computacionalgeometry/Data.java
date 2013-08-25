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
    private static ArrayList<Point2> polygonVertex = new ArrayList<>();
    private static ArrayList<Line> polygonEdges = new ArrayList<>();

    public static void clearData() {
        mainPoint = new Point2(0F, 0F);
        polygonVertex.clear();
        polygonEdges.clear();
    }
    
    public static Point2 getMainPoint() {
        return mainPoint;
    }

    public static void setMainPoint(Point2 mainPoint) {
        Data.mainPoint = mainPoint;
    }

    public static ArrayList<Point2> getPolygonVertex() {
        return polygonVertex;
    }

    public static void addPolygonVertex(Point2 vertex) {
        Data.polygonVertex.add(vertex);
        Data.polygonEdges.clear();
        calculatePolygonEdges(polygonVertex);
    }
    
    public static void calculatePolygonEdges(ArrayList<Point2> polygonVertex) {
        for (int i = 0; i < polygonVertex.size(); i++) {
            if (i == polygonVertex.size()-1) {
                polygonEdges.add(new Line(polygonVertex.get(i), polygonVertex.get(0))); 
            } 
            else 
            {
                polygonEdges.add(new Line(polygonVertex.get(i), polygonVertex.get(i+1))); 
            }
        }
    }

    public static ArrayList<Line> getPolygonEdges() {
        return polygonEdges;
    }
    
}
