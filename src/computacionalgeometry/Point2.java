/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author paulaceccon
 */
public class Point2 {
    
    private float pointX;
    private float pointY;

    public Point2 (float pointX, float pointY) 
    {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public float getPointX() {
        return pointX;
    }

    public float getPointY() {
        return pointY;
    }

    public void setPointX(float pointX) {
        this.pointX = pointX;
    }

    public void setPointY(float pointY) {
        this.pointY = pointY;
    }  
    
    static public Comparator<Point2> xPosition;
    
    static {
        xPosition = new Comparator<Point2>(){
            @Override
            public int compare(Point2 p1, Point2 p2){
                return (p1.getPointX() < p2.getPointX() ) ? -1: (p1.getPointX() > p2.getPointX()) ? 1:0 ;
            }
        };
    }
    
    // the point with maximum Y
    public static Point2 getMaxY(ArrayList<Point2> points) 
    {
        double maxY = points.get(0).getPointY();   // start with the first value
        Point2 maxPoint = points.get(0);
        for (int i=1; i<points.size(); i++) {
            if (points.get(i).getPointY() > maxY) 
            {
                maxY = points.get(i).getPointY(); // new maximum
                maxPoint = points.get(i);
            }
        }
        return maxPoint;
    }

    // a method to find the Point with the minimum y 
    public static Point2 getMinY(ArrayList<Point2> points)
    {  
          double minValue = points.get(0).getPointY();
          Point2 minPoint = points.get(0);
          for(int i=1;i<points.size();i++){  
            if(points.get(i).getPointY() < minValue)
            {
                minPoint = points.get(i);
                minValue = points.get(i).getPointY();  
            }  
          }  
          return minPoint;  
    } 
    
    // if the equation is equal to 0, the points are collinear
    // the method returns the determinant of the point matrix
    // This determinant tells how far point 'c' is from vector ab and on which side
    // it is 
    // < 0 if the point 'c' is below the line (assumption : horizontal line) 
    // > 0 if the point 'c' is above the line 
    public static float isCollinear(Point2 a, Point2 b, Point2 c)
    {
         return ((b.getPointX() - a.getPointX())*(c.getPointY() - a.getPointY()) - 
                 (b.getPointY() - a.getPointY())*(c.getPointX() - a.getPointX()));
    }
}
