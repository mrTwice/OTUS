package ru.otus.basic.yampolskiy;

public class PrintCharTask implements Runnable {
    private final char symbol;
    private int repeat;
    private final TaskPrinter taskPrinter;

    public PrintCharTask(char symbol, int repeat, TaskPrinter taskPrinter) {
        this.symbol = symbol;
        this.repeat = repeat;
        this.taskPrinter = taskPrinter;
    }

    @Override
    public void run() {
        while (repeat > 0) {
            try {
                taskPrinter.printChar(symbol);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repeat--;
        }
    }
}
