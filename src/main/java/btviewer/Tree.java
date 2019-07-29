package btviewer;

import java.util.LinkedList;
import java.util.List;

public class Tree {

    public static final int MIN_RANGE_VALUE = 0;
    public static final int MAX_RANGE_VALUE = 99;
    public static final int MAX_LEVEL = 6;

    private Node root;

    public Tree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void addValueToTree(int content) throws OutOfLevelException, DublicateContentException {
        //Если дерево еще пустое, то просто создаем его корневой узел
        if (root == null) {
            root = new Node(content);
            return;
        }

        //Если дерево не пустое, то ищем место для нового узла. При этом узел нельзя добавить, если его уровень больше максимального
        int level = 1;
        Node current = root;
        while (true) {
            if (level == MAX_LEVEL) throw new OutOfLevelException();
            if (content < current.getValue()) {
                if (current.getLeft() == null) {
                    current.setLeft(new Node(content));
                    break;
                }
                current = current.getLeft();
                level++;
                continue;
            }
            if (content > current.getValue()) {
                if (current.getRight() == null) {
                    current.setRight(new Node(content));
                    break;
                }
                current = current.getRight();
                level++;
                continue;
            }
            if (content == current.getValue()) {
                throw new DublicateContentException();
            }
        }
    }

    public List<Node> searchPathToValue(int value) throws CannotFindValueException {
        LinkedList<Node> pathToValue = new LinkedList<>();

        Node current = root;
        while (true) {
            if (current == null) throw new CannotFindValueException();

            //Включаем текущий узел в список посещенных узлов
            pathToValue.add(current);
            if (current.getValue() == value) {
                break;
            }

            //В зависимости от переданного значения переходим к правому или левому потомку
            if (value < current.getValue()) {
                current = current.getLeft();
                continue;
            }
            if (value > current.getValue()) {
                current = current.getRight();
                continue;
            }
        }

        return pathToValue;
    }

    public boolean isEmptyTree() {
        return root == null;
    }

}
