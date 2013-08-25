/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

import computacionalgeometry.Polygon.PointLocation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author paulaceccon
 */
public class GUI extends JPanel {
    
    private JButton locatePoint; 
    private JButton triangulate; 
    private JButton convexHull; 
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
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
        
        linesIntersection = new JButton("Verify intersection of lines");
        linesIntersection.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {               
                Point2 p = new Point2(-1F,-1F);
                boolean i = Line.Intersection(Data.getPolygonEdges().get(0), Data.getPolygonEdges().get(2), p);
                JOptionPane.showMessageDialog(locatePoint, i);
            }        
        });
         
        
        locatePoint = new JButton("Locate Point");
        locatePoint.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {               
                PointLocation location = Polygon.LocatePoint(Data.getMainPoint(), Data.getPolygonVertex());
                JOptionPane.showMessageDialog(locatePoint, location);
            }        
        });
        
        triangulate = new JButton("Triangulate");
        triangulate.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }        
        });
        
        convexHull = new JButton("Convex Hull");
        convexHull.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }        
        });
        
        clear = new JButton("Clear Canvas");
        clear.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.clearData();
            }        
        });

        optionsPanel.add(locatePoint);
        optionsPanel.add(triangulate);
        optionsPanel.add(convexHull);
        optionsPanel.add(linesIntersection);
        optionsPanel.add(clear);
        optionsPanel.setPreferredSize(new Dimension(150, 500));
        
        MouseHandler mouseHandler = new MouseHandler();
        
        canvas = new Canvas();
        canvas.setBackground(Color.LIGHT_GRAY);
        //DO NOT WORK. HEIGHT IS = 800!! 
        canvas.setPreferredSize(new Dimension (650, 500));
        canvas.addMouseListener(mouseHandler);
        canvas.addMouseMotionListener(mouseHandler);
             
        panel.setLayout(new BorderLayout());
        
        panel.add(canvas, BorderLayout.CENTER);
        panel.add(optionsPanel, BorderLayout.EAST);
    }
    
}
