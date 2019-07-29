package btviewer;

import java.util.LinkedList;
import java.util.Random;

public class TreeGenerator {

    private class Range {
        int a;
        int b;
        int level;

        public Range(int a, int b, int level) {
            this.a = a;
            this.b = b;
            this.level = level;
        }

    }

    public Tree getFulleTree() {
        return generateTree(true);
    }

    public Tree getRandomTree() {
        return generateTree(false);
    }

    private Tree generateTree(boolean isFullTree) {
        Tree tree = new Tree();

        //Генерируем количество узлов в создаваемом дереве
        int nodeCountLimit = (int) Math.pow(2, Tree.MAX_LEVEL) - 1;
        int nodeCount = 0;
        Random rnd = new Random();
        while (nodeCount == 0) {
            nodeCount = rnd.nextInt(nodeCountLimit + 1);
        }

        //Создаем список диапазонов и добавляем в него стартовый диапазон
        LinkedList<Range> rangeList = new LinkedList<>();
        rangeList.add(new Range(Tree.MIN_RANGE_VALUE, Tree.MAX_RANGE_VALUE, 1));

        //В цикле выбираем случайные элементы из случайных диапазонов и добавляем их в дерево
        int countAdded = 0;             //Количество добавленных в дерево вершин
        Range selectedRange;            //Диапазон, из которого будем выбирать число для добавления в дерево
        int content;                    //Выбранное число
        Range leftRange, rightRange;    //Поддиапазоны для левого и правого потомков

        //Если нужно сгенерировать полное дерево, то количество вершин должно быть равно максимальному для дерева данной глубины
        if (isFullTree) nodeCount = nodeCountLimit;

        while (countAdded < nodeCount & rangeList.size() > 0) {
            //Случайным образом выбираем диапазон из списка диапазонов

            //Если нужно генерировать полное дерево, то значение нужно выбирать строго по середине диапазона
            //Выбор диапазона для создания полного дерева также не нужно делать случайным
            if (isFullTree) {
                selectedRange = rangeList.peekLast();
                content = (selectedRange.a + selectedRange.b) / 2;
            } else {
                selectedRange = rangeList.get(rnd.nextInt(rangeList.size()));
                content = selectedRange.a + rnd.nextInt(getRangeSize(selectedRange));
            }

            //Вносим выбранное значение в дерево
            try {
                tree.addContentToTree(content);
            } catch (OutOfLevelException | DublicateContentException e) {
                e.printStackTrace();
            }
            countAdded++;

            //Удаляем использованный диапазон из списка
            rangeList.remove(selectedRange);

            //Формируем новые диапазоны для левого и правого потомков
            leftRange = new Range(selectedRange.a, content - 1, selectedRange.level + 1);
            rightRange = new Range(content + 1, selectedRange.b, selectedRange.level + 1);

            //Если диапазоны корректны - добавляем их в список
            if (isCorrectRange(leftRange)) rangeList.add(leftRange);
            if (isCorrectRange(rightRange)) rangeList.add(rightRange);
        }

        return tree;
    }

    private boolean isCorrectRange(Range range) {
        int size = range.b - range.a + 1;
        return size >= 1 & range.level <= Tree.MAX_LEVEL;
    }

    private int getRangeSize(Range range) {
        return range.b - range.a + 1;
    }

}
