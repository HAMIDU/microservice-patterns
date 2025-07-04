package com.bluteam.personal.cache;

public class Student {
    String id;
    String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Student [id=" + id + ", name=" + name + "]";
    }
}
