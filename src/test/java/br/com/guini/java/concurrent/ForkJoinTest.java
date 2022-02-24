package br.com.guini.java.concurrent;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

class ForkJoinTest {

    @Test
    void test() throws InterruptedException {
        class ArrayFill extends RecursiveAction {
            private static final int MIN_SIZE_TO_FILL = 1000;

            private final int[] arr;
            private final int start;
            private final int end;

            ArrayFill(int[] arr, int start, int end) {
                this.arr = arr;
                this.start = start;
                this.end = end;
            }

            @Override
            protected void compute() {
                if ((end - start) <= MIN_SIZE_TO_FILL) {
                    IntStream.range(start, end)
                            .forEach(pos -> arr[pos] = ThreadLocalRandom.current().nextInt());
                } else {
                    int half = ((end - start) / 2) + start;
                    ArrayFill arrayFillLeft = new ArrayFill(arr, start, half);
                    arrayFillLeft.fork();

                    ArrayFill arrayFillRight = new ArrayFill(arr, half, end);
                    arrayFillRight.compute();

                    arrayFillLeft.join();
                }
            }
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int[] arr = new int[100000000];

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        /*
         * Invoke method description:
         *  Arrange async execution	| execute(ForkJoinTask) | ForkJoinTask.fork()
         *  Await and obtain result | invoke(ForkJoinTask)  | ForkJoinTask.invoke()
         *  Arrange exec and obtain Future | submit(ForkJoinTask) | ForkJoinTask.fork() (ForkJoinTasks are Futures)
         */
        forkJoinPool.invoke(new ArrayFill(arr, 0, arr.length));
        stopWatch.stop();
        System.out.println("Finish: " + stopWatch.getTotalTimeMillis());
    }

    @Test
    void testSerial() {
        StopWatch stopWatch = new StopWatch();
        int[] arr = new int[100000000];

        stopWatch.start();
        IntStream.range(0, arr.length)
                .forEach(pos -> arr[pos] = ThreadLocalRandom.current().nextInt());
        stopWatch.stop();
        System.out.println("Finish: " + stopWatch.getTotalTimeMillis());
    }
}
