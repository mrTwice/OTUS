package ru.otus.basic.yampolskiy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayOperationTest {

    private final ArrayOperation arrayOperation = new ArrayOperation();

    static Stream<Arguments> provideArraysForArrayAfterLastDigitOne() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 1, 2, 2}, new int[]{2, 2}),
                Arguments.of(new int[]{1, 2, 2, 1, 3, 4, 2}, new int[]{3, 4, 2}),
                Arguments.of(new int[]{2, 2, 2, 1, 5, 6}, new int[]{5, 6}),
                Arguments.of(new int[]{2, 2, 1}, new int[]{})
        );
    }

    @ParameterizedTest
    @MethodSource("provideArraysForArrayAfterLastDigitOne")
    void testArrayAfterLastDigitOne(int[] input, int[] expected) {
        int[] result = arrayOperation.arrayAfterLastDigitOne(input);
        assertArrayEquals(expected, result);
    }

    static Stream<Arguments> provideArraysForArrayAfterLastDigitOneException() {
        return Stream.of(
                Arguments.of(new int[]{2, 2, 2, 2}),
                Arguments.of(new int[]{3, 4, 5, 6}),
                Arguments.of(new int[]{2, 3, 4, 5})
        );
    }

    @ParameterizedTest
    @MethodSource("provideArraysForArrayAfterLastDigitOneException")
    void testArrayAfterLastDigitOneException(int[] input) {
        assertThrows(RuntimeException.class, () -> arrayOperation.arrayAfterLastDigitOne(input));
    }

    static Stream<Arguments> provideArraysForCheckArray() {
        return Stream.of(
                Arguments.of(new int[]{1, 2}, true),
                Arguments.of(new int[]{1, 1}, false),
                Arguments.of(new int[]{1, 3}, false),
                Arguments.of(new int[]{1, 2, 2, 1}, true),
                Arguments.of(new int[]{2, 1}, true),
                Arguments.of(new int[]{2, 2}, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArraysForCheckArray")
    void testCheckArray(int[] input, boolean expected) {
        boolean result = arrayOperation.checkArray(input);
        assertEquals(expected, result);
    }
}