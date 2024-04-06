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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.HashMap;

public class Pixels {
    private int xSize;
    private int ySize;
    private int width;
    private JFrame frame;
    private Map<Tuple<Integer, Integer>, Tuple<Double, Double>> intersectMap;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Tuple<Integer, Integer>> polygonCoords = Arrays.asList(
            new Tuple<>(100, 100),
            new Tuple<>(135, 120),
            new Tuple<>(140, 130)
        );

        Pixels graph = new Pixels(10, 500, 500);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < graph.getX(); i += graph.getWidth()) {
                    g.drawLine(i, 0, i, graph.getY());
                }

                for (int j = 0; j < graph.getY(); j += graph.getWidth()) {
                    g.drawLine(0, j, graph.getX(), j);
                }

                Pixel[][] out = graph.getPixels(polygonCoords);

                for (int row = 0; row < out.length; row++) {
                    for (int col = 0; col < out[0].length; col++) {
                        Pixel curPixel = out[row][col];
                        g.setColor(new Color(250, (int) Math.floor(250*(1-curPixel.intensity)), (int) Math.floor(250*(1-curPixel.intensity))));
                        g.fillRect(curPixel.xCoord*graph.getWidth(), curPixel.yCoord*graph.getWidth(), graph.getWidth(), graph.getWidth());
                    }
                }
            }
        };
            
        graph.getFrame().add(panel);
        panel.setLayout(null); //for custom painting
        });
    }

    public Pixels(int width, int xSize, int ySize) {
        this.width = width;
        this.xSize = xSize;
        this.ySize = ySize;
        this.intersectMap = new HashMap<>();
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

    public int getWidth() { return width; }
    public int getX() { return xSize; }
    public int getY() { return ySize; }
    public Map<Tuple<Integer, Integer>, Tuple<Double, Double>> getIntersectMap() { return intersectMap; }

    public Pixel[][] getPixels(List<Tuple<Integer, Integer>> polygonCoords) {
        Tuple<Integer, Integer> firstCoord = polygonCoords.get(0);
        AtomicInteger minXCoord = new AtomicInteger(firstCoord.getFirst()), maxXCoord = new AtomicInteger(firstCoord.getFirst());
        AtomicInteger minSecond = new AtomicInteger(firstCoord.getSecond()), maxSecond = new AtomicInteger(firstCoord.getSecond());

        polygonCoords.forEach((coord) -> {
            if (coord.getFirst() < minXCoord.get()) {
                minXCoord.set(coord.getFirst());
            }
            if (coord.getFirst() > maxXCoord.get()) {
                maxXCoord.set(coord.getFirst());
            }
            if (coord.getSecond() < minSecond.get()) {
                minSecond.set(coord.getSecond());
            }
            if (coord.getSecond() > maxSecond.get()) {
                maxSecond.set(coord.getSecond());
            }
        });

        List<Tuple<Double, Double>> boundingEqs = new ArrayList<>(); //slope, y-intercept
        for (int i = 0; i < polygonCoords.size(); i++) {
            Tuple<Integer, Integer> first = polygonCoords.get(i);
            Tuple<Integer, Integer> second = new Tuple<Integer,Integer>(0, 0);
            if (i == polygonCoords.size() - 1) {
                second = polygonCoords.get(0);
            } else {
                second = polygonCoords.get(i+1);
            }
            double slope = (second.getSecond() - first.getSecond()) / (second.getFirst() - first.getFirst());
            double yIntercept = first.getSecond() - slope * first.getFirst();
            boundingEqs.add(new Tuple<>(slope, yIntercept));
        }
        
        Pixel[][] pixels = new Pixel[maxXCoord.get() - minXCoord.get() + 1][maxSecond.get() - minSecond.get() + 1];
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                //pixel classification logic here
                PointType[] pointTypes = new PointType[] {
                    classifyPoint(row*width, col*width, boundingEqs),
                    classifyPoint(row*width + width, col*width, boundingEqs),
                    classifyPoint(row*width, col*width + width, boundingEqs),
                    classifyPoint(row*width + width, col*width + width, boundingEqs)
                };

                for (PointType t: pointTypes){
                    System.out.println(t);
                }
                System.out.println("---------------");
                int numInside = 0, numOutside = 0;
                for (PointType type : pointTypes) {
                    switch (type) {
                        case INSIDE:
                            numInside++;
                        case OUTSIDE:
                            numOutside++;
                        default:
                            break;
                    }
                }

                if (numInside == 4) {
                    pixels[row][col] = new Pixel(row, col, 1);
                } else if (numOutside == 4) {
                    pixels[row][col] = new Pixel(row, col, 0);
                } else {
                    pixels[row][col] = new Pixel(row, col, calculateIntensity(row, col, boundingEqs));
                }

            }
        }
        
        return pixels;
    }

    public PointType classifyPoint(int x, int y, List<Tuple<Double, Double>> boundingEqs) {
        PointType type = null;
        int numAbove = 0, numBelow = 0, numOnLine = 0;
        for (Tuple<Double, Double> line : boundingEqs) {
            System.out.println("point on line: " + line.getFirst()*x + line.getSecond());
            System.out.println("y val: " + y);
            if (y > line.getFirst()*x + line.getSecond()) {
                numAbove++;
            } else if (y < line.getFirst()*x + line.getSecond()) {
                numBelow++;
            } else {
                getIntersectMap().put(new Tuple<Integer,Integer>(x, y), line); //save intersection to hashmap
                System.out.println("slope: " + line.getFirst() + " y-intercept: " + line.getSecond());
                numOnLine++;
            }
        }

        System.out.println("num above: " + numAbove);
        System.out.println("num below: " + numBelow);
        System.out.println("num on line: " + numOnLine);
        System.out.println("-------------------------------------");

        if (numAbove == 0 || numBelow == 0) {
            type = PointType.OUTSIDE;
        } else {
            type = PointType.INSIDE;
        }

        return type;
    }
    

    //finds any intersections of square with bounding eqs to calculate percentage included
    public double calculateIntensity(int pixelX, int pixelY, List<Tuple<Double, Double>> boundingEqs) {
        return 0.5; //placeholder
    }

    private enum PointType {
        INSIDE,
        OUTSIDE
    }
}
