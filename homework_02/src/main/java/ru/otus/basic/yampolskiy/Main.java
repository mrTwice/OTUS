package ru.otus.basic.yampolskiy;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/*
● Реализуйте метод, принимающий в качестве аргументов целое число и строку, и печатающий в консоли строку указанное количество раз;
● Реализуйте метод, принимающий в качестве аргумента целочисленный массив, суммирующий все элементы, значение которых больше 5, и печатающий полученную сумму в консоли;
● Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на целочисленный массив, метод должен заполнить каждую ячейку массива указанным числом;
● Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на целочисленный массив, увеличивающий каждый элемент массива на указанное число;
● Реализуйте метод, принимающий в качестве аргумента целочисленный массив, и печатающий в консоли информацию о том, сумма элементов какой из половин массива больше;

Дополнительно:

● Реализуйте метод, принимающий на вход набор целочисленных массивов, и получающий новый массив равный сумме входящих
Пример: { 1, 2, 3 } + { 2, 2 } + { 1, 1, 1, 1, 1} = { 4, 5, 4, 1, 1 }
● Реализуйте метод, проверяющий, что есть “точка” в массиве, в которой сумма левой и правой части равны. “Точка находится между элементами”;
Пример: { 1, 1, 1, 1, 1, | 5 }, { 5, | 3, 4, -2 }, { 7, 2, 2, 2 }, { 9, 4 }
● Реализуйте метод, проверяющий что все элементы массива идут в порядке убывания или
возрастания (по выбору пользователя)
● Реализуйте метод, “переворачивающий” входной массив Пример: { 1 2 3 4 } => { 4 3 2 1 }
 */


public class Main {
    private static Scanner consoleInput = new Scanner(System.in);

    public static void main(String[] args) {
        start();
    }

    /**
     * Метод реализующий запуск соответствующего метода
     */
    private static void start() {
        boolean flag = true;
        while (flag){
            printMenu();
            switch (consoleInput.nextInt()){
               case 1 -> printStringFewTimes(getRandomInt(2, 5), "Java");
               case 2 -> printSumArrayElementsMoreThanFive(getRandomArray());
               case 3 -> fillArrayWithNumber(getRandomInt(0, 10), new int[9]);
               case 4 -> enlargeEachElementByNumber(getRandomInt(0, 10), new int[] {5, 3, 8, 5, 1, 1, 5, 7, 8});
               case 5 -> whichHalfGreater(getRandomArray());
               case 6 -> sumArraysElementsByIndex(new int[]{1,3,4,5,6}, new int[]{ 2,5}, new int[]{1} );
               case 7 -> pointOfArray(new int[] {1, 1, 1, 1, 1, 5});
               case 8 -> checkDecreaseOrIncreaseElements(new int[] {5, 1, 1, 1, 1, 1}, false);
               case 9 -> reversArray(new int[]{1, 2, 3, 7, 1, 2, 3});
               case 10 -> flag=false;
            }
        }
    }

    /**
     * Метод печатает консольное меню
     */
    private static void printMenu() {
        System.out.print("""
                        1. printStringFewTimes
                        2. printSumArrayElementsMoreThanFive
                        3. fillArrayWithNumber
                        4. enlargeEachElementByNumber
                        5. whichHalfGreater
                        
                        Дополнительные задачи:
                        
                        6. sumArraysElementsByIndex
                        7. pointOfArray
                        8. checkDecreaseOrIncreaseElements
                        9. reversArray
                        10. Выход
                        Введите номер пункта меню:\s""");
    }

    /**
     * Метод разворачивающий массив
     * @param array ссылка на массив
     */
    private static void reversArray(int[] array) {
        System.out.println(Arrays.toString(array));

        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }

        System.out.println(Arrays.toString(array));
    }

    /**
     * Метод проверяет, является ли массив упорядоченным по убыванию или по возрастанию
     *
     * @param array    ссылка на массив
     * @param decrease устанавливае вариант проверки, если true, проверка осуществляется на соответствие условию является ли массив упорядоченным по убыванию, если false - по возрастанию.
     */
    private static void checkDecreaseOrIncreaseElements(int[] array, boolean decrease) {
        boolean ordered = true;
        for (int i = 0; i < array.length - 1; i++) {
            if (decrease ? array[i] < array[i + 1] : array[i] > array[i + 1]) {
                ordered = false;
                break;
            }
        }

        if (ordered) {
            System.out.println(decrease ? "По убыванию" : "По возрастанию");
        } else {
            System.out.println(decrease ? "Не по убыванию" : "Не по возрастанию");
        }
    }

    /**
     * Метод вычисляет индекс ячейки массива, сумма элементов хранимых в массиве, включая элемент с указанным индексом, равна сумме элементов стоящих после данного индекса
     *
     * @param array ссылка на массив
     */
    private static void pointOfArray(int[] array) {
        int leftPart = 0;
        int rightPart = 0;
        for (int i = 0; i < array.length; i++) {
            leftPart += array[i];
            for (int j = i + 1; j < array.length; j++) {
                rightPart += array[j];
            }
            if (leftPart == rightPart) {
                System.out.printf("Сумма элементов массива до элемента с индексом %d включительно равна сумме оставшихся элементов в правой части", i);
            } else {
                rightPart = 0;
            }
        }
        System.out.println("Волшебной точки в массиве нет");
    }

    /**
     * Метод принимает на вход три массива, создает новый массив, в котором каждый элемент является суммой элементов
     * входящих массивов с соответствующим индексом
     *
     * @param first  первый массив
     * @param second второй массив
     * @param third  третий массив
     */
    private static void sumArraysElementsByIndex(int[] first, int[] second, int[] third) {
        int maxLength = Math.max(first.length, Math.max(second.length, third.length));
        int[] result = new int[maxLength];

        for (int i = 0; i < maxLength; i++) {
            if (i < first.length)
                result[i] += first[i];
            if (i < second.length)
                result[i] += second[i];
            if (i < third.length)
                result[i] += third[i];
        }

        System.out.print("Результирующий массив: ");
        System.out.println(Arrays.toString(result));
    }

    /**
     * Метод считает сумму чисел для левой и правой половин массива, и выводит в консоль, сумма чисел какой из половин больше.
     *
     * @param array ссылка на массив
     */
    private static void whichHalfGreater(int[] array) {
        int leftHalf = 0;
        int rightHalf = 0;
        for (int i = 0; i < array.length; i++) {
            if (i < array.length / 2) {
                leftHalf += array[i];
            } else {
                rightHalf += array[i];
            }
        }
        System.out.println(Arrays.toString(array));
        if (leftHalf > rightHalf) {
            System.out.println("Левая половина больше");
        } else {
            System.out.println("Правая половина больше");
        }
    }

    /**
     * Метод увеличивает каждый элемент массива на заданное число
     *
     * @param number число на которое увеличивается каждый элемент массива
     * @param array  ссылка на массив
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
     *
     * @param number число для заполнения всех ячеек массива
     * @param array  массив для заполнения
     */
    private static void fillArrayWithNumber(int number, int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = number;
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * Метод считает сумму всех элементов массива, которые больше 5, и выводит полученный результат в консоль
     *
     * @param array ссылка на массив
     */
    private static void printSumArrayElementsMoreThanFive(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > 5) {
                sum += array[i];
            }
        }
        System.out.print("Исходный массив: ");
        System.out.println(Arrays.toString(array));
        System.out.printf("Сумма всех элементов, которые больше 5, равна: %d\n", sum);
    }

    /**
     * Метод печатает заданную строку указанное количество ращ
     *
     * @param times количество повторений печати строки
     * @param str   строка для печати
     */
    private static void printStringFewTimes(int times, String str) {
        for (int i = 0; i < times; i++) {
            System.out.println(str);
        }
    }

    /**
     * Метод создает массив, длинна которого проинициализирована случайным числом в диапазоне [5,10]
     * Данный массив заполняется случайными числами из диапазона [-10, 10]
     *
     * @return int[]
     */
    private static int[] getRandomArray() {
        int[] array = new int[getRandomInt(5, 11)];
        for (int i = 0; i < array.length; i++) {
            array[i] = getRandomInt(-10, 11);
        }
        return array;
    }

    /**
     * Метод возвращает случайное целое число
     *
     * @param origin начала диапазона генерации
     * @param bound  конец диапазоны генерации (число не входит в диапазон)
     * @return полученное случайное число
     */
    private static int getRandomInt(int origin, int bound) {
        Random rnd = new Random();
        return rnd.nextInt(origin, bound);
    }
}