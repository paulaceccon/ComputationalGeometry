/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

/**
 *
 * @author paulaceccon
 */
public class Line {

    public enum PointSituation {LEFT, RIGHT, ON};
    private Point2 startPoint;
    private Point2 endPoint;

    public Line(Point2 p0, Point2 p1) {
        this.startPoint = p0;
        this.endPoint = p1;
    }

    public Point2 GetStartPoint() {
        return startPoint;
    }

    public Point2 GetEndPoint() {
        return endPoint;
    }
   
    public static PointSituation PointSituatedAt(Line l, Point2 p) {
        
        float xa = l.GetStartPoint().getPointX(), ya = l.GetStartPoint().getPointY();
        float xb = l.GetEndPoint().getPointX()  , yb = l.GetEndPoint().getPointY();
        float xp = p.getPointX(), yp = p.getPointY();
        
        float det = (xb-xa) * (yp-ya) - (xp-xa) * (yb-ya);
      
        if (det > 0) {
            return PointSituation.RIGHT;
        }
        if (det < 0) {
            return PointSituation.LEFT;
        }
        else {
            return PointSituation.ON;
        }
    }
    
    public static boolean Intersection(Line l1, Line l2, Point2 i) {
        float xa = l1.GetStartPoint().getPointX(), ya = l1.GetStartPoint().getPointY();
        float xb = l1.GetEndPoint().getPointX()  , yb = l1.GetEndPoint().getPointY();
        float xc = l2.GetStartPoint().getPointX(), yc = l2.GetStartPoint().getPointY();
        float xd = l2.GetEndPoint().getPointX()  , yd = l2.GetEndPoint().getPointY();
        
        float det = ((xb-xa) * (yd-yc)) - ((yb-ya) * (xd-xc));      
        
        float s = ((xb-xa)*(ya-yc) - (yb-ya)*(xa-xc))/det;
        float t = ((ya-yc)*(xd-xc) - (xa-xc)*(yd-yc))/det;
        
        
        if (det != 0) {
            if (s > 0 && s < 1 && t > 0 && t < 1)
            {
                i.setPointX(xa + (t * (xb-xa)));
                i.setPointY(ya + (t * (yb-ya)));
                return true;
            }
        } 
        return false;
    }
    
    
}
