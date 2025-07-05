package com.bluteam.personal.taskqueue;

import java.util.HashMap;
import java.util.Map;

public class TaskQueueMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=====Distributed Task Queue Started=====\n");

        // ایجاد TaskManager با 4 worker thread
        TaskQueueManager taskManager = new TaskQueueManager(4);

        // ایجاد انواع مختلف Task
        Task[] tasks = {
            // IMAGE_RESIZE tasks
            createImageResizeTask("photo1.jpg", 800, 600, TaskPriority.HIGH),
            createImageResizeTask("photo2.jpg", 1920, 1080, TaskPriority.MEDIUM),
            createImageResizeTask("photo3.jpg", 640, 480, TaskPriority.LOW),

            // EMAIL_SEND tasks
            createEmailTask("user@example.com", "Welcome Email", TaskPriority.HIGH),
            createEmailTask("admin@company.com", "Daily Report", TaskPriority.MEDIUM),

            // DATA_BACKUP tasks
            createBackupTask("user_database", TaskPriority.LOW),
            createBackupTask("transaction_database", TaskPriority.HIGH),

            // REPORT_GENERATE tasks
            createReportTask("Sales Report", TaskPriority.MEDIUM),
            createReportTask("User Analytics", TaskPriority.LOW),
            createReportTask("Financial Summary", TaskPriority.HIGH)
        };

        // Submit کردن همه tasks
        System.out.println("📝 Submitting tasks...\n");
        for (Task task : tasks) {
            taskManager.submitTask(task);
            Thread.sleep(200); // کمی تاخیر برای مشاهده بهتر
        }

        for (int i = 0; i < 30; i++) {
            Thread.sleep(1000);
            taskManager.printStats();

            if (taskManager.completedTasks.get() >= tasks.length) {
                break;
            }
        }

        System.out.println("📝 Adding more tasks during runtime...\n");
        taskManager.submitTask(createImageResizeTask("late_photo.jpg", 1024, 768, TaskPriority.HIGH));
        taskManager.submitTask(createEmailTask("late_user@example.com", "Late Email", TaskPriority.MEDIUM));

        Thread.sleep(5000);

        taskManager.printStats();

        System.out.println("🔴 Shutting down TaskQueueManager...");
        taskManager.shutdown();

        System.out.println("\n=====Task Queue Test Completed=====");
    }

    // Helper methods برای ایجاد انواع مختلف Task
    private static Task createImageResizeTask(String imageName, int width, int height, TaskPriority priority) {
        Map<String, Object> data = new HashMap<>();
        data.put("imageName", imageName);
        data.put("width", width);
        data.put("height", height);
        return new Task("IMAGE_RESIZE", data, priority);
    }

    private static Task createEmailTask(String recipient, String subject, TaskPriority priority) {
        Map<String, Object> data = new HashMap<>();
        data.put("recipient", recipient);
        data.put("subject", subject);
        return new Task("EMAIL_SEND", data, priority);
    }

    private static Task createBackupTask(String database, TaskPriority priority) {
        Map<String, Object> data = new HashMap<>();
        data.put("database", database);
        return new Task("DATA_BACKUP", data, priority);
    }

    private static Task createReportTask(String reportType, TaskPriority priority) {
        Map<String, Object> data = new HashMap<>();
        data.put("reportType", reportType);
        return new Task("REPORT_GENERATE", data, priority);
    }
}
