package ru.otus.basic.yampolskiy;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private static final int ARRAY_SIZE = 100_000_000;
    private static final int PART_SIZE = ARRAY_SIZE / 4;
    private static final double[] firstArray = new double[ARRAY_SIZE];
    private static final double[] secondArray = new double[ARRAY_SIZE];
    private static final Queue<Worker> workers = new LinkedBlockingQueue<>();

    static {
        System.out.println("Инициализируем потоки для выполнения задачи расчета.");
        workers.add(new Worker(firstArray,0, firstArray.length));
        workers.add(new Worker(secondArray,0, PART_SIZE));
        workers.add(new Worker(secondArray,PART_SIZE, PART_SIZE * 2));
        workers.add(new Worker(secondArray,PART_SIZE * 2, PART_SIZE * 3));
        workers.add(new Worker(secondArray,PART_SIZE * 3, secondArray.length));
        System.out.println("Инициализация окончена");
        System.out.println();
    }

    public static void main(String[] args) {
        for (Worker worker: workers) {
            new Thread(worker).start();
        }
    }
}