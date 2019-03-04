package com.tarambana.markit.DataContainers;

public class Student {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("StudentLastName")
    public String studentLastName;

    @com.google.gson.annotations.SerializedName("StudentFirstName")
    public String studentFirstName;

    @com.google.gson.annotations.SerializedName("StudentID")
    public String studentID;

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
