/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computacionalgeometry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author paulaceccon
 */
public class ComputationalGeometry {
    
    public static void main(String s[]) {
        JFrame f = new JFrame("Computational Geometry");
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setBackground(Color.white);  
        f.setContentPane(new GUI().getMainPanel());
        f.setSize(new Dimension(800, 600));
        f.setResizable(false);
        f.setVisible(true);
    }
}
