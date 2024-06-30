package ru.otus.basic.yampolskiy;

public class Main {

    private static final int ARRAY_SIZE = 200_000_000;
    private static final double[] firstArray = new double[ARRAY_SIZE];
    private static final double[] secondArray = new double[ARRAY_SIZE];

    public static void main(String[] args) {
        System.out.println();
        new Thread(new SingleWorker(firstArray,0, firstArray.length)).start();
        new GroupWorkers(secondArray).start();

    }

}