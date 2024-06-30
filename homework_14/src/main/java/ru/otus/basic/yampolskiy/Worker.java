package ru.otus.basic.yampolskiy;

public abstract class Worker implements Runnable{
    final double[] array;
    int startIndex;
    int endIndex;

    public Worker(double[] array) {
        this.array = array;
    }

    public Worker(double[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
