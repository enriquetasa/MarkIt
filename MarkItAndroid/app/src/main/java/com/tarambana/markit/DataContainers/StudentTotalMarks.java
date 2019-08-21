package com.tarambana.markit.DataContainers;

public class StudentTotalMarks {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("TotalStudentID")
    public int totalMarksStudentID;

    @com.google.gson.annotations.SerializedName("TotalAssignmentID")
    public int totalMarksAssignmentID;

    @com.google.gson.annotations.SerializedName("TotalMarksAchieved")
    public int totalMarksAchieved;

    public int getStudentTotalMarksStudentID() {
        return totalMarksStudentID;
    }

    public void setStudentTotalMarksStudentID(int studentTotalMarksStudentID) {
        this.totalMarksStudentID = studentTotalMarksStudentID;
    }

    public int getStudentTotalMarksAssignmentID() {
        return totalMarksAssignmentID;
    }

    public void setStudentTotalMarksAssignmentID(int studentTotalMarksAssignmentID) {
        this.totalMarksAssignmentID = studentTotalMarksAssignmentID;
    }

    public int getStudentTotalMarksAchieved() {
        return totalMarksAchieved;
    }

    public void setStudentTotalMarksAchieved(int studentTotalMarksAchieved) {
        totalMarksAchieved = studentTotalMarksAchieved;
    }
}
