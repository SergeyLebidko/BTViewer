package btviewer;

public class TreeGenerator {

    public Tree getRandomTree() {
        Tree tree = new Tree();

        try {
            tree.addContentToTree(50);
            tree.addContentToTree(20);
            tree.addContentToTree(60);
            tree.addContentToTree(15);
            tree.addContentToTree(30);
            tree.addContentToTree(55);
            tree.addContentToTree(70);
            tree.addContentToTree(65);
            tree.addContentToTree(85);
            tree.addContentToTree(80);
            tree.addContentToTree(95);
            tree.addContentToTree(90);
            tree.addContentToTree(99);
            tree.addContentToTree(82);
            tree.addContentToTree(78);
            tree.addContentToTree(100);
        } catch (OutOfLevelException e) {
        }

        return tree;
    }

}
