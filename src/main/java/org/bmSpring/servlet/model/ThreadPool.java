package org.bmSpring.servlet.model;

import lombok.Getter;

import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPool {

    private final ArrayBlockingQueue<Thread> waitQueue;

    @Getter
    private int threadCount = 50;

    @Getter
    private int waitQueueSize = 100;

    public ThreadPool() {
        this.waitQueue = new ArrayBlockingQueue<>(100);
        start();
    }

    public ThreadPool(int queueSize) {
        this.waitQueue = new ArrayBlockingQueue<>(queueSize);
        start();
    }

    public ThreadPool(int queueSize, int threadCount) {
        this.waitQueue = new ArrayBlockingQueue<>(queueSize);
        this.threadCount = threadCount;
        this.waitQueueSize = queueSize;
        start();
    }

    private void start() {
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread take = waitQueue.take();

                        take.start();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }

    public void addThread(Thread thread) {
        new Thread(() -> {
            waitQueue.add(thread);
        }).start();
    }
}
