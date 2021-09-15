package br.com.guini.java.eight.functional;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PredicatesTest {

    @Test
    void predicates_test() {
        Predicate<Integer> greaterThanAHundred = val -> val > 100;
        assertTrue(greaterThanAHundred.test(101));
        assertFalse(greaterThanAHundred.negate().test(101));
    }

    @Test
    void predicates_test_and() {
        Predicate<Integer> greaterThanAHundred = val -> val > 100;
        Predicate<Integer> lessThanAThousand = val -> val < 1000;

        assertTrue(greaterThanAHundred.and(lessThanAThousand).test(101));
    }

    @Test
    void predicates_test_or() {
        Predicate<Integer> greaterThanAHundred = val -> val > 100;
        Predicate<Integer> lessThanZero = val -> val < 0;

        assertTrue(greaterThanAHundred.or(lessThanZero).test(101));
        assertTrue(greaterThanAHundred.or(lessThanZero).test(-1));
    }
}
