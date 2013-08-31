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

    Vector2(Point2 p0, Point2 p1) {
        p = new Point2(p1.getPointX()-p0.getPointX(), p1.getPointY()-p0.getPointY());
    }

    public Point2 getPoint() {
        return p;
    }
    
    public float getX() {
        return getPoint().getPointX();
    }
    
    public float getY() {
        return getPoint().getPointY();
    }      
    
    public float DotProduct (Vector2 p1)
    {
        return this.getX() * p1.getX() + this.getY() + p1.getY();
    }
    
    public float CrossProduct (Vector2 p1)
    {
        return this.getX() * p1.getY() - this.getY() * p1.getX();
    }
    
    public float Magnitude (Vector2 p0)
    {
        return (float) (Math.sqrt(Math.pow(p0.getX(), 2) + Math.pow(p0.getY(), 2)));
    }
    
    public float OrientedAngle (Vector2 v) {
        float angle = (float) (Math.acos((this.DotProduct(v))/(Magnitude(v) * Magnitude(this))));
        
        if (this.CrossProduct(v) >= 0) {
            return angle;
        }
        else {
            return -angle;
        }
    }
    
    public boolean IsCounterclockwise (Vector2 v) {
	float crossProduct = this.CrossProduct(v);
	return crossProduct > 0;
    }
}