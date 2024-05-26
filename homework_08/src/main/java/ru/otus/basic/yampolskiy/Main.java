package ru.otus.basic.yampolskiy;

import ru.otus.basic.yampolskiy.exeptions.AppArrayDataException;
import ru.otus.basic.yampolskiy.exeptions.AppArraySizeException;


public class Main {

    static final String[][] FIRST_ARRAY = {
            {"1", "1", "1", "1"},
            {"1", "1", "1", "1"},
            {"1", "1", "1", "1"},
            {"1", "-1", "1"}
    };

    static final String[][] SECOND_ARRAY = {
            {"1", "1", "1", "1"},
            {"1", "1", "1", "1"},
            {"1", "1", "1", "1"},
            {"ф", "1", "1", "1"},
    };

    public static void main(String[] args) {
        try {
            System.out.println(convertStringToIntAndSumAllElements(FIRST_ARRAY));
        } catch (AppArraySizeException | AppArrayDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int convertStringToIntAndSumAllElements(String[][] array) throws AppArraySizeException, AppArrayDataException {
        if (array.length != 4 || array[0].length != 4 || array[1].length != 4 || array[2].length != 4  || array[3].length != 4) {
            if (array.length != 4) {
                throw new AppArraySizeException( array.length, "В массиве должно быть четыре строки. Он содержит: ");
            }

            for (int i = 0; i < array.length; i++) {
                if (array[i].length != 4) {
                    throw new AppArraySizeException(i, "В строке должно быть 4 элементы. Индекс текущей строки: ");
                }
            }
        }
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    int currentElement = Integer.parseInt(array[i][j]);
                    sum += currentElement;
                } catch (NumberFormatException e) {
                    throw new AppArrayDataException(i, j);
                }
            }
        }
        return sum;
    }
}