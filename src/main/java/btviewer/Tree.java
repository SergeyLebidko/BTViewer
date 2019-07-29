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

    public void removeNode(int value) throws CannotFindValueException {
        //Ищем узел, который будем удалять
        Node current = root;
        Node parent = null;

        while (true) {
            if (current == null) throw new CannotFindValueException();

            //Если узел найден - выходим из цикла
            if (current.getValue() == value) {
                break;
            }

            //Если узел не найден - продолжаем поиски в узлах-потомках
            if (value < current.getValue()) {
                parent = current;
                current = current.getLeft();
            }
            if (value > current.getValue()) {
                parent = current;
                current = current.getRight();
            }
        }

        //Первый случай - удаляемый узел не имеет потомков
        if (current.isNotChildren()) {
            if (parent.getLeft() == current) {
                parent.setLeft(null);
                return;
            }
            if (parent.getRight() == current) {
                parent.setRight(null);
                return;
            }
        }

        //Второй случай - удаляемый узел имеет одного потомка
        if (current.isOnlyLeftChild() | current.isOnlyRightChild()) {
            if (parent.getLeft() == current) {
                if (current.isOnlyLeftChild()) {
                    parent.setLeft(current.getLeft());
                    return;
                }
                if (current.isOnlyRightChild()) {
                    parent.setLeft(current.getRight());
                    return;
                }
            }
            if (parent.getRight() == current) {
                if (current.isOnlyLeftChild()) {
                    parent.setRight(current.getLeft());
                    return;
                }
                if (current.isOnlyRightChild()) {
                    parent.setRight(current.getRight());
                    return;
                }
            }
        }

        //Третий случай - удаляемый узел имеет двух потомков
        if (current.isBothChild()) {
            Node successor = current.getRight();
            Node parentSuccessor = current;

            while (true) {
                //Пытаемся сдвинуться влево. Делаем это до тех пор, пока возможно
                if (successor.getLeft() != null) {
                    parentSuccessor = successor;
                    successor = successor.getLeft();
                    continue;
                }
                break;
            }

            //Первый частный случай - преемник является правым узлом удаляемого узла (лувых узлов он при этом иметь не будет)
            if (successor == current.getRight()) {
                successor.setLeft(current.getLeft());
                if (parent.getRight() == current) {
                    parent.setRight(successor);
                    return;
                }
                if (parent.getLeft() == current) {
                    parent.setLeft(successor);
                    return;
                }
            }

            //Второй частный случай - преемник является листовым узлом
            if (successor.isNotChildren()) {
                successor.setLeft(current.getLeft());
                successor.setRight(current.getRight());
                parentSuccessor.setLeft(null);
                if (parent.getRight() == current) {
                    parent.setRight(successor);
                    return;
                }
                if (parent.getLeft() == current) {
                    parent.setLeft(successor);
                    return;
                }
            }

            //Третий частный случай - преемник имеет потомков справа (потомков слева он иметь не может, иначе он не был бы преемником)
            if (successor.isOnlyRightChild()) {
                successor.setLeft(current.getLeft());
                parentSuccessor.setLeft(successor.getRight());
                successor.setRight(current.getRight());
                if (parent.getRight() == current) {
                    parent.setRight(successor);
                    return;
                }
                if (parent.getLeft() == current) {
                    parent.setLeft(successor);
                    return;
                }
            }
        }

    }

    public boolean isEmptyTree() {
        return root == null;
    }

}
