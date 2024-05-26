package ru.otus.basic.yampolskiy;

import java.util.*;

import static ru.otus.basic.yampolskiy.Standard.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Реализуйте метод, принимающий в качестве аргументов числа min и max, и возвращающий ArrayList с набором последовательных значений в указанном диапазоне (min и max включительно, шаг - 1)");
        System.out.println(fillArrayList(2, 8));
        System.out.println(ru.otus.basic.yampolskiy.WithStreamApi.fillArrayList(2, 8));
        System.out.println();

        System.out.println("Реализуйте метод, принимающий в качестве аргумента список целых чисел, суммирующий все элементы, значение которых больше 5, и возвращающий сумму");
        System.out.println(getSumAllElementsOverThenFive(Arrays.asList(1, 5, 6, 1, 7)));
        System.out.println(ru.otus.basic.yampolskiy.WithStreamApi.getSumAllElementsOverThenFive(Arrays.asList(1, 5, 6, 1, 7)));
        System.out.println();

        System.out.println("Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на список, метод должен переписать каждую заполненную ячейку списка указанным числом");
        List<Integer> standardInts = Arrays.asList(1, 2, 3);
        List<Integer> streamApiInts = Arrays.asList(1, 2, 3);
        rewriteListWithNumber(5, standardInts);
        ru.otus.basic.yampolskiy.WithStreamApi.rewriteListWithNumber(5, streamApiInts);
        System.out.println(standardInts);
        System.out.println(streamApiInts);
        System.out.println();

        System.out.println("Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на список, увеличивающий каждый элемент списка на указанное число");
        List<Integer> standardInts2 = Arrays.asList(1, 2, 3);
        List<Integer> streamApiInts2 = Arrays.asList(1, 2, 3);
        increaseListElementsByValue(1, standardInts2);
        ru.otus.basic.yampolskiy.WithStreamApi.increaseListElementsByValue(1, streamApiInts2);
        System.out.println(standardInts2);
        System.out.println(streamApiInts2);
        System.out.println();

        List<Employee> employees = Arrays.asList(
                new Employee("Борис", 20),
                new Employee("Алексей", 25),
                new Employee("Аркадий", 19),
                new Employee("Олег", 33),
                new Employee("Виктор", 38),
                new Employee("Виктор", 18)
        );
        System.out.println("Реализуйте метод, принимающий в качестве аргумента список сотрудников, и возвращающий список их имен");
        System.out.println(getNamesOfEmployees(employees));
        System.out.println(ru.otus.basic.yampolskiy.WithStreamApi.getNamesOfEmployees(employees));
        System.out.println();

        System.out.println("Реализуйте метод, принимающий в качестве аргумента список сотрудников и минимальный возраст, и возвращающий список сотрудников, возраст которых больше либо равен указанному аргументу");
        System.out.println(filterEmployeesByMinAge(employees, 25));
        System.out.println(ru.otus.basic.yampolskiy.WithStreamApi.filterEmployeesByMinAge(employees, 25));
        System.out.println();

        System.out.println("Реализуйте метод, принимающий в качестве аргумента список сотрудников и минимальный средний возраст, и проверяющий что средний возраст сотрудников превышает указанный аргумент");
        System.out.println(checkAverageAgeExceeds(employees, 27));
        System.out.println(ru.otus.basic.yampolskiy.WithStreamApi.checkAverageAgeExceeds(employees, 27));
        System.out.println();

        System.out.println("Реализуйте метод, принимающий в качестве аргумента список сотрудников, и возвращающий ссылку на самого молодого сотрудника");
        System.out.println(getYoungestEmployee(employees));
        System.out.println(ru.otus.basic.yampolskiy.WithStreamApi.getYoungestEmployee(employees));
    }
}