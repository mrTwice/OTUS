package ru.otus.basic.yampolskiy;

import java.util.Arrays;

public class ArrayOperation {

    public int[] arrayAfterLastDigitOne(int[] array) {
        int indexLastDigit = getIndexByNumber(1, array);
        if (indexLastDigit == -1)
            throw new  RuntimeException("В массиве нет единиц");

        return Arrays.copyOfRange(array, indexLastDigit + 1, array.length);
    }

    public boolean checkArray(int[] array){
        boolean haveDigitOne = false;
        boolean haveDigitTwo = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                haveDigitOne = true;
            } else if (array[i] == 2) {
                haveDigitTwo = true;
            } else {
                return false;
            }
        }
        return haveDigitOne && haveDigitTwo;
    }

    private int getIndexByNumber(int number, int[] array) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == number)
                index = i;
        }
        return index;
    }
}