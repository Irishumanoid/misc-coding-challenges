package JavaGraphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * 1. get grid layout (done)
 * 2. figure out how to add rectangles and set their color (done)
 * 3. populate pixel list (sample data just to see if it renders correctly) (done)
 * 4. pass in array of polygon points (these will populate the pixel list)
 * 5. algorithm to calculate %area of polygon within bounded region
 */

public class Pixels {
    private int xSize;
    private int ySize;
    private List<Pixel> pixels;
    private JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pixels graph = new Pixels(500, 500, new ArrayList<>(Arrays.asList(
                new Pixel(0, 0, 0),
                new Pixel(300,300, 0)
            )));
            
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    for (int i = 0; i < graph.getX(); i += 5) {
                        g.drawLine(i, 0, i, graph.getY());
                    }
    
                    for (int j = 0; j < graph.getY(); j += 5) {
                        g.drawLine(0, j, graph.getX(), j);
                    }

                    //test fill
                    g.setColor(new Color(250, 50, 250));
                    g.fillRect(100, 100, 5, 5);

                    graph.getPixels().forEach((pixel) -> {
                        g.fillRect(pixel.xCoord, pixel.yCoord, 5, 5);
                    });
                }
            };
            
            graph.getFrame().add(panel);
            panel.setLayout(null); //for custom painting
        });
    }

    public Pixels(int xSize, int ySize, List<Pixel> pixels) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.pixels = pixels;
        this.frame = new JFrame("Polygon renderer");
        this.frame.setSize(xSize, ySize);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);

        //center on screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        this.frame.addWindowListener(new WindowAdapter() { 
            @Override
            public void windowClosing(WindowEvent e) { 
                System.exit(0); 
            } 
        }); 
    }

    public JFrame getFrame() {
        return frame;
    }

    public List<Pixel> getPixels() {
        return pixels;
    }

    public int getX() { return xSize; }
    public int getY() { return ySize; }

    public void drawRectangle(int x, int y, int width, int height) {
        Graphics graphics = frame.getGraphics();
        graphics.drawRect(x, y, width, height);
        graphics.fillRect(x, y, width, height);
    }
}
