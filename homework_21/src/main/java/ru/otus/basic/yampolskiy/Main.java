package ru.otus.basic.yampolskiy;

public class Main {
    public static void main(String[] args) {
        TaskPrinter taskPrinter = new TaskPrinter();
        new Thread(new PrintCharTask('A', 5, taskPrinter)).start();
        new Thread(new PrintCharTask('B', 5, taskPrinter)).start();
        new Thread(new PrintCharTask('C', 5, taskPrinter)).start();
    }
}