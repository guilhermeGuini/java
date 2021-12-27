package br.com.guini.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class CountDownLatchTest {

    @Test
    void test() throws InterruptedException {
        int n = 100;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(n);

        for (int i = 0; i < n; ++i) // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();

        Thread.sleep(1000L);
        System.out.println("Allow execute");
        startSignal.countDown();
        Thread.sleep(1000L);
        System.out.println("Wait execute");
        doneSignal.await();
    }

    @Test
    void testWithExecutor() throws InterruptedException {
        int n = 100;
        CountDownLatch doneSignal = new CountDownLatch(n);

        Executor e = Executors.newCachedThreadPool();

        for (int i = 0; i < n; ++i) // create and start threads
            e.execute(new WorkerRunnable(doneSignal, i));

        doneSignal.await();           // wait for all to finish
    }

    class WorkerRunnable implements Runnable {
        private final CountDownLatch doneSignal;
        private final int i;
        WorkerRunnable(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }
        public void run() {
            doWork(i);
            doneSignal.countDown();
            System.out.println("Do work done");
        }

        void doWork(int i) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Worker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;
        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }
        public void run() {
            try {
                System.out.println("Waiting to start");
                startSignal.await();
                System.out.println("Starting do work");
                doWork();
                doneSignal.countDown();
                System.out.println("Do work done");
            } catch (InterruptedException ex) {} // return;
        }

        void doWork() {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
