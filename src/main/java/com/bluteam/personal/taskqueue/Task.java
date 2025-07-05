package com.bluteam.personal.taskqueue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public class Task implements Callable<TaskResult>, Comparable<Task> {

    private final String id;
    private final String type;
    private final Map<String, Object> data;
    private final TaskPriority priority;
    private final long createdTime;

    public Task(String type, Map<String, Object> data, TaskPriority priority) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.type = type;
        this.data = data != null ? new HashMap<>(data) : new HashMap<>();
        this.priority = priority;
        this.createdTime = System.currentTimeMillis();
    }

    @Override
    public TaskResult call() throws Exception {
        System.out.println("🔄 Processing task [" + id + "] of type: " + type +
            " by thread: " + Thread.currentThread().getName());

        // شبیه‌سازی پردازش بر اساس نوع task
        TaskResult result = processTask();

        System.out.println("✅ Task [" + id + "] completed successfully");
        return result;
    }

    private TaskResult processTask() throws Exception {
        switch (type) {
            case "IMAGE_RESIZE":
                return processImageResize();
            case "EMAIL_SEND":
                return processEmailSend();
            case "DATA_BACKUP":
                return processDataBackup();
            case "REPORT_GENERATE":
                return processReportGenerate();
            default:
                return processGenericTask();
        }
    }

    private TaskResult processImageResize() throws Exception {
        String imageName = (String) data.get("imageName");
        Integer width = (Integer) data.get("width");
        Integer height = (Integer) data.get("height");

        // شبیه‌سازی resize
        Thread.sleep(2000 + (long) (Math.random() * 1000)); // 2-3 seconds

        Map<String, Object> result = new HashMap<>();
        result.put("originalImage", imageName);
        result.put("resizedImage", imageName + "_" + width + "x" + height);
        result.put("processingTime", "2.5s");

        return new TaskResult(id, true, "Image resized successfully", result);
    }

    private TaskResult processEmailSend() throws Exception {
        String recipient = (String) data.get("recipient");
        String subject = (String) data.get("subject");

        // شبیه‌سازی ارسال ایمیل
        Thread.sleep(1000 + (long) (Math.random() * 500)); // 1-1.5 seconds

        Map<String, Object> result = new HashMap<>();
        result.put("recipient", recipient);
        result.put("subject", subject);
        result.put("messageId", "msg_" + System.currentTimeMillis());

        return new TaskResult(id, true, "Email sent successfully", result);
    }

    private TaskResult processDataBackup() throws Exception {
        String database = (String) data.get("database");

        // شبیه‌سازی backup
        Thread.sleep(5000 + (long) (Math.random() * 2000)); // 5-7 seconds

        Map<String, Object> result = new HashMap<>();
        result.put("database", database);
        result.put("backupFile", database + "_backup_" + System.currentTimeMillis() + ".sql");
        result.put("backupSize", (Math.random() * 1000) + "MB");

        return new TaskResult(id, true, "Backup completed successfully", result);
    }

    private TaskResult processReportGenerate() throws Exception {
        String reportType = (String) data.get("reportType");

        // شبیه‌سازی تولید گزارش
        Thread.sleep(3000 + (long) (Math.random() * 1500)); // 3-4.5 seconds

        Map<String, Object> result = new HashMap<>();
        result.put("reportType", reportType);
        result.put("reportFile", reportType + "_report_" + System.currentTimeMillis() + ".pdf");
        result.put("pages", (int) (Math.random() * 100) + 10);

        return new TaskResult(id, true, "Report generated successfully", result);
    }

    private TaskResult processGenericTask() throws Exception {
        Thread.sleep(1000 + (long) (Math.random() * 2000)); // 1-3 seconds

        Map<String, Object> result = new HashMap<>();
        result.put("message", "Generic task completed");
        result.put("data", data);

        return new TaskResult(id, true, "Task completed successfully", result);
    }

    // برای Priority Queue
    @Override
    public int compareTo(Task other) {
        int priorityCompare = Integer.compare(this.priority.getValue(), other.priority.getValue());
        if (priorityCompare != 0) {
            return priorityCompare;
        }
        // اگه priority یکسان بود، قدیمی‌تر اول
        return Long.compare(this.createdTime, other.createdTime);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    @Override
    public String toString() {
        return String.format("Task[id=%s, type=%s, priority=%s]", id, type, priority);
    }
}
