package btviewer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private static final int WIDTH = 1300;
    private static final int HEIGHT = 900;

    private JFrame frm;
    private BTPanel btPanel;
    private Tree currentTree;

    private TreeGenerator treeGenerator;
    private TreeCreator treeCreator;

    private JButton showRandomTreeBtn;
    private JButton showFullTreeBtn;
    private JButton showCreatorDialogBtn;

    private JButton searchValueBtn;

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
        showCreatorDialogBtn = new JButton("Создать дерево вручную");
        controlPanel.add(showCreatorDialogBtn);

        controlPanel.add(Box.createHorizontalStrut(10));
        searchValueBtn = new JButton("Найти значение");
        controlPanel.add(searchValueBtn);

        btPanel = new BTPanel();

        contentPane.add(controlPanel, BorderLayout.NORTH);
        contentPane.add(btPanel, BorderLayout.CENTER);
        frm.setContentPane(contentPane);

        treeGenerator = new TreeGenerator();
        treeCreator = new TreeCreator(frm, btPanel);

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

        showCreatorDialogBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTree = new Tree();
                btPanel.showTree(currentTree);
                treeCreator.showCreatorDialog(currentTree);
            }
        });

        searchValueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Получаем значение от пользователя
                int value;
                while (true) {
                    String str = JOptionPane.showInputDialog(frm, "Введите целое число:", "", JOptionPane.QUESTION_MESSAGE);
                    if (str == null) return;
                    str = str.trim();
                    try {
                        value = Integer.parseInt(str);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frm, "Введите корректное значение", "", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    break;
                }

                //Ищем полученное значение в дереве
                List<Node> pathToValue;
                try {
                    pathToValue = currentTree.searchPathToValue(value);
                } catch (CannotFindValueException e1) {
                    JOptionPane.showMessageDialog(frm, "Введенного значения нет в дереве", "", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Отображаем путь к узлу
                btPanel.showTreeWithPath(currentTree, pathToValue);
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
