/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

import computacionalgeometry.Polygon.PointLocation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author paulaceccon
 */
public class GUI extends JPanel {
    
    private JButton locatePointRT; 
    private JButton locatePointRI; 
    private JButton triangulate; 
    private JButton convexHull; 
    private JButton mergeHull; 
    private JButton linesIntersection;
    private JButton clear;
    
    private JPanel panel;
    private JPanel optionsPanel;
    private JPanel canvas;

    public JPanel getMainPanel() {
        return this.panel;
    }
    
    public GUI () {
        panel = new JPanel();
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(7, 1));
        
        linesIntersection = new JButton("<html><center>Verify if the Polygon is Simple</center></html>");
        linesIntersection.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                if (Polygon.IsCounterclockwise(Data.getPolygonVertex()))               
                    Collections.reverse(Data.getPolygonVertex());
                Point2 p = new Point2(-1F, -1F);
                boolean i = Polygon.CheckPolygonIsSimple(Data.getPolygonEdges(), p);
                if (i) {

                    JOptionPane.showMessageDialog(linesIntersection, "The polygon is not simple.\n"
                            + "Intersection at point: ("+String.format("%.2f", p.getPointX())+";"+String.format("%.2f", p.getPointY())+")");
                }
                else
                    JOptionPane.showMessageDialog(linesIntersection, "The polygon is simple.");   
            }        
        });
         
        
        locatePointRT = new JButton("<html><center>Locate Point Using Ray Crossing</html></center>");
        locatePointRT.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {       
                if (Polygon.IsCounterclockwise(Data.getPolygonVertex()))               
                    Collections.reverse(Data.getPolygonVertex());
                PointLocation location = Polygon.LocatePointUsingRayTracing(Data.getMainPoint(), Data.getPolygonVertex());
                JOptionPane.showMessageDialog(locatePointRT, "The point is "+location+" the polygon.");
            }        
        });
        
        locatePointRI = new JButton("<html><center>Locate Point Using Rotaion Index</html></center>");
        locatePointRI.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {     
                if (Polygon.IsCounterclockwise(Data.getPolygonVertex()))               
                    Collections.reverse(Data.getPolygonVertex());
                PointLocation location = Polygon.LocatePointUsingRotationIndex(Data.getMainPoint(), Data.getPolygonVertex());
                JOptionPane.showMessageDialog(locatePointRI, "The point is "+location+" the polygon.");
            }        
        });
        
        triangulate = new JButton("<html><center>Ear Clipping Triangulation</html></center>");
        triangulate.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Polygon.IsCounterclockwise(Data.getPolygonVertex()))               
                    Collections.reverse(Data.getPolygonVertex());
                Data.clearTriangulation();
                ArrayList<Point2> v = new ArrayList(Data.getPolygonVertex()); 
                Polygon.EarClippingTriangulation(v, 1);
            }        
        });
        
        convexHull = new JButton("<html><center>Graham Convex Hull</html></center>");
        convexHull.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Polygon.IsCounterclockwise(Data.getPolygonVertex()))               
                    Collections.reverse(Data.getPolygonVertex());
                Data.clearConvexHull();
                ArrayList<Point2> v = new ArrayList(Data.getPolygonVertex()); 
                Polygon.GrahamConvexHull(v);
            }        
        });
        
        mergeHull = new JButton("<html><center>Merge Hull</html></center>");
        mergeHull.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Polygon.IsCounterclockwise(Data.getPolygonVertex()))               
                    Collections.reverse(Data.getPolygonVertex());
                Data.clearConvexHull();
                ArrayList<Point2> v = new ArrayList(Data.getPolygonVertex()); 
                Polygon.MergeHull(v);
            }        
        });
        
        clear = new JButton("Clear Canvas");
        clear.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.clearData();
            }        
        });

        optionsPanel.add(linesIntersection);
        optionsPanel.add(locatePointRT);
        optionsPanel.add(locatePointRI);
        optionsPanel.add(triangulate);
        optionsPanel.add(convexHull);
        optionsPanel.add(mergeHull);
        optionsPanel.add(clear);
        optionsPanel.setPreferredSize(new Dimension(150, 600));
        
        MouseHandler mouseHandler = new MouseHandler();
        
        canvas = new Canvas();
        canvas.setBackground(Color.LIGHT_GRAY);
        canvas.setPreferredSize(new Dimension (650, 600));
        canvas.addMouseListener(mouseHandler);
        canvas.addMouseMotionListener(mouseHandler);
             
        panel.setLayout(new BorderLayout());
        
        panel.add(canvas, BorderLayout.CENTER);
        panel.add(optionsPanel, BorderLayout.EAST);
        //panel.add(new Box.Filler(null, new Dimension(0, 50), null), BorderLayout.SOUTH);
    }
    
}
