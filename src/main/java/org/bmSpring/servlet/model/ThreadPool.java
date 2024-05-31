package org.bmSpring.servlet.model;

import lombok.Getter;

import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPool {

    private final ArrayBlockingQueue<Thread> threadPool;

    @Getter
    private long inProgressCount = 0;

    public ThreadPool() {
        this.threadPool = new ArrayBlockingQueue<>(100);
    }

    public ThreadPool(int queueSize) {
        this.threadPool = new ArrayBlockingQueue<>(queueSize);
    }

    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("ThreadPool Wait");
                    Thread take = threadPool.take();

                    System.out.println("ThreadPool Start");
                    System.out.println(inProgressCount);
                    take.start();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void addThread(Thread thread) throws InterruptedException {
        threadPool.put(thread);
    }

    private synchronized void plusProgressCount() {
        inProgressCount++;
    }

    public synchronized void minusProgressCount() {
        inProgressCount--;
    }

}
