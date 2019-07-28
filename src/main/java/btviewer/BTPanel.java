package btviewer;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class BTPanel extends JPanel {

    private static final double X_FACTOR_TO_SINGLE_STR = 0.3;
    private static final double Y_FACTOR_TO_SINGLE_STR = 0.4;

    private static final double X_FACTOR_TO_DOUBLE_STR = 0.55;
    private static final double Y_FACTOR_TO_DOUBLE_STR = 0.4;

    private static final int NODE_RADIUS = 15;

    private static final Color NODE_BACKGROUND_COLOR = new Color(150, 150, 150);
    private static final Color NODE_TEXT_COLOR = new Color(20, 20, 20);
    private static final Font NODE_TEXT_FONT = new Font(null, Font.BOLD, 16);


    public BTPanel() {
        setLayout(null);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        System.out.println(getWidth() + " x " + getHeight());

        Node node1 = new Node(99);
        Node node2 = new Node(5);

        paintNode(g2d, node1, 100, 100);
        paintNode(g2d, node2, 300, 200);
    }

    private void paintNode(Graphics2D g2d, Node node, int x, int y) {
        String content = node.getContent() + "";

        int xCenterOval = x - NODE_RADIUS;
        int yCenterOval = y - NODE_RADIUS;

        int xTextPos = 0;
        int yTextPos = 0;

        if (content.length() == 1) {
            xTextPos = x - (int) (X_FACTOR_TO_SINGLE_STR * NODE_RADIUS);
            yTextPos = y + (int) (Y_FACTOR_TO_SINGLE_STR * NODE_RADIUS);
        }
        if (content.length() == 2) {
            xTextPos = x - (int) (X_FACTOR_TO_DOUBLE_STR * NODE_RADIUS);
            yTextPos = y + (int) (Y_FACTOR_TO_DOUBLE_STR * NODE_RADIUS);
        }

        //Рисуем овал
        g2d.setColor(NODE_BACKGROUND_COLOR);
        g2d.fillOval(xCenterOval, yCenterOval, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

        //Рисуем текст внутри товала
        g2d.setFont(NODE_TEXT_FONT);
        g2d.setColor(NODE_TEXT_COLOR);
        g2d.drawString(content, xTextPos, yTextPos);
    }

}
