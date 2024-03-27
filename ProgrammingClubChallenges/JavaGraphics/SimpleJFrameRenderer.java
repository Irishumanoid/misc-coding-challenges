package JavaGraphics;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.*;

public class SimpleJFrameRenderer {
    public static final int frameX = 300;
    public static final int frameY = 200;

    public static void main(String[] args) {

        // Create a new JFrame
        JFrame frame = new JFrame("Simple JFrame Renderer");
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < frameX; i += 5) {
                    g.drawLine(i, 0, i, 200);
                }

                for (int j = 0; j < frameY; j += 5) {
                    g.drawLine(0, j, 300, j);
                }
            }
        };
        panel.setLayout(null); //for custom painting


        JLabel label = new JLabel("Hello, World!", SwingConstants.CENTER);
        label.setBounds(0, 0, 300, 200);

        panel.add(label);
        frame.add(panel);

        frame.setSize(frameX, frameY);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}
