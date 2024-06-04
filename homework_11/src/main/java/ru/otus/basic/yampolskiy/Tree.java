package ru.otus.basic.yampolskiy;

import java.util.ArrayList;
import java.util.List;

public class Tree implements SearchTree {
    private final Node NIL = new Node(0, Color.BLACK);
    private Node root = NIL;

    public Tree(List<Integer> ints) {
        for (Integer i : ints) {
            add(i);
        }
    }

    public void add(int value) {

        root = addNode(root, value);
        root.color = Color.BLACK;

    }

    private Node addNode(Node currentNode, int value) {

        if (currentNode == NIL) return new Node(value);

        if (value < currentNode.value) currentNode.left = addNode(currentNode.left, value);
        else if (value > currentNode.value) currentNode.right = addNode(currentNode.right, value);

        if (isRed(currentNode.right) && !isRed(currentNode.left))
            currentNode = leftTurn(currentNode);

        if (isRed(currentNode.left) && isRed(currentNode.left.left))
            currentNode = rightTurn(currentNode);

        if (isRed(currentNode.left) && isRed(currentNode.right))
            colorSwipe(currentNode);

        return currentNode;
    }


    private void colorSwipe(Node node) {
        node.color = isRed(node) ? Color.BLACK : Color.RED;
        node.left.color = isRed(node.left) ? Color.BLACK : Color.RED;
        node.right.color = isRed(node.right) ? Color.BLACK : Color.RED;
    }

    private Node leftTurn(Node oldRootNode) {
        Node newRootNode = oldRootNode.right;
        oldRootNode.right = newRootNode.left;
        newRootNode.left = oldRootNode;
        newRootNode.color = oldRootNode.color;
        oldRootNode.color = Color.RED;
        return newRootNode;
    }

    private Node rightTurn(Node oldRootNode) {
        Node newRootNode = oldRootNode.left;
        oldRootNode.left = newRootNode.right;
        newRootNode.right = oldRootNode;
        newRootNode.color = oldRootNode.color;
        oldRootNode.color = Color.RED;
        return newRootNode;
    }

    @Override
    public boolean find(int element) {
        Node currentNode = root;
        while (currentNode != NIL) {
            if (currentNode.value == element) {
                return true;
            }
            if (currentNode.value > element) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
        return false;
    }

    @Override
    public List<Integer> getSortedList() {
        List<Integer> sortedList = new ArrayList<>();
        inOrderTraversal(root, sortedList);
        return sortedList;
    }

    private void inOrderTraversal(Node node, List<Integer> sortedList) {
        if (node != NIL) {
            inOrderTraversal(node.left, sortedList);
            sortedList.add(node.value);
            inOrderTraversal(node.right, sortedList);
        }
    }

    public Node getRoot() {
        return root;
    }

    public Node getNilNode() {
        return NIL;
    }

    public boolean isRed(Node node) {
        return node.isRed();
    }

    public class Node {
        private int value;
        private Node left;
        private Node right;
        private Color color;

        public Node(int value) {
            this.value = value;
            this.color = Color.RED;
            this.left = NIL;
            this.right = NIL;
        }

        public Node(int value, Color color) {
            this.value = value;
            this.color = color;
            this.left = NIL;
            this.right = NIL;
        }

        public int getValue() {
            return value;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public Color getColor() {
            return color;
        }

        public boolean isRed() {
            return color == Color.RED;
        }
    }

    private enum Color {RED, BLACK}

}


