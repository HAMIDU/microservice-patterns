package com.bluteam.personal.taskqueue;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class TaskQueueManager {
    private final PriorityBlockingQueue<Task> taskQueue;
    private final ExecutorService executorService;
    private final CompletionService<TaskResult> completionService;
    private final AtomicLong totalTasks = new AtomicLong(0);
    public final AtomicLong completedTasks = new AtomicLong(0);
    private volatile boolean isShutdown = false;

    public TaskQueueManager(int workerThreads) {
        this.taskQueue = new PriorityBlockingQueue<>();
        this.executorService = Executors.newFixedThreadPool(workerThreads, r -> {
            Thread t = new Thread(r);
            t.setName("TaskWorker-" + t.getId());
            t.setDaemon(true);
            return t;
        });
        this.completionService = new ExecutorCompletionService<>(executorService);

        startResultProcessor();
    }

    public boolean submitTask(Task task) {
        if (isShutdown) {
            System.out.println("❌ Cannot submit task - TaskManager is shutdown");
            return false;
        }

        taskQueue.offer(task);
        totalTasks.incrementAndGet();
        System.out.println("📥 Task submitted: " + task);

        completionService.submit(task);

        return true;
    }

    private void startResultProcessor() {
        Thread resultProcessor = new Thread(() -> {
            while (!isShutdown || !taskQueue.isEmpty()) {
                try {
                    Future<TaskResult> future = completionService.poll(1, TimeUnit.SECONDS);
                    if (future != null) {
                        TaskResult result = future.get();
                        processResult(result);
                        completedTasks.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (ExecutionException e) {
                    System.err.println("❌ Task execution error: " + e.getCause().getMessage());
                    completedTasks.incrementAndGet();
                }
            }
        });

        resultProcessor.setName("ResultProcessor");
        resultProcessor.setDaemon(true);
        resultProcessor.start();
    }

    private void processResult(TaskResult result) {
        System.out.println("📤 Task result received: " + result.getMessage());
        if (result.isSuccess()) {
            System.out.println("   ✅ Data: " + result.getData());
        } else {
            System.out.println("   ❌ Failed: " + result.getMessage());
        }
    }

    public void printStats() {
        System.out.println("\n=== Task Queue Stats ===");
        System.out.println("Total submitted: " + totalTasks.get());
        System.out.println("Completed: " + completedTasks.get());
        System.out.println("Pending: " + (totalTasks.get() - completedTasks.get()));
        System.out.println("Queue size: " + taskQueue.size());
        System.out.println("=======================\n");
    }

    public void shutdown() {
        isShutdown = true;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("🔴 TaskQueueManager shutdown completed");
    }

    public boolean isTerminated() {
        return executorService.isTerminated();
    }
}
