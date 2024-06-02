package ru.otus.basic.yampolskiy;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(67,34,5,98,2,56,32,61,65,87,75,45,57,63,66);

        Tree tree = new Tree(list);

        System.out.println(list);
        System.out.println(tree.getSortedList());
        System.out.println();

        System.out.println(tree.find(45));
        System.out.println();
        System.out.println();

        TreePrinter.print(tree);
        // TODO: разобраться со случаем, когда дерево симметричное и все его элементы черные.
    }
}