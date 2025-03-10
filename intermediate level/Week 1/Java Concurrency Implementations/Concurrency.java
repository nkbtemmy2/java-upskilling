import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrencyDemo {

    // Exercise 4: Deadlock Example and Prevention
    public static class DeadlockDemo {
        private static final Object RESOURCE_A = new Object();
        private static final Object RESOURCE_B = new Object();

        public static void createDeadlock() {
            Thread thread1 = new Thread(() -> {
                synchronized (RESOURCE_A) {
                    System.out.println("Thread 1: Locked resource A");
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                    System.out.println("Thread 1: Waiting for resource B");
                    synchronized (RESOURCE_B) {
                        System.out.println("Thread 1: Locked resource B");
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                synchronized (RESOURCE_B) {
                    System.out.println("Thread 2: Locked resource B");
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                    System.out.println("Thread 2: Waiting for resource A");
                    synchronized (RESOURCE_A) {
                        System.out.println("Thread 2: Locked resource A");
                    }
                }
            });

            thread1.start();
            thread2.start();
        }

        public static void preventDeadlock() {
            Lock lockA = new ReentrantLock();
            Lock lockB = new ReentrantLock();

            Thread thread1 = new Thread(() -> {
                try {
                    if (lockA.tryLock(1, TimeUnit.SECONDS)) {
                        System.out.println("Thread 1: Locked resource A");
                        Thread.sleep(100);
                        if (lockB.tryLock(1, TimeUnit.SECONDS)) {
                            System.out.println("Thread 1: Locked resource B");
                            lockB.unlock();
                        }
                        lockA.unlock();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            Thread thread2 = new Thread(() -> {
                try {
                    if (lockB.tryLock(1, TimeUnit.SECONDS)) {
                        System.out.println("Thread 2: Locked resource B");
                        Thread.sleep(100);
                        if (lockA.tryLock(1, TimeUnit.SECONDS)) {
                            System.out.println("Thread 2: Locked resource A");
                            lockA.unlock();
                        }
                        lockB.unlock();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            thread1.start();
            thread2.start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting image processing demo...");
        ImageProcessor.demonstrateImageProcessing();

        System.out.println("\nStarting concurrent cache demo...");
        demonstrateConcurrentCache();

        System.out.println("\nStarting counter demo...");
        CounterDemo.demonstrateCounters();

        System.out.println("\nStarting deadlock demo (this may hang)...");
        DeadlockDemo.createDeadlock();

        Thread.sleep(3000); // Allow some time for deadlock to show
        System.out.println("\nStarting deadlock prevention demo...");
        DeadlockDemo.preventDeadlock();
    }
}

