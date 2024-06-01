package ru.otus.basic.yampolskiy;

public class Tree {
    private Node root;

    public boolean add(int value) {
        if (root == null) { // Если корня нет
            root = new Node(value, Color.BLACK); // устанавливаем значение для корня, цвет корня всегда черный
            return true;
        } else {
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.BLACK;
            return  result;
        }
    }

    private boolean addNode(Node currentNode, int value) {
        if (currentNode.value == value) {
            return false;
        } else {
            if (currentNode.value > value) {
                if (currentNode.left != null) {
                    boolean result = addNode(currentNode.left, value);
                    currentNode.left = rebalance(currentNode.left);
                    return result;
                } else {
                    currentNode.left = new Node(value, Color.RED);
                    return true;
                }
            } else {
                if (currentNode.right != null) {
                    boolean result = addNode(currentNode.right, value);
                    currentNode.right = rebalance(currentNode.right);
                    return result;
                } else {
                    currentNode.right = new Node(value, Color.RED);
                    return true;
                }
            }
        }
    }

    private void colorSwipe(Node node) {
        node.left.color = Color.BLACK;
        node.right.color = Color.BLACK;
        node.color = Color.RED;
    }

    private Node rightTurn(Node oldRootNode) {
        Node newRootNode = oldRootNode.right;   // Новым корнем станет правый ребенок старого корня
        Node middle = oldRootNode.right.left;   // Средний элемент, который был левым потомком правого элемента старого корня
        newRootNode.left = oldRootNode;         // Левым элементом нового корня становится старый корень
        oldRootNode.right = middle;             // Правым элементов старого корня становится средний элемент
        newRootNode.color = oldRootNode.color;  // новый корень наследует цвет старого
        oldRootNode.color = Color.RED;          // старый корень становится красным
        return newRootNode;
    }

    private Node leftTurn(Node oldRootNode) {
        Node newRootNode = oldRootNode.left;    // В новый корень кладем левый элемент старого корня
        Node middle = oldRootNode.left.right;   // Средним элементом будет правый потомок левого элемента старого корня
        newRootNode.right = oldRootNode;        // Правым ребенком нового корня становится старый корень
        oldRootNode.left = middle;              // Левым потомком правого элемента нового корня становится средний элемент
        newRootNode.color = oldRootNode.color;  // новый корень наследует цвет старого
        oldRootNode.color = Color.RED;          // старый корень становится красным
        return newRootNode;
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.right != null && result.right.color == Color.RED &&
                    (result.left == null || result.left.color == Color.BLACK)) {
                needRebalance = true;
                result = rightTurn(result);
            }
            if (result.left != null && result.left.color == Color.RED &&
                    result.left.left != null && result.left.left.color == Color.RED) {
                needRebalance = true;
                result = leftTurn(result);
            }
            if (result.left != null && result.left.color == Color.RED &&
                    result.right != null && result.right.color == Color.RED) {
                needRebalance = true;
                colorSwipe(result);
            }
        } while (needRebalance);
        return result;
    }


    public class Node {
        private int value;
        private Node left;
        private Node right;
        private Color color;

        public Node(int value) {
            this.value = value;
            color = Color.RED;
        }

        public Node(int value, Color color) {
            this.value = value;
            this.color = color;
        }

    }

    private enum Color {RED, BLACK}
}
