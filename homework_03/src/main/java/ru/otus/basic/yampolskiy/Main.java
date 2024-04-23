package ru.otus.basic.yampolskiy;

/*
● Реализовать метод sumOfPositiveElements(..), принимающий в качестве аргумента целочисленный двумерный
массив, метод должен посчитать и вернуть сумму всех элементов массива, которые больше 0;
● Реализовать метод, который принимает в качестве аргумента int size и печатает в консоль квадрат из
символов * со сторонами соответствующей длины;
● Реализовать метод, принимающий в качестве аргумента квадратный двумерный целочисленный массив, и
зануляющий его диагональные элементы (можете выбрать любую из диагоналей, или занулить обе).
Проверять количество строк и столбцов не требуется, условие “квадратности” нужно чтобы упростить вам
работу с диагоналями;
● Реализовать метод findMax(int[][] array) который должен найти и вернуть максимальный элемент массива;
● Реализуйте метод, который считает сумму элементов второй строки или столбца двумерного массива (по
вашему выбору), если второй строки/столбца не существует, то в качестве результата необходимо вернуть -1
 */
public class Main {
    public static void main(String[] args) {

//        int sum = sumOfPositiveElements(new int[][]{{1,1},{-1,1}});
//        System.out.println(sum);

//        printArray(5);

//        nullDiagonals(new int[][]{{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}});

//        int max = findMax(new int[][]{{-1,-1,-1,-1,-9},{-1,-1,-1,-1,-1},{-1,-1,-8,-1,-1},{-1,-1,-1,-1,0},{-1,-1,-12,-1,-1}});
//        System.out.println(max);

        int sum = sumElementsColumnWithNumberTwo(new int[][]{
                {1, 0, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1}});
        System.out.println(sum);
    }

    /**
     * Метод вычисляет сумму всех элементом массива, которые больше нуля и возвращает ее
     *
     * @param array сслыка на массив
     * @return значение вычисленной суммы
     */
    private static int sumOfPositiveElements(int[][] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] > 0) {
                    sum += array[i][j];
                }
            }
        }
        return sum;
    }

    /**
     * Метод выводит на печать в консоль квадрат из '*'
     *
     * @param size размер массива
     */
    private static void printArray(int size) {
        System.out.print("  ");
        for (int k = 0; k < size; k++) {
            System.out.print(k + "  ");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (j == 0) {
                    System.out.print(i + " ");
                }
                System.out.printf("*  ");
            }
            System.out.println();
        }
    }

    /**
     * Метод получает массив и зануляет его диагонали
     *
     * @param array ссылка на массив
     */
    private static void nullDiagonals(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (i == j || j == array.length - 1 - i) {
                    array[i][j] = 0;
                }
                System.out.print(array[i][j] + "  ");
            }
            System.out.println();
        }
    }

    /**
     * Метод ищет максимальный элемент массива
     *
     * @param array ссылка на массив
     * @return наибольший элемент
     */
    private static int findMax(int[][] array) {
        int max = array[0][0];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] > max) {
                    max = array[i][j];
                }
            }
        }
        return max;
    }

    /**
     * Метод вычисляет сумму элементов второго столбца двумерного массива
     *
     * @param array ссылка на массив
     * @return суммв элементов второго столбца, иначе -1
     */
    private static int sumElementsColumnWithNumberTwo(int[][] array) {
        int sumSecondColumns = 0;
        if (array[0].length < 2) {
            return -1;
        } else {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    if (j == 1) {
                        sumSecondColumns += array[i][j];
                    }
                }
            }
        }
        return sumSecondColumns;
    }
}