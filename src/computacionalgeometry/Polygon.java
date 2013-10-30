/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author paulaceccon
 */
public class Polygon {

    public static enum PointLocation {

        INSIDE, OUTSIDE, ON
    };

    public static boolean CheckPolygonIsSimple(ArrayList<Line> polygonEdges, Point2D p) {
        boolean intersection = false;
        for (int i = 0; i < polygonEdges.size(); i++) {
            for (int j = i+1; j < polygonEdges.size(); j++) {
                intersection = Line.Intersection(Data.getPolygonEdges().get(i), Data.getPolygonEdges().get(j), p);
                if (intersection) {
                    break;
                }
            }
            if (intersection) {
                break;
            }
        }
        return intersection;
    }

    public static PointLocation LocatePointUsingRayTracing(Point2D p, ArrayList<Point2D> VertexPoints) {
        int numberOfIntersections = 0;
        Line l1 = new Line(p, new Point2D(p.getPointX() + 800, p.getPointY()));

        for (int i = 0; i < VertexPoints.size(); i++) {
            //Check if the point is a vertex
            Point2D p1 = VertexPoints.get(i);
            if (p == p1) {
                return PointLocation.ON;
            }

            //Building edges
            Point2D p2;
            if (i == VertexPoints.size() - 1) {
                p2 = VertexPoints.get(0);
            } else {
                p2 = VertexPoints.get(i + 1);
            }
            Line l2 = new Line(p1, p2);

            if (p1.getPointX() != p2.getPointX()) {
                if (p1.getPointY() != p2.getPointY()) {
                    Point2D pointOfIntersection = new Point2D(-1, -1);
                    if (Line.Intersection(l1, l2, pointOfIntersection)) {
                        if (pointOfIntersection.getPointX() >= Math.min(p1.getPointX(), p2.getPointX()) && pointOfIntersection.getPointX() <= Math.max(p1.getPointX(), p2.getPointX())
                                && pointOfIntersection.getPointY() >= Math.min(p1.getPointY(), p2.getPointY()) && pointOfIntersection.getPointY() <= Math.max(p1.getPointY(), p2.getPointY())) {
                            numberOfIntersections++;
                        }
                    }
                } else {
                    if (p.getPointX() >= Math.min(p1.getPointX(), p2.getPointX()) && p.getPointX() <= Math.max(p1.getPointX(), p2.getPointX())) {
                        if (p.getPointY() == p1.getPointY()) {
                            return PointLocation.ON;
                        }
                    }
                }
            } else {
                if (p.getPointY() >= Math.min(p1.getPointY(), p2.getPointY()) && p.getPointY() <= Math.max(p1.getPointY(), p2.getPointY())) {
                    if (p.getPointX() == p1.getPointX()) {
                        return PointLocation.ON;
                    }
                }
            }

        }

        if (numberOfIntersections % 2 == 0) {
            return PointLocation.OUTSIDE;
        }
        return PointLocation.INSIDE;
    }

    public static PointLocation LocatePointUsingRotationIndex(Point2D p, ArrayList<Point2D> VertexPoints) {
        double rotationIndex = 0;

        for (int i = 0; i < VertexPoints.size(); i++) {
            Point2D p1 = VertexPoints.get(i);

            //Building edges
            Point2D p2;
            if (i == VertexPoints.size() - 1) {
                p2 = VertexPoints.get(0);
            } else {
                p2 = VertexPoints.get(i + 1);
            }

            Vector2D v1 = new Vector2D(p, p1);
            Vector2D v2 = new Vector2D(p, p2);

            rotationIndex += v2.OrientedAngle(v1);
        }
        rotationIndex /= (float) (2 * Math.PI);
        rotationIndex = Math.round(rotationIndex);

        if (rotationIndex == 0f) {
            return PointLocation.OUTSIDE;
        } else {
            return PointLocation.INSIDE;
        }
    }

    public static void EarClippingTriangulation(ArrayList<Point2D> VertexPoints, int start) {
        if (VertexPoints.size() > 3 && start < VertexPoints.size() - 1) {
            int previous = start - 1;
            int next = start + 1;
            int pointsInside = 0;

            Point2D p1, p2, p3;
            //Build a polygon and check if it is an ear candidate
            p1 = VertexPoints.get(previous);
            p2 = VertexPoints.get(start);
            p3 = VertexPoints.get(next);

            Vector2D v0 = new Vector2D(p2, p1);
            Vector2D v1 = new Vector2D(p2, p3);

            //Check if it's an ear or a mouth
            if (v0.IsCounterclockwise(v1)) {
                ArrayList<Point2D> polygon = new ArrayList<>();
                polygon.add(p1);
                polygon.add(p2);
                polygon.add(p3);
                for (int i = 0; i < VertexPoints.size(); i++) {

                    if (Polygon.LocatePointUsingRayTracing(VertexPoints.get(i), polygon) == PointLocation.INSIDE) {
                        pointsInside++;
                    }
                }
                //It there is no point inside this polygon, we can build a new edge
                if (pointsInside == 0) {
                    Data.addTriangulationEdge(new Line(p1, p3));
                    VertexPoints.remove(start);
                    start = 1;
                } else {
                    start++;
                }
            } else {
                start++;
            }
            EarClippingTriangulation(VertexPoints, start);
        }
    }

