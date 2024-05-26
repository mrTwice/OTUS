package ru.otus.basic.yampolskiy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

        System.out.println(fillArrayList(1,9));
        System.out.println(getSumAllElementsWhichOverThenFive(Arrays.asList(5,1,6,7)));
    }

    public static ArrayList<Integer> fillArrayList(int min, int max) {
//        ArrayList<Integer> arrayList = new ArrayList<>();
//        for(int i = min; i <= max; i++) {
//            arrayList.add(i);
//        }
//        return arrayList;
        return IntStream.rangeClosed(min, max)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public static int getSumAllElementsWhichOverThenFive(List<Integer> ints) {
//        int sum = 0;
//        for(Integer i: ints){
//            if(i > 5) {
//                sum += i;
//            }
//        }
        return ints.stream()
                .filter(integer -> integer > 5)
                .mapToInt(Integer::intValue)
                .sum();
    }

//    Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на список, метод должен переписать каждую заполненную ячейку списка указанным числом;

    public static
}