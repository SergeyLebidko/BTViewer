package btviewer;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BTPanel extends JPanel {

    private static final double X_FACTOR_TO_SINGLE_STR = 0.3;
    private static final double Y_FACTOR_TO_SINGLE_STR = 0.4;

    private static final double X_FACTOR_TO_DOUBLE_STR = 0.55;
    private static final double Y_FACTOR_TO_DOUBLE_STR = 0.4;

    private static final int NODE_RADIUS = 15;

    private static final Color NODE_BACKGROUND_COLOR = new Color(60, 80, 255);
    private static final Color LINE_COLOR = new Color(25, 150, 255);

    private static final Color NODE_IN_PATH_BACKGROUND_COLOR = new Color(255, 0, 13);
    private static final Color LINE_IN_PATH_COLOR = new Color(255, 121, 123);

    private static final Color NODE_TEXT_COLOR = new Color(255, 255, 255);
    private static final Font NODE_TEXT_FONT = new Font(null, Font.BOLD, 16);

    private Tree tree;
    private List<Node> path;
    private Graphics2D g2d;

    public BTPanel() {
        setLayout(null);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        tree = null;
        path = new LinkedList<>();
        g2d = null;
    }

    public void showTree(Tree tree) {
        this.tree = tree;
        path.clear();
        repaint();
    }

    public void showTreeWithPath(Tree tree, List<Node> path) {
        this.tree = tree;
        this.path.clear();
        this.path.addAll(path);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;

        //Если список отсутствует или пуст - рисовать нечего. Просто выходим из метода
        if (tree == null || tree.isEmptyTree()) return;

        //Определяем координаты корневого узла
        int x = getWidth() / 2;
        int y = (getHeight() / Tree.MAX_LEVEL) / 2;

        recursivePaint(tree.getRoot(), x, y, 1);
    }

    private void recursivePaint(Node node, int x, int y, int level) {
        //Объявляем вспомогательные переменные
        int xChild, yChild;

        //Если у него есть левый потомок - определяем его координаты и рисуем линию до него
        if (node.getLeft() != null) {
            yChild = y + (getHeight() / Tree.MAX_LEVEL);
            xChild = (int) (x - (getWidth() / (Math.pow(2, level))) / 2);
            if (path.contains(node) & path.contains(node.getLeft())) {
                paintLine(x, y, xChild, yChild, LINE_IN_PATH_COLOR);
            } else {
                paintLine(x, y, xChild, yChild, LINE_COLOR);
            }
            recursivePaint(node.getLeft(), xChild, yChild, level + 1);
        }

        //Если у него есть правый потомок - определяем его координаты и рисуем линию до него
        if (node.getRight() != null) {
            yChild = y + (getHeight() / Tree.MAX_LEVEL);
            xChild = (int) (x + (getWidth() / (Math.pow(2, level))) / 2);
            if (path.contains(node) & path.contains(node.getRight())) {
                paintLine(x, y, xChild, yChild, LINE_IN_PATH_COLOR);
            } else {
                paintLine(x, y, xChild, yChild, LINE_COLOR);
            }
            recursivePaint(node.getRight(), xChild, yChild, level + 1);
        }

        //Рисуем текущий узел
        if (path.contains(node)) {
            paintNode(node, x, y, NODE_IN_PATH_BACKGROUND_COLOR);
        } else {
            paintNode(node, x, y, NODE_BACKGROUND_COLOR);
        }
    }

    private void paintNode(Node node, int x, int y, Color backColor) {
        String content = node.getValue() + "";

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
        g2d.setColor(backColor);
        g2d.fillOval(xCenterOval, yCenterOval, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

        //Рисуем текст внутри овала
        g2d.setFont(NODE_TEXT_FONT);
        g2d.setColor(NODE_TEXT_COLOR);
        g2d.drawString(content, xTextPos, yTextPos);
    }

    private void paintLine(int x1, int y1, int x2, int y2, Color lineColor) {
        g2d.setColor(lineColor);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x1, y1, x2, y2);
    }

}
