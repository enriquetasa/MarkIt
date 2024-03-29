package com.tarambana.markit.DataContainers;

public class Student {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("StudentLastName")
    public String studentLastName;

    @com.google.gson.annotations.SerializedName("StudentFirstName")
    public String studentFirstName;

    @com.google.gson.annotations.SerializedName("StudentID")
    public int studentID;

    public String getStudentLastName() {
        return studentLastName;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public int getStudentID() {
        return studentID;
    }

}
