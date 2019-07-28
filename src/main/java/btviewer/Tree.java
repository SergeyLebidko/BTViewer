package btviewer;

public class Tree {

    private static final int MAX_LEVEL = 6;

    private Node root;

    public Tree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void addContentToTree(int content) throws OutOfLevelException {
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
            if (content < current.getContent()) {
                if (current.getLeft() == null) {
                    current.setLeft(new Node(content));
                    break;
                }
                current = current.getLeft();
                level++;
                continue;
            }
            if (content > current.getContent()) {
                if (current.getRight() == null) {
                    current.setRight(new Node(content));
                    break;
                }
                current = current.getRight();
                level++;
                continue;
            }
        }
    }

}
