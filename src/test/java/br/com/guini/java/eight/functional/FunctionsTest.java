package br.com.guini.java.eight.functional;

import org.junit.jupiter.api.Test;

import java.util.function.IntFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionsTest {

    @Test
    void functional_test() {
        IntFunction<String> intFunction = value -> "The value is: " + value;
        String returnValue = intFunction.apply(101);
        assertEquals("The value is: 101", returnValue);
    }
}
