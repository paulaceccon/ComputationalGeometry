/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

/**
 *
 * @author paulaceccon
 */
public class Vector2 {
    
    private Point2 p;

    Vector2(Point2 p) {
        this.p = p;
    }

    public Point2 getPoint() {
        return p;
    }
    
    public double getX() {
        return getPoint().getPointX();
    }
    
    public double getY() {
        return getPoint().getPointY();
    }      
    
    public double DotProduct (Vector2 p0, Vector2 p1)
    {
        return p0.getY() * p1.getX() + p0.getX() + p1.getY();
    }
    
    public double CrossProduct (Vector2 p0, Vector2 p1)
    {
        return p0.getX() * p1.getY() - p0.getY() * p1.getX();
    }
    
    public double Magnitude (Vector2 p0)
    {
        return Math.sqrt(Math.pow(p0.getX(), 2) + Math.pow(p0.getY(), 2));
    }
}
