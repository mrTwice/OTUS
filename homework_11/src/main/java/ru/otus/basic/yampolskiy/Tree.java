package ru.otus.basic.yampolskiy;

import java.util.ArrayList;
import java.util.List;

public class Tree implements SearchTree {
    private Node root;
    private final Node NIL;

    public Tree(List<Integer> ints) {
        NIL = new Node(0, Color.BLACK);
        root = NIL;
        for (Integer i : ints) {
            add(i);
        }
    }

    public Node getRoot() {
        return root;
    }

    public Node getNilNode() {
        return NIL;
    }

    public boolean add(int value) {
        if (root == NIL) {
            root = new Node(value);
            return true;
        } else {
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.BLACK;
            return result;
        }
    }

    private boolean addNode(Node currentNode, int value) {
        if (currentNode.value == value) {
            return false;
        } else {
            boolean result;
            if (currentNode.value > value) {
                if (currentNode.left != NIL) {
                    result = addNode(currentNode.left, value);
                    currentNode.left = rebalance(currentNode.left);
                    return result;
                } else {
                    currentNode.left = new Node(value);
                    return true;
                }
            } else {
                if (currentNode.right != NIL) {
                    result = addNode(currentNode.right, value);
                    currentNode.right = rebalance(currentNode.right);
                    return result;
                } else {
                    currentNode.right = new Node(value);
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

    private Node leftTurn(Node oldRootNode) {
        Node newRootNode = oldRootNode.right;
        Node middle = oldRootNode.right.left;
        newRootNode.left = oldRootNode;
        oldRootNode.right = middle;
        newRootNode.color = oldRootNode.color;
        oldRootNode.color = Color.RED;
        return newRootNode;
    }

    private Node rightTurn(Node oldRootNode) {
        Node newRootNode = oldRootNode.left;
        Node middle = oldRootNode.left.right;
        newRootNode.right = oldRootNode;
        oldRootNode.left = middle;
        newRootNode.color = oldRootNode.color;
        oldRootNode.color = Color.RED;
        return newRootNode;
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.right != NIL && result.right.isRed() &&
                    (result.left == NIL || !result.left.isRed())) {
                needRebalance = true;
                result = leftTurn(result);
            }
            if (result.left != NIL && result.left.isRed() &&
                    result.left.left != NIL && result.left.left.isRed()) {
                needRebalance = true;
                result = rightTurn(result);
            }
            if (result.left != NIL && result.left.isRed() &&
                    result.right != NIL && result.right.isRed()) {
                needRebalance = true;
                colorSwipe(result);
            }
        } while (needRebalance);
        return result;
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
