package br.com.guini.java.eight.functional;

import org.junit.jupiter.api.Test;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuppliersTest {

    @Test
    void supplier_test() {
        Supplier<Integer> supplier = () -> 55;
        assertEquals(55, supplier.get());
    }

    @Test
    void double_supplier() {
        IntSupplier supplier = () -> 55;
        assertEquals(55, supplier.getAsInt());
    }
}
