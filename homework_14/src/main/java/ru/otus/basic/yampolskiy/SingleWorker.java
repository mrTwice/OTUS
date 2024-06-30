package ru.otus.basic.yampolskiy;

public class SingleWorker extends Worker{

    public SingleWorker(double[] array, int startIndex, int endIndex) {
        super(array, startIndex, endIndex);
    }

    @Override
    public void run() {
        System.out.println(this.getClass().getSimpleName() +": Запуск расчетной задачи.");
        long init = System.currentTimeMillis();
        for (int i = startIndex; i < endIndex; i++) {
            array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
        }
        System.out.println(this.getClass().getSimpleName() +": "+"Задача выполнена");
        long finish = System.currentTimeMillis() - init;
        System.out.printf(this.getClass().getSimpleName() + " закончил выполнять задачу в потоке %s  за %s мс.\n", Thread.currentThread().getName(), finish);
    }
}
