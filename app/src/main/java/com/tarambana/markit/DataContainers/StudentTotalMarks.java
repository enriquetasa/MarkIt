package com.tarambana.markit.DataContainers;

public class StudentTotalMarks {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("TotalStudentID")
    public String totalMarksStudentID;

    @com.google.gson.annotations.SerializedName("TotalAssignmentID")
    public String totalMarksAssignmentID;

    @com.google.gson.annotations.SerializedName("TotalMarksAchieved")
    public String totalMarksAchieved;

    public String getStudentTotalMarksStudentID() {
        return totalMarksStudentID;
    }

    public void setStudentTotalMarksStudentID(String studentTotalMarksStudentID) {
        this.totalMarksStudentID = studentTotalMarksStudentID;
    }

    public String getStudentTotalMarksAssignmentID() {
        return totalMarksAssignmentID;
    }

    public void setStudentTotalMarksAssignmentID(String studentTotalMarksAssignmentID) {
        this.totalMarksAssignmentID = studentTotalMarksAssignmentID;
    }

    public String getStudentTotalMarksAchieved() {
        return totalMarksAchieved;
    }

    public void setStudentTotalMarksAchieved(String studentTotalMarksAchieved) {
        totalMarksAchieved = studentTotalMarksAchieved;
    }
}
