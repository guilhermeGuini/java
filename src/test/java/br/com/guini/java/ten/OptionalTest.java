package br.com.guini.java.ten;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OptionalTest {

    @Test
    void orElseThrow() {
        assertThrows(IllegalArgumentException.class, () -> Optional.empty().orElseThrow(IllegalArgumentException::new));
    }
}
