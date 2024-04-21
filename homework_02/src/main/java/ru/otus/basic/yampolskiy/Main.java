package ru.otus.basic.yampolskiy;

import java.util.Arrays;
import java.util.Random;

/*
● Реализуйте метод, принимающий в качестве аргументов целое число и строку, и печатающий в консоли строку указанное количество раз;
● Реализуйте метод, принимающий в качестве аргумента целочисленный массив, суммирующий все элементы, значение которых больше 5, и печатающий полученную сумму в консоли;
● Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на целочисленный массив, метод должен заполнить каждую ячейку массива указанным числом;
● Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на целочисленный массив, увеличивающий каждый элемент массива на указанное число;
● Реализуйте метод, принимающий в качестве аргумента целочисленный массив, и печатающий в консоли информацию о том, сумма элементов какой из половин массива больше;
 */
public class Main {
    public static void main(String[] args) {
        printStringFewTimes(getRandomInt(2, 5), "Java");
        printSumArrayElementsMoreThanFive(getRandomArray());
        fillArrayWithNumber(getRandomInt(0, 10), new int[9]);
        enlargeEachElementByNumber(getRandomInt(0, 10), new int[] {5, 3, 8, 5, 1, 1, 5, 7, 8});
        whichHalfGreater(getRandomArray());
    }

    /**
     * Метод считает сумму чисел для левой и правой половин массива, и выводит в консоль, сумма чисел какой из половин больше.
     * @param array ссылка на массив
     */
    private static void whichHalfGreater(int[] array) {
        int leftHalf = 0;
        int rightHalf = 0;
        for (int i = 0; i < array.length; i++) {
            if(i < array.length/2){
                leftHalf += array[i];
            } else {
                rightHalf += array[i];
            }
        }
        System.out.println(Arrays.toString(array));
        if(leftHalf > rightHalf){
            System.out.println("Левая половина больше");
        } else {
            System.out.println("Правая половина больше");
        }
    }

    /**
     * Метод увеличивает каждый элемент массива на заданное число
     * @param number число на которое увеличивается каждый элемент массива
     * @param array ссылка на массив
     */
    private static void enlargeEachElementByNumber(int number, int[] array) {
        System.out.printf("Заданное число: %d", number);
        System.out.print("Исходный массив: ");
        System.out.println(Arrays.toString(array));
        for (int i = 0; i < array.length; i++) {
            array[i] += number;
        }
        System.out.print("Результат: ");
        System.out.println(Arrays.toString(array));
    }

    /**
     * Метод заполняет массив указанным числом
     * @param number число для заполнения всех ячеек массива
     * @param array массив для заполнения
     */
    private static void fillArrayWithNumber(int number, int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = number;
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * Метод считает сумму всех элементов массива, которые больше 5, и выводит полученный результат в консоль
     * @param array ссылка на массив
     */
    private static void printSumArrayElementsMoreThanFive(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] > 5) {
                sum += array[i];
            }
        }
        System.out.print("Исходный массив: ");
        System.out.println(Arrays.toString(array));
        System.out.printf("Сумма всех элементов, которые больше 5, равна: %d\n", sum);
    }

    /**
     * Метод печатает заданную строку указанное количество ращ
     * @param times количество повторений печати строки
     * @param str строка для печати
     */
    public static void printStringFewTimes(int times, String str) {
        for (int i = 0 ; i < times; i++) {
            System.out.println(str);
        }
    }

    /**
     * Метод создает массив, длинна которого проинициализирована случайным числом в диапазоне [5,10]
     * Данный массив заполняется случайными числами из диапазона [-10, 10]
     * @return int[]
     */
    public static int[] getRandomArray() {
        int[] array = new int[getRandomInt(5, 11)];
        for (int i = 0; i < array.length; i++) {
            array[i] = getRandomInt(-10, 11);
        }
        return array;
    }

    /**
     * Метод возвращает случайное целое число
     * @param origin начала диапазона генерации
     * @param bound конец диапазоны генерации (число не входит в диапазон)
     * @return полученное случайное число
     */
    public static int getRandomInt(int origin, int bound){
        Random rnd = new Random();
        return rnd.nextInt(origin, bound);
    }
}