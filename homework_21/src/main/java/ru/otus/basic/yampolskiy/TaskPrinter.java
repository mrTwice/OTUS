package ru.otus.basic.yampolskiy;

public class TaskPrinter {
    final Object monitor = new Object();
    private char nextChar = 'A';

    public void printChar(char symbol) throws InterruptedException {
        synchronized (monitor) {
            while (symbol != nextChar) {
                monitor.wait();
            }
            System.out.print(symbol);
            if (nextChar == 'A') {
                nextChar = 'B';
            } else if (nextChar == 'B') {
                nextChar = 'C';
            } else if (nextChar == 'C') {
                nextChar = 'A';
            }
            monitor.notifyAll();
        }
    }
}
