package org.example.sql_tutorial.dto;

public class CountPatientForEachAge {
    private int age;
    private int count;

    public CountPatientForEachAge(int age, int count) {
        this.age = age;
        this.count = count;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Patient for age '" + age +
                "': " + count;
    }
}
