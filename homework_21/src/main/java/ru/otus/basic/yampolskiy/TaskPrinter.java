package ru.otus.basic.yampolskiy;

public class TaskPrinter {
    private final Object monitor = new Object();
    private char nextChar = 'A';

    public void printChar(char symbol) throws InterruptedException {
        synchronized (monitor) {
            while (symbol != nextChar) {
                monitor.wait();
            }
            System.out.print(symbol);
            nextChar = getNextChar(nextChar);
            monitor.notifyAll();
        }
    }

    private char getNextChar(char currentChar) {
        switch (currentChar) {
            case 'A':
                return 'B';
            case 'B':
                return 'C';
            case 'C':
                return 'A';
            default:
                throw new IllegalArgumentException("Unexpected value: " + currentChar);
        }
    }
}
