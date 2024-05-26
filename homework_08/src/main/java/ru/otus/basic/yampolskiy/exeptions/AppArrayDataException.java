package ru.otus.basic.yampolskiy.exeptions;

public class AppArrayDataException extends Exception{

    private final int i;
    private final int j;

    public AppArrayDataException(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public String getMessage() {
        return "Не удалось преобразовать элемент с индексом"+  " [" + i + "," + j + "]"+" в целое число.";
    }
}
