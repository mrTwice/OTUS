package ru.otus.basic.yampolskiy;

import java.util.List;

public interface SearchTree{
    /**
     * @param element to find
     * @return element if exists, otherwise - null
     */
    boolean find(int element);

    List<Integer> getSortedList();
}
