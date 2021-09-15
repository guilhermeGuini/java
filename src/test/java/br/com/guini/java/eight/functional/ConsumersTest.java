package br.com.guini.java.eight.functional;

import org.junit.jupiter.api.Test;

import java.util.function.IntConsumer;

class ConsumersTest {

    @Test
    void consumers_test() {
        IntConsumer intConsumer = val -> {
            if (val > 10) {
                System.out.println("OK");
            } else {
                System.out.println("ERROR");
            }
        };

        intConsumer.accept(3);
        intConsumer.accept(13);
    }
}
