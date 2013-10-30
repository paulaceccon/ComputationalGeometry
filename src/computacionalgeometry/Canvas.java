/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author paulaceccon
 */
public class Canvas extends JPanel {
    
    private static int radious = 10;

    Stroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 3, 1 }, 0);
    
    final static Color mainPointColor = Color.blue;
    final static Color currentPointColor = Color.orange;
    final static Color vertexColor = Color.red;
    final static Color lineColor = Color.white;
    final static Color pointColor = Color.black;
    final static Color infoColor = Color.yellow;
    final static Color triangulationColor = Color.cyan;
    final static Color convexHullColor = Color.green;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Font textFont = new Font("Arial", Font.BOLD, 12);  
        g2d.setFont(textFont); 
        
        DrawMainPoint(g2d);
        DrawCurrentPoint(g2d);
        DrawPolygonVertex(g2d);
        DrawPolygonEdges(g2d);
        DrawTriangulationEdges(g2d);
        DrawConvexHullEdges(g2d);
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        repaint();
    }
    
    public void DrawCurrentPoint(Graphics2D g2d) {
        Point2D currentPoint = Data.getVertexPoint();
        if (currentPoint != null) {
            g2d.setColor(currentPointColor);
            g2d.fill(new Ellipse2D.Float(currentPoint.getPointX()-radious/2F, currentPoint.getPointY()-radious/2F, radious, radious));
            String position = Float.toString(currentPoint.getPointX())+","+Float.toString(currentPoint.getPointY());
            g2d.setColor(pointColor);
            g2d.drawString(position, currentPoint.getPointX(), currentPoint.getPointY());
        }
    }
    
    public void DrawMainPoint(Graphics2D g2d) {
        Point2D mainPoint = Data.getMainPoint();
        if (mainPoint != null) {
            g2d.setColor(mainPointColor);
            g2d.fill(new Ellipse2D.Float(mainPoint.getPointX()-radious/2F, mainPoint.getPointY()-radious/2F, radious, radious));
            String position = Float.toString(mainPoint.getPointX())+","+Float.toString(mainPoint.getPointY());
            g2d.setColor(pointColor);
            g2d.drawString(position, mainPoint.getPointX(), mainPoint.getPointY());
        }
    }
    
    public void DrawPolygonVertex(Graphics2D g2d) {
        ArrayList<Point2D> polygonVertex = Data.getPolygonVertex();
        for (int i = 0; i < polygonVertex.size(); i++) {
            g2d.setColor(vertexColor);
            g2d.fill(new Ellipse2D.Float(polygonVertex.get(i).getPointX()-radious/2F, polygonVertex.get(i).getPointY()-radious/2F, radious, radious));  
            String position = Float.toString(polygonVertex.get(i).getPointX())+","+Float.toString(polygonVertex.get(i).getPointY());
            g2d.setColor(pointColor);
            g2d.drawString(position, polygonVertex.get(i).getPointX(), polygonVertex.get(i).getPointY());
        }
    }
    
    public void DrawPolygonEdges (Graphics2D g2d) {
        ArrayList<Line> polygonEdges = Data.getPolygonEdges();
        for (int i = 0; i < polygonEdges.size(); i++) {
            float x1 = polygonEdges.get(i).GetStartPoint().getPointX(), y1 = polygonEdges.get(i).GetStartPoint().getPointY();
            float x2 = polygonEdges.get(i).GetEndPoint().getPointX(), y2 = polygonEdges.get(i).GetEndPoint().getPointY();
            g2d.setColor(lineColor);
            g2d.draw(new Line2D.Float(x1, y1, x2, y2)); 
            g2d.setColor(infoColor);
            g2d.drawString(Integer.toString(i), (x1+x2)/2, (y1+y2)/2);
        }
    }
    
    public void DrawTriangulationEdges (Graphics2D g2d) {
        ArrayList<Line> triangulationEdges = Data.getTriangulationEdges();
        for (int i = 0; i < triangulationEdges.size(); i++) {
            float x1 = triangulationEdges.get(i).GetStartPoint().getPointX(), y1 = triangulationEdges.get(i).GetStartPoint().getPointY();
            float x2 = triangulationEdges.get(i).GetEndPoint().getPointX(), y2 = triangulationEdges.get(i).GetEndPoint().getPointY();
            g2d.setColor(triangulationColor);
            g2d.draw(new Line2D.Float(x1, y1, x2, y2)); 
        } 
    }
    
    public void DrawConvexHullEdges (Graphics2D g2d) {
        ArrayList<Line> convexHullEdges = Data.getConvexHullEdges();
        for (int i = 0; i < convexHullEdges.size(); i++) {
            float x1 = convexHullEdges.get(i).GetStartPoint().getPointX(), y1 = convexHullEdges.get(i).GetStartPoint().getPointY();
            float x2 = convexHullEdges.get(i).GetEndPoint().getPointX(), y2 = convexHullEdges.get(i).GetEndPoint().getPointY();
            g2d.setColor(convexHullColor);
            g2d.draw(new Line2D.Float(x1, y1, x2, y2)); 
        } 
    }
}
