package br.com.guini.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Library {
    private final Semaphore semaphore = new Semaphore(3, true);
    private final List<String> books = List.of("Java", "Python", "C#");
    private final Boolean[] isUsed = new Boolean[]{false, false, false};

    public String getBook() throws InterruptedException {
        semaphore.tryAcquire(1, TimeUnit.SECONDS);
        return doGetBook();
    }

    public int getAvailable() {
        return semaphore.availablePermits();
    }

    private synchronized String doGetBook() {
        return IntStream.range(0, isUsed.length)
                .filter(pos -> !isUsed[pos])
                .mapToObj(pos -> {
                    isUsed[pos] = true;
                    return books.get(pos);
                })
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    public void putBook(String book) {
        semaphore.release();
        doPutBook(book);
    }

    private synchronized void doPutBook(String book) {
        IntStream.range(0, books.size())
                .filter(pos -> books.get(pos).equals(book))
                .forEach(pos -> isUsed[pos] = false);
    }
}

class SynchronizerTest {

    @Test
    void testTimeoutSemaphore() throws InterruptedException {
        Library library = new Library();
        library.getBook();
        library.getBook();
        library.getBook();

        assertThrows(IllegalArgumentException.class, () -> library.getBook());
    }

    @Test
    void testSuccessSemaphore() throws InterruptedException {
        Library library = new Library();
        String book1 = library.getBook();
        String book2 = library.getBook();
        library.putBook(book1);

        assertEquals(2, library.getAvailable());

        String book3 = library.getBook();
        String book4 = library.getBook();

        assertEquals(0, library.getAvailable());

        library.putBook(book2);
        library.putBook(book3);
        library.putBook(book4);

        assertEquals(3, library.getAvailable());
    }
}
