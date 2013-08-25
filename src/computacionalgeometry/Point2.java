/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

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
    
}
