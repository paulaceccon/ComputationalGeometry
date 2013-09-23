/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author paulaceccon
 */
public class Polygon {

    public static enum PointLocation {

        INSIDE, OUTSIDE, ON
    };

    public static PointLocation LocatePointUsingRayTracing(Point2 p, ArrayList<Point2> VertexPoints) {
        int numberOfIntersections = 0;
        Line l1 = new Line(p, new Point2(p.getPointX() + 800, p.getPointY()));

        for (int i = 0; i < VertexPoints.size(); i++) {
            //Check if the point is a vertex
            Point2 p1 = VertexPoints.get(i);
            if (p == p1) {
                return PointLocation.ON;
            }

            //Building edges
            Point2 p2;
            if (i == VertexPoints.size() - 1) {
                p2 = VertexPoints.get(0);
            } else {
                p2 = VertexPoints.get(i + 1);
            }
            Line l2 = new Line(p1, p2);

            if (p1.getPointX() != p2.getPointX()) {
                if (p1.getPointY() != p2.getPointY()) {
                    Point2 pointOfIntersection = new Point2(-1, -1);
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

    public static PointLocation LocatePointUsingRotationIndex(Point2 p, ArrayList<Point2> VertexPoints) {
        double rotationIndex = 0;

        for (int i = 0; i < VertexPoints.size(); i++) {
            Point2 p1 = VertexPoints.get(i);

            //Building edges
            Point2 p2;
            if (i == VertexPoints.size() - 1) {
                p2 = VertexPoints.get(0);
            } else {
                p2 = VertexPoints.get(i + 1);
            }

            Vector2 v1 = new Vector2(p, p1);
            Vector2 v2 = new Vector2(p, p2);

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

    public static void EarClippingTriangulation(ArrayList<Point2> VertexPoints, int start) {
        if (VertexPoints.size() > 3 && start < VertexPoints.size() - 1) {
            int previous = start - 1;
            int next = start + 1;
            int pointsInside = 0;

            Point2 p1, p2, p3;
            p1 = VertexPoints.get(previous);
            p2 = VertexPoints.get(start);
            p3 = VertexPoints.get(next);

            Vector2 v0 = new Vector2(p2, p1);
            Vector2 v1 = new Vector2(p2, p3);

            if (v0.IsCounterclockwise(v1)) {
                ArrayList<Point2> polygon = new ArrayList<>();
                polygon.add(p1);
                polygon.add(p2);
                polygon.add(p3);
                for (int i = 0; i < VertexPoints.size(); i++) {

                    if (Polygon.LocatePointUsingRayTracing(VertexPoints.get(i), polygon) == PointLocation.INSIDE) {
                        pointsInside++;
                    }
                }
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

    public static void GrahamConvexHull(ArrayList<Point2> VertexPoints) {

        int lowestYcoordinatePoint = 0;

        for (int i = 0; i < VertexPoints.size(); i++) {
            lowestYcoordinatePoint = (VertexPoints.get(i).getPointY()) > VertexPoints.get(lowestYcoordinatePoint).getPointY() ? i : lowestYcoordinatePoint;
        }

        TreeMap<Float, Integer> orientedAngle = new TreeMap<>();

        for (int i = 0; i < VertexPoints.size(); i++) {
            if (i != lowestYcoordinatePoint) {
                Point2 p1 = VertexPoints.get(lowestYcoordinatePoint);
                Point2 p2 = VertexPoints.get(i);

                Vector2 v1 = new Vector2(p2, p1);
                Vector2 v2 = new Vector2(new Point2(400, 0), new Point2(0, 0));
                orientedAngle.put(v2.OrientedAngle(v1), i);
            }
        }

        ArrayList<Integer> orientedAngleIndex = new ArrayList<>();
        orientedAngleIndex.add(lowestYcoordinatePoint);
        for (int value : orientedAngle.values()) {
            orientedAngleIndex.add(value);
        }

        ArrayList<Point2> convexHull = new ArrayList<>();
        convexHull.add(VertexPoints.get(orientedAngleIndex.get(0)));
        convexHull.add(VertexPoints.get(orientedAngleIndex.get(1)));
        convexHull.add(VertexPoints.get(orientedAngleIndex.get(2)));

        ArrayList<Point2> points = new ArrayList<>();
        int M = convexHull.size();
        for (int i = 3; i < orientedAngleIndex.size() + 1; i++) {

            while (M > 2) {
                points.clear();
                points.add(convexHull.get(M - 3));
                points.add(convexHull.get(M - 2));
                points.add(convexHull.get(M - 1));
                if (!IsCounterclockwise(points)) {
                    System.out.println("Points tested: " + points.get(0).getPointX() + " " + points.get(1).getPointX() + " " + points.get(2).getPointX());
                    System.out.println("Removed: " + convexHull.get(M - 2).getPointX() + "\n");
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

        System.out.println(convexHull.size());
        for (int i = 0; i < convexHull.size(); i++) {
            Data.addConvexHullVertex(convexHull.get(i));
        }
    }

    public static boolean IsCounterclockwise(ArrayList<Point2> VertexPoints) {
        double crossProduct = 0;

        Point2 p1 = VertexPoints.get(0);
        Point2 p2 = VertexPoints.get(1);
        Vector2 v1 = new Vector2(p1, p2);
        for (int i = 1; i < VertexPoints.size() - 1; i++) {
            p1 = VertexPoints.get(i);

            if (i == VertexPoints.size() - 1) {
                p2 = VertexPoints.get(0);
            } else {
                p2 = VertexPoints.get(i + 1);
            }

            Vector2 v2 = new Vector2(p1, p2);

            crossProduct += v1.CrossProduct(v2);

            v1 = v2;
        }

        return crossProduct > 0;
    }
}
