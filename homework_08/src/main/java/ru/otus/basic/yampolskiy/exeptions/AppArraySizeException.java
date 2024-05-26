package ru.otus.basic.yampolskiy.exeptions;

public class AppArraySizeException extends Exception{

    private String[][] array;

    public AppArraySizeException( String[][] array) {
        this.array = array;
    }

    public String getMessage() {
        return "Передан массив некорректной размерности: " + "[" + array[0].length + "," + array[1].length + "]";
    }
}
