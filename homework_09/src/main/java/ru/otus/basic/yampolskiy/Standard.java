package ru.otus.basic.yampolskiy;

import java.util.*;

public class Standard {

    public static ArrayList<Integer> fillArrayList(int min, int max) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            arrayList.add(i);
        }
        return arrayList;

    }

    public static int getSumAllElementsOverThenFive(List<Integer> ints) {
        checkListToEmpty(ints);
        int sum = 0;
        for (Integer i : ints) {
            if (i > 5) {
                sum += i;
            }
        }
        return sum;
    }

    public static void rewriteListWithNumber(int number, List<Integer> list) {
        checkListToEmpty(list);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, number);
        }
    }

    public static void increaseListElementsByValue(int number, List<Integer> list) {
        checkListToEmpty(list);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) + number);
        }
    }

    public static List<String> getNamesOfEmployees(List<Employee> list) {
        checkListToEmpty(list);
        List<String> names = new ArrayList<>();
        for (Employee employee : list) {
            names.add(employee.getName());
        }
        return names;
    }

    public static List<Employee> filterEmployeesByMinAge(List<Employee> employees, int minAge) {
        checkListToEmpty(employees);
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() >= minAge) {
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    public static boolean checkAverageAgeExceeds(List<Employee> employees, int minAverageAge) {
        checkListToEmpty(employees);
        int sumAllAges = 0;
        for (Employee employee : employees) {
            sumAllAges += employee.getAge();
        }
        return minAverageAge < (sumAllAges / employees.size());
    }


    public static Employee getYoungestEmployee(List<Employee> employees) {
        checkListToEmpty(employees);
        Employee youngestEmployee = employees.get(0);
        int minAge = youngestEmployee.getAge();
        for (Employee employee : employees) {
            if (employee.getAge() < minAge) {
                youngestEmployee = employee;
                minAge = youngestEmployee.getAge();
            }
        }
        return youngestEmployee;
    }

    public static <T> void checkListToEmpty(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("Список пуст");
        }
    }
}