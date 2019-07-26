package btviewer;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private static final int WIDTH = 1300;
    private static final int HEIGHT = 900;

    private JFrame frm;

    public GUI() {
        frm = new JFrame("BTViewer");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(WIDTH, HEIGHT);
        int xPos = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2;
        int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2;
        frm.setLocation(xPos, yPos);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        frm.setContentPane(contentPane);
    }

    public void showGui() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frm.setVisible(true);
            }
        });
    }

}
