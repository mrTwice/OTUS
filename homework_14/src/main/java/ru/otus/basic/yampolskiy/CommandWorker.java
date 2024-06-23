package ru.otus.basic.yampolskiy;

import java.util.concurrent.CountDownLatch;

public class CommandWorker extends Worker{

    private final CountDownLatch countDownLatch;

    public CommandWorker(double[] array, CountDownLatch countDownLatch) {
        super(array);
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.printf("%s: Запуск расчетной задачи в потоке %s.\n", this.getClass().getSimpleName(), Thread.currentThread().getName());
        for (int i = startIndex; i < endIndex; i++) {
            array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
        }
        countDownLatch.countDown();
        System.out.printf("%s: выполнил расчет задачи в потоке %s.\n", this.getClass().getSimpleName(), Thread.currentThread().getName());
    }
}
