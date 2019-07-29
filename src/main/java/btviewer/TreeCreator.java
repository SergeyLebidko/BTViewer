package btviewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TreeCreator {

    private static final int DIALOG_WIDTH = 550;
    private static final int DIALOG_HEIGHT = 550;

    private JDialog dialog;
    private Tree tree;
    private BTPanel btPanel;
    private JButton[] buttons;

    public TreeCreator(JFrame frm, BTPanel btPanel) {
        dialog = new JDialog(frm, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        dialog.setResizable(false);
        int xPos = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - DIALOG_WIDTH / 2;
        int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - DIALOG_HEIGHT / 2;
        dialog.setLocation(xPos, yPos);

        this.btPanel = btPanel;

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(0, 10));

        buttons = new JButton[Tree.MAX_RANGE_VALUE - Tree.MIN_RANGE_VALUE + 1];
        int index = 0;
        for (int value = Tree.MIN_RANGE_VALUE; value <= Tree.MAX_RANGE_VALUE; value++) {
            buttons[index] = new JButton(value + "");
            buttons[index].setActionCommand(value + "");
            buttons[index].addActionListener(buttonListener);
            contentPane.add(buttons[index]);
            index++;
        }

        dialog.setContentPane(contentPane);
    }

    public void showCreatorDialog(Tree tree) {
        this.tree = tree;
        for (JButton button : buttons) {
            button.setEnabled(true);
        }
        dialog.setVisible(true);
    }

    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int value = Integer.parseInt(button.getActionCommand());
            try {
                tree.addValueToTree(value);
                btPanel.showTree(tree);
            } catch (OutOfLevelException e1) {
                JOptionPane.showMessageDialog(dialog, "<html>Эначение " + value + " нельзя добавить в дерево.<br>Будет превышема максимально допустимая глубина");
            } catch (DublicateContentException e1) {
            }
            button.setEnabled(false);
        }
    };

}
