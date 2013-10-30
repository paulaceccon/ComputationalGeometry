/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author paulaceccon
 */
public class MouseHandler implements MouseListener, MouseMotionListener {
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Data.addPolygonVertex(new Point2D (e.getX(), e.getY()));
        }
        else if (e.getButton() == MouseEvent.BUTTON3) {
            Data.setMainPoint(new Point2D (e.getX(), e.getY()));      
        }
    }
    
    public float translate(float value, float inputMin, float inputMax, float outputMin, float outputMax) {
        float leftSpan = inputMax - inputMin;
        float rightSpan = outputMax - outputMin;
        float valueScaled = value - inputMin / leftSpan;
        return outputMin + (valueScaled * rightSpan);
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { 
            Data.getVertexPoint().setPointX(e.getX());
            Data.getVertexPoint().setPointY(e.getY());    
    }
}
