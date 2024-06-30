package ru.otus.basic.yampolskiy;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class GroupWorkers {
    private static final int INITIAL_WORKERS_QUEUE_SIZE = 4;
    private final double[] array;
    private final int partSize;
    private final Queue<CommandWorker> workers = new LinkedBlockingQueue<>();
    private CountDownLatch countDownLatch;

    public GroupWorkers(double[] array) {
        this.array = array;
        this.countDownLatch = new CountDownLatch(INITIAL_WORKERS_QUEUE_SIZE);
        this.partSize = array.length/ INITIAL_WORKERS_QUEUE_SIZE;
        initWorkers(INITIAL_WORKERS_QUEUE_SIZE);

    }

    public void start() {
        try {
            System.out.println(this.getClass().getSimpleName() +": Запуск расчетной задачи.\n");
            long init = System.currentTimeMillis();
            workers.forEach(commandWorker -> new Thread(commandWorker).start());
            countDownLatch.await();
            long finish = System.currentTimeMillis() - init;
            System.out.printf("Группа выполнила расчет за %s мс.\n\n", finish);
        } catch (InterruptedException e) {
            System.out.println("Что-то пошло не так");;
        }
    }

    public void initWorkers(int queueSize) {
        for (int i =0; i < queueSize; i++) {
            int startIndex = i * partSize;
            int endIndex = (i == queueSize - 1) ? array.length : (i + 1) * partSize;
            CommandWorker commandWorker = new CommandWorker(array, countDownLatch);
            commandWorker.setStartIndex(startIndex);
            commandWorker.setEndIndex(endIndex);
            workers.add(commandWorker);
        }
    }
}
