package ru.otus.basic.yampolskiy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WithStreamApi {

    public static ArrayList<Integer> fillArrayList(int min, int max) {
        return IntStream
                .rangeClosed(min, max)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static int getSumAllElementsOverThenFive(List<Integer> ints) {
        return ints.stream()
                .filter(n -> n > 5)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static void rewriteListWithNumber(int number, List<Integer> list) {
        list.replaceAll(n -> number);
    }

    public static void increaseListElementsByValue(int number, List<Integer> list) {
        list.replaceAll(n -> n + number);
    }


    public static List<String> getNamesOfEmployees(List<Employee> list) {
        return list.stream()
                .map(Employee::getName)
                .toList();
    }

    public static List<Employee> filterEmployeesByMinAge(List<Employee> employees, int minAge) {
        return employees.stream()
                .filter(employee -> employee.getAge() >= minAge)
                .toList();
    }

    public static boolean checkAverageAgeExceeds(List<Employee> employees, int minAverageAge) {
        OptionalDouble averageAge = employees.stream()
                .mapToInt(Employee::getAge)
                .average();
        return averageAge.isPresent() && averageAge.getAsDouble() > minAverageAge;
    }

    public static Employee getYoungestEmployee(List<Employee> employees) {
        return employees.stream()
                .min(Comparator.comparingInt(Employee::getAge))
                .orElseThrow(() -> new NoSuchElementException("Список сотрудников пуст"));
    }
}