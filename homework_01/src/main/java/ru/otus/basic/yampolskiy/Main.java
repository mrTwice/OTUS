package ru.otus.basic.yampolskiy;

import java.util.Random;
import java.util.Scanner;

/*
Домашнее задание
Практика по основам языка Java #1
Цель:
Научиться решать простые задачи с использованием изученных конструкций языка Java.

Описание/Пошаговая инструкция выполнения домашнего задания:
(1) Реализуйте метод greetings(), который при вызове должен отпечатать в столбец 4 слова: Hello, World, from, Java;
(2) Реализуйте метод checkSign(..), принимающий в качестве аргументов 3 int переменные a, b и c. Метод должен посчитать их сумму, и если она больше или равна 0, то вывести в консоль сообщение “Сумма положительная”, в противном случае - “Сумма отрицательная”;
(3) Реализуйте метод selectColor() в теле которого задайте int переменную data с любым начальным значением. Если data меньше 10 включительно, то в консоль должно быть выведено сообщение “Красный”, если от 10 до 20 включительно, то “Желтый”, если больше 20 - “Зеленый”;
(4) Реализуйте метод compareNumbers(), в теле которого объявите две int переменные a и b с любыми начальными значениями. Если a больше или равно b, то необходимо вывести в консоль сообщение “a >= b”, в противном случае “a < b”;
(5) Создайте метод addOrSubtractAndPrint(int initValue, int delta, boolean increment). Если increment = true, то метод должен к initValue прибавить delta и отпечатать в консоль результат, в противном случае - вычесть;
Каждый метод последовательно вызовите из метода main();
(*) При запуске приложения, запросите у пользователя число от 1 до 5, и после ввода выполнения метод, соответствующий указанному номеру со случайным значением аргументов;
Домашнее задание сдается через Pull Request!
 */
public class Main {

    static Scanner consoleInput = new Scanner(System.in);

    public static void main(String[] args) {
        start();
    }

    /**
     * Основной метод программы, реализующий выбор метода для запуска и его вызов.
     */
    static void start(){
        boolean stopProgram = false;
        Random rnd = new Random();
        while (!stopProgram){
            printMenu();
            switch (consoleInput.nextInt()) {
                case 1 -> greetings();
                case 2 -> checkSign(rnd.nextInt(-10, 11), rnd.nextInt(-10, 11), rnd.nextInt(-10, 11));
                case 3 -> selectColor();
                case 4 -> compareNumbers();
                case 5 -> addOrSubtractAndPrint(rnd.nextInt(-10, 11), rnd.nextInt(-10, 11), rnd.nextBoolean());
                case 6 -> stopProgram = true;

            }
        }
    }

    /**
     * Метод выводит в консоль список пунктов меню
     */
    static void printMenu(){
        System.out.println("\n");
        System.out.print(
                """
                        1. Вызвать метод greetings()
                        2. Вызвать метод checkSign()
                        3. Вызвать метод selectColor();
                        4. Вызвать метод compareNumbers()
                        5. Вызвать метод addOrSubtractAndPrint()
                        6. Выйти

                        Введите номер пункта меню:\s"""
        );

    }

    static void printMessage(String message){
        System.out.println(message);
    }

    /**
     * Метод выводит слова "Hello" "World" "from" "Java" в столбик
     */
    static void greetings() {
        System.out.printf("%s\n%s\n%s\n%s\n", "Hello", "World", "from", "Java");
    }

    /**
     * Метод принимает три числа, складывает их, выводя информационное сообщение о том, положительная или отрицательная их сумма
     * @param a
     * @param b
     * @param c
     */
    static void checkSign(int a, int b, int c) {
        if (a + b + c >= 0) {
            printMessage("Сумма положительная");
        } else {
            printMessage("Сумма отрицательная");
        }
    }

    /**
     * В методе генерируется случайное число, и в зависимости от условия выводится название цвета
     */
    static void selectColor() {
        int data = new Random().nextInt(0, 30);
        if(data <= 10){
            printMessage("Красный");
        } else if (10 < data && data <= 20) {
            printMessage("Желтый");
        } else {
            printMessage("Зеленый");
        }
    }

    /**
     * В методе генерируется два случайных числа, после чего он выводит результат сравнения этих числе
     */
    static void compareNumbers() {
        int a = new Random().nextInt(-100, 101);
        int b = new Random().nextInt(-100, 101);
        printMessage("а = " + a);
        printMessage("b = " + b);
        if(a >= b){
            printMessage("a >= b");
        } else {
            printMessage("a < b");
        }
    }

    /**
     * Метод в случае increment=true складывает два аргумента, в противном случае вычитает из первого второй
     * @param initValue
     * @param delta
     * @param increment
     */

    static void addOrSubtractAndPrint(int initValue, int delta, boolean increment) {
        printMessage("initValue = " + initValue);
        printMessage("delta = " + delta);
        printMessage("increment = " + increment);
        if(increment) {
            printMessage(String.valueOf(initValue + delta));
        } else {
            printMessage(String.valueOf(initValue - delta));
        }
    }


}