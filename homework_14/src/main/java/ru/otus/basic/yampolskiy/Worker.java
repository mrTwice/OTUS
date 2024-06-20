package ru.otus.basic.yampolskiy;

public class Worker implements Runnable{
    private final double[] array;
    private final int startIndex;
    private final int endIndex;

    public Worker(double[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        long init = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() +": "+"Запуск расчетной задачи.");
        for (int i = startIndex; i < endIndex; i++) {
                array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
        }
        System.out.println(Thread.currentThread().getName() +": "+"Задача выполнена");
        long finish = System.currentTimeMillis() - init;
        System.out.printf("Поток %s выполнил работу за %s мс.\n", Thread.currentThread().getName(), finish);
    }
}