    public static void GrahamConvexHull(ArrayList<Point2D> VertexPoints) {

        int lowestYcoordinatePoint = 0;

        for (int i = 0; i < VertexPoints.size(); i++) {
            lowestYcoordinatePoint = (VertexPoints.get(i).getPointY()) > VertexPoints.get(lowestYcoordinatePoint).getPointY() ? i : lowestYcoordinatePoint;
        }

        TreeMap<Float, Integer> orientedAngle = new TreeMap<>();

        for (int i = 0; i < VertexPoints.size(); i++) {
            if (i != lowestYcoordinatePoint) {
                Point2D p1 = VertexPoints.get(lowestYcoordinatePoint);
                Point2D p2 = VertexPoints.get(i);

                Vector2D v1 = new Vector2D(p2, p1);
                Vector2D v2 = new Vector2D(new Point2D(400, 0), new Point2D(0, 0));
                orientedAngle.put(v2.OrientedAngle(v1), i);
            }
        }

        ArrayList<Integer> orientedAngleIndex = new ArrayList<>();
        orientedAngleIndex.add(lowestYcoordinatePoint);
        for (int value : orientedAngle.values()) {
            orientedAngleIndex.add(value);
        }

        ArrayList<Point2D> convexHull = new ArrayList<>();
        convexHull.add(VertexPoints.get(orientedAngleIndex.get(0)));
        convexHull.add(VertexPoints.get(orientedAngleIndex.get(1)));
        convexHull.add(VertexPoints.get(orientedAngleIndex.get(2)));

        ArrayList<Point2D> points = new ArrayList<>();
        int M = convexHull.size();
        for (int i = 3; i < orientedAngleIndex.size() + 1; i++) {

            while (M > 2) {
                points.clear();
                points.add(convexHull.get(M - 3));
                points.add(convexHull.get(M - 2));
                points.add(convexHull.get(M - 1));
                if (!IsCounterclockwise(points)) {
                    convexHull.remove(M - 2);
                    M--;
                } else {
                    break;
                }
            }

            if (i != orientedAngleIndex.size()) {
                convexHull.add(VertexPoints.get(orientedAngleIndex.get(i)));
                M++;
            } else {
                while (M > 2) {
                    points.clear();
                    points.add(convexHull.get(M - 3));
                    points.add(convexHull.get(M - 2));
                    points.add(convexHull.get(M - 1));
                    if (!IsCounterclockwise(points)) {
                        convexHull.remove(M - 2);
                        M--;
                    } else {
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < convexHull.size(); i++) {
            Data.addConvexHullVertex(convexHull.get(i));
        }
    }

    public static void MergeHull(ArrayList<Point2D> VertexPoints) {

        ArrayList<Point2D> xOrderedVertices = new ArrayList<>();
        for (int i = 0; i < VertexPoints.size(); i++) {
            xOrderedVertices.add(VertexPoints.get(i));
        }
        Collections.sort(xOrderedVertices, Point2D.xPosition);

        ArrayList<Point2D> convexHull = DivideAndConquer(xOrderedVertices);
       
        for (int i = 0; i < convexHull.size(); i++) {
            Data.addConvexHullVertex(convexHull.get(i));
        }
    }

    public static ArrayList DivideAndConquer(ArrayList<Point2D> VertexPoints) {

        if (VertexPoints.size() < 3) {
            return VertexPoints;
        }

        int half = VertexPoints.size() / 2;

        ArrayList<Point2D> firstHalf = new ArrayList<>(VertexPoints.subList(0, half));
        ArrayList<Point2D> secondHalf = new ArrayList<>(VertexPoints.subList(half, VertexPoints.size()));

        DivideAndConquer(firstHalf);
        DivideAndConquer(secondHalf);

        return Merge(firstHalf, secondHalf);

    }

    public static ArrayList Merge(ArrayList<Point2D> FirstHalf, ArrayList<Point2D> SecondHalf) {
        ArrayList<Point2D> points = new ArrayList<>();
        int counter = 0;
        
        //********************upper tangent***************       
        Point2D firstPartHighestPoint = Point2D.getMaxY(FirstHalf);
        Point2D secondPartHighestPoint = Point2D.getMaxY(SecondHalf);

        int i = SecondHalf.indexOf(secondPartHighestPoint);
        for (i = (i + SecondHalf.size() - 1) % SecondHalf.size(); SecondHalf.size() > 1 ;i = (i+1)%SecondHalf.size())
        {
            Vector2D v1 = new Vector2D(firstPartHighestPoint, SecondHalf.get(i));
            Vector2D v2 = new Vector2D(SecondHalf.get(i), SecondHalf.get((i+1)%SecondHalf.size()));
            if (v1.CrossProduct(v2) < 0) {
                secondPartHighestPoint = SecondHalf.get(i);
                break; 
            }                
        }
        
        i = FirstHalf.indexOf(firstPartHighestPoint);
        for (i = (i + FirstHalf.size() - 1) % FirstHalf.size(); FirstHalf.size() > 1 ;i = (i+1)%FirstHalf.size())
        {
            Vector2D v1 = new Vector2D(secondPartHighestPoint, FirstHalf.get(i));
            Vector2D v2 = new Vector2D(FirstHalf.get(i), FirstHalf.get((i+1)%FirstHalf.size()));
            if (v1.CrossProduct(v2) > 0) {
                firstPartHighestPoint = FirstHalf.get(i);
                break; 
            }                
        }

        //******************lower tangent***************     
        Point2D firstPartLowestPoint = Point2D.getMinY(FirstHalf);
        Point2D secondPartLowestPoint = Point2D.getMinY(SecondHalf);

        i = SecondHalf.indexOf(secondPartLowestPoint);
        for (i = (i + SecondHalf.size() - 1) % SecondHalf.size(); SecondHalf.size() > 1 ;i = (i+1)%SecondHalf.size())
        {
            Vector2D v1 = new Vector2D(firstPartLowestPoint, SecondHalf.get(i));
            Vector2D v2 = new Vector2D(SecondHalf.get(i), SecondHalf.get((i+1)%SecondHalf.size()));
            if (v1.CrossProduct(v2) > 0) {
                secondPartLowestPoint = SecondHalf.get(i);
                break; 
            }                
        }
        
        i = FirstHalf.indexOf(firstPartLowestPoint);
        for (i = (i + FirstHalf.size() - 1) % FirstHalf.size(); FirstHalf.size() > 1 ;i = (i+1)%FirstHalf.size())
        {
            Vector2D v1 = new Vector2D(secondPartLowestPoint, FirstHalf.get(i));
            Vector2D v2 = new Vector2D(FirstHalf.get(i), FirstHalf.get((i+1)%FirstHalf.size()));
            if (v1.CrossProduct(v2) < 0) {
                firstPartLowestPoint = FirstHalf.get(i);
                break; 
            }                
        }
        
        int index1f = FirstHalf.indexOf(firstPartHighestPoint);
        int index2f = FirstHalf.indexOf(firstPartLowestPoint);
              
        for (i = (index1f+1)%FirstHalf.size(); i != index2f && counter < FirstHalf.size(); i=(i+1)%FirstHalf.size()) {
            counter++;
            points.add(FirstHalf.get(i));
        }
        FirstHalf.removeAll(points);
           
        int index1s = SecondHalf.indexOf(secondPartLowestPoint);
        int index2s = SecondHalf.indexOf(secondPartHighestPoint);
              
        points.clear();
        counter = 0;
        for (i = (index1s+1)%SecondHalf.size(); i != index2s && counter < SecondHalf.size(); i=(i+1)%SecondHalf.size()) {
            counter++;
            points.add(SecondHalf.get(i));
        }
        
        SecondHalf.removeAll(points);
        
        FirstHalf.addAll(SecondHalf);
        
        return FirstHalf;
    }

    public static boolean IsCounterclockwise(ArrayList<Point2D> VertexPoints) {
        double crossProduct = 0;

        Point2D p1 = VertexPoints.get(0);
        Point2D p2 = VertexPoints.get(1);
        Vector2D v1 = new Vector2D(p1, p2);
        for (int i = 1; i < VertexPoints.size() - 1; i++) {
            p1 = VertexPoints.get(i);

            if (i == VertexPoints.size() - 1) {
                p2 = VertexPoints.get(0);
            } else {
                p2 = VertexPoints.get(i + 1);
            }

            Vector2D v2 = new Vector2D(p1, p2);

            crossProduct += v1.CrossProduct(v2);

            v1 = v2;
        }

        return crossProduct > 0;
    }
}
