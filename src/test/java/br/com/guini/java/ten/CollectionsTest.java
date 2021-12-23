package br.com.guini.java.ten;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

class CollectionsTest {

    @Test
    void listOfFactory() {
        var example1 = List.of(1, 2);
        var example2 = List.of(new int[] { 1, 2});
    }

    @Test
    void setOfFactory() {
        var example1 = Set.of(1);
    }

    @Test
    void mapOfFactory() {
        Map<String, Integer> key = Map.of("key", 1);
    }
}
