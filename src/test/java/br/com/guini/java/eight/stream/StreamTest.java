package br.com.guini.java.eight.stream;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StreamTest {

    @Test
    void testPeek() {

        List<String> peekValues = new ArrayList<>();

        long count = Stream.of("A", "B", "C", "D")
                .filter(value -> value.equals("A"))
                .peek(peekValues::add)
                .count();

        assertEquals(1, count);
        assertEquals(1, peekValues.size());
        assertEquals("A", peekValues.get(0));
    }

    @Test
    void testReduce() {
        Optional<String> reduceValue = Stream.of("A", "B", "C", "D")
                .filter(value -> value.equals("A") || value.equals("D"))
                .reduce((v1, v2) -> v1 + v2);

        assertTrue(reduceValue.isPresent());
        assertEquals("AD", reduceValue.get());
    }

    @Test
    void testMap() {
        Optional<Integer> reduceValue = Stream.of('A', 'B', 'C', 'D')
                .map(value -> (int) value)
                .peek(System.out::println)
                .reduce(Integer::sum);

        assertTrue(reduceValue.isPresent());
        assertEquals((65+66+67+68), reduceValue.get());
    }

    @Test
    void findFirst() {
        Optional<Character> character = Stream.of('A', 'B', 'C', 'D')
                .findFirst();

        assertTrue(character.isPresent());
        assertEquals('A', character.get());
    }

    @Test
    void findFirstException() {
        Optional<Character> character = Stream.of('A', 'B', 'C', 'D')
                .filter(value -> value == 'X')
                .findFirst();

        assertFalse(character.isPresent());
    }

    @Test
    void findAny() {
        Optional<Character> character = Stream.of('A', 'B', 'A', 'B')
                .filter(value -> value == 'A')
                .findAny();

        assertTrue(character.isPresent());
        assertEquals('A', character.get());
    }

    @Test
    void allMatchFail() {
        boolean character = Stream.of('A', 'B', 'C', 'D')
                .allMatch(value -> value == 'A');

        assertFalse(character);
    }

    @Test
    void allMatch() {
        boolean character = Stream.of('A', 'A', 'A', 'A')
                .allMatch(value -> value == 'A');

        assertTrue(character);
    }

    @Test
    void noneMatchFail() {
        boolean character = Stream.of('A', 'B', 'C', 'D')
                .noneMatch(value -> value == 'A');

        assertFalse(character);
    }

    @Test
    void noneMatch() {
        boolean character = Stream.of('A', 'C', 'D', 'A')
                .noneMatch(value -> value == 'B');

        assertTrue(character);
    }

    @Test
    void parallel() {
        boolean parallel = Stream.of('A', 'C', 'D', 'A')
                .isParallel();

        assertFalse(parallel);

        parallel = Stream.of('A', 'C', 'D', 'A')
                .parallel()
                .isParallel();

        assertTrue(parallel);
    }

    @Test
    void testLimit() {
        List<Integer> values = Stream.of(1, 2, 3, 4)
                .limit(2)
                .collect(Collectors.toList());

        assertEquals(2, values.size());
        assertEquals(1, values.get(0));
        assertEquals(2, values.get(1));
    }

    @Test
    void testSkip() {
        List<Integer> values = Stream.of(1, 2, 3, 4)
                .skip(2)
                .collect(Collectors.toList());

        assertEquals(2, values.size());
        assertEquals(3, values.get(0));
        assertEquals(4, values.get(1));
    }

    @Test
    void dropWhile() {
        List<Integer> values = Stream.of(1, 2, 3, 4)
                .dropWhile(value -> value < 3)
                .collect(Collectors.toList());

        assertEquals(2, values.size());
        assertEquals(3, values.get(0));
        assertEquals(4, values.get(1));
    }

    @Test
    void takeWhile() {
        List<Integer> values = Stream.of(1, 2, 3, 4)
                .takeWhile(value -> value < 3)
                .collect(Collectors.toList());

        assertEquals(2, values.size());
        assertEquals(1, values.get(0));
        assertEquals(2, values.get(1));
    }

    @Test
    void modifySource() {
        List<Integer> values = new ArrayList<>(List.of(1, 2, 3, 4));

        assertThrows(NullPointerException.class, () -> values.stream()
              .forEach(val -> {
                  if (val == 1) {
                      values.remove(val);
                  }
              }));
    }

    @Test
    void testFlatMap() {

        class Author {
            private final List<String> books;
            Author(List<String> books) {
                this.books = books;
            }
            public List<String> getBooks() {return books;}

            @Override
            public String toString() {
                return "Author{" +
                        "books=" + books +
                        '}';
            }
        }

        List<Author> authors = List.of(
                new Author(List.of("Python", "JavaScript", "C#")),
                new Author(List.of("Go", "Java")),
                new Author(List.of("HTML", "Ruby")),
                new Author(List.of("Python", "Angular", "Clojure"))
        );

        List<String> books = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .filter(book -> book.contains("Python"))
                .peek(System.out::println)
                .collect(Collectors.toList());

        assertEquals(2, books.size());
        assertEquals("Python", books.get(0));
        assertEquals("Python", books.get(1));
    }
}
