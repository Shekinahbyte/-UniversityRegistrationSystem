package com.example.universityregistration;

public class Student {

    private String fullName;
    private String idNumber;
    private String regNumber;
    private String gender;
    private String course;
    private String department;

    // Empty constructor required by Firebase
    public Student() {
    }

    // Constructor
    public Student(String fullName, String idNumber,
                   String regNumber, String gender,
                   String course, String department) {

        this.fullName = fullName;
        this.idNumber = idNumber;
        this.regNumber = regNumber;
        this.gender = gender;
        this.course = course;
        this.department = department;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getCourse() {
        return course;
    }

    public String getDepartment() {
        return department;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}