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
public class Polygon {
    
    public static enum PointLocation {INSIDE, OUTSIDE, ON_EDGE};
    
    public static PointLocation LocatePoint (Point2 p, ArrayList<Point2> VertexPoints) {
        int numberOfIntersections = 0;
        Line l1 = new Line(p, new Point2(p.getPointX()+400, p.getPointY()));
        
        for (int i = 0; i < VertexPoints.size(); i++) {
            //Check if the point is a vertex
            Point2 p1 = VertexPoints.get(i);
            if (p == p1) {
                return PointLocation.ON_EDGE;
            }
            
            //Building edges
            Point2 p2;
            if (i == VertexPoints.size()-1) {
                p2 = VertexPoints.get(0);
            }
            else {
                p2 = VertexPoints.get(i+1);
            }
            Line l2 = new Line(p1, p2);
            
            if (p1.getPointX() != p2.getPointX()) {
                if (p1.getPointY() != p2.getPointY()) {
                    Point2 pointOfIntersection = new Point2(-1,-1);
                    System.out.println(Line.Intersection(l1, l2, pointOfIntersection));
                    if (Line.Intersection(l1, l2, pointOfIntersection)) {
                        if (pointOfIntersection.getPointX() >= Math.min(p1.getPointX(), p2.getPointX()) && pointOfIntersection.getPointX() <= Math.max(p1.getPointX(), p2.getPointX()) &&
                            pointOfIntersection.getPointY() >= Math.min(p1.getPointY(), p2.getPointY()) && pointOfIntersection.getPointY() <= Math.max(p1.getPointY(), p2.getPointY())) {
                                numberOfIntersections ++;
                        }
                    }
                }
                else {
                    if (p.getPointX() >= Math.min(p1.getPointX(), p2.getPointX()) && p.getPointX() <= Math.max(p1.getPointX(), p2.getPointX())) {
                        if (p.getPointY() == p1.getPointY()) {
                            return PointLocation.ON_EDGE;
                        }
                        //else ??
                    }
                }
            }
            else {
                if (p.getPointY() >= Math.min(p1.getPointY(), p2.getPointY()) && p.getPointY() <= Math.max(p1.getPointY(), p2.getPointY())) {
                    if (p.getPointX() == p1.getPointX()) {
                        return PointLocation.ON_EDGE;
                    }
                    //else ??
                }
            }
                
        }
        
        if (numberOfIntersections % 2 == 0) {
            return PointLocation.OUTSIDE;
        }
        return PointLocation.INSIDE;     
    }
            
}
