package btviewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private static final int WIDTH = 1300;
    private static final int HEIGHT = 900;

    private JFrame frm;
    private BTPanel btPanel;
    private Tree currentTree;
    private TreeGenerator treeGenerator;

    private JButton showRandomTreeBtn;
    private JButton showFullTreeBtn;

    public GUI() {
        frm = new JFrame("BTViewer");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(WIDTH, HEIGHT);
        frm.setResizable(false);
        int xPos = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2;
        int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2;
        frm.setLocation(xPos, yPos);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        showRandomTreeBtn = new JButton("Случайное дерево");
        controlPanel.add(showRandomTreeBtn);
        showFullTreeBtn = new JButton("Полное дерево");
        controlPanel.add(showFullTreeBtn);

        btPanel = new BTPanel();

        contentPane.add(controlPanel, BorderLayout.NORTH);
        contentPane.add(btPanel, BorderLayout.CENTER);
        frm.setContentPane(contentPane);

        treeGenerator = new TreeGenerator();

        showRandomTreeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTree = treeGenerator.getRandomTree();
                btPanel.showTree(currentTree);
            }
        });

        showFullTreeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTree = treeGenerator.getFulleTree();
                btPanel.showTree(currentTree);
            }
        });
    }

    public void showGui() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frm.setVisible(true);
            }
        });
    }

}
