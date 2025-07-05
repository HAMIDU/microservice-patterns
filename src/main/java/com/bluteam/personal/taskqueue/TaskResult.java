package com.bluteam.personal.taskqueue;

import java.util.HashMap;
import java.util.Map;

public class TaskResult {
    private final String taskId;
    private final boolean success;
    private final String message;
    private final Map<String, Object> data;
    private final long completedTime;

    public TaskResult(String taskId, boolean success, String message, Map<String, Object> data) {
        this.taskId = taskId;
        this.success = success;
        this.message = message;
        this.data = data != null ? new HashMap<>(data) : new HashMap<>();
        this.completedTime = System.currentTimeMillis();
    }

    // Getters
    public String getTaskId() {
        return taskId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public long getCompletedTime() {
        return completedTime;
    }

    @Override
    public String toString() {
        return String.format("TaskResult[taskId=%s, success=%s, message=%s, data=%s]",
            taskId, success, message, data);
    }
}

