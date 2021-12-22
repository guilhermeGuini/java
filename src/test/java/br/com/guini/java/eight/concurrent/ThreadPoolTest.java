package br.com.guini.java.eight.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreadPoolTest {

    @Test
    void cachedThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> System.out.println("Test"));
    }

    @Test
    void fixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> System.out.println("Test"));
    }

    @Test
    void fixedThreadPoolUsingUnboundedQueue() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        IntStream.range(0, 200)
                .forEach(i -> executorService.execute(() -> System.out.println("Test" + i)));
    }

    @Test
    void scheduledThreadPool() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        ScheduledFuture<?> future = scheduledExecutorService.schedule(() -> System.out.println("Test"), 3, TimeUnit.SECONDS);
        boolean done = future.isDone();
        System.out.println(done);

        while (!future.isDone()) {
            Thread.sleep(1000L);
        }
    }

    @Test
    void scheduledThreadPoolGetFuture() throws InterruptedException, ExecutionException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        ScheduledFuture<?> future = scheduledExecutorService.schedule(() -> System.out.println("Test"), 3, TimeUnit.SECONDS);
        Object o = future.get();
        boolean done = future.isDone();
        System.out.println(done);
    }

    @Test
    void testCallable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> callableResponse = executorService.submit(() -> 15 + 10);
        Integer  value = callableResponse.get();
        assertEquals(25, value);
    }
}
