package com.tarambana.markit.DataContainers;

import java.util.Date;

public class LabGroup {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("LabGroupStudentID")
    public int labGroupStudentID;

    @com.google.gson.annotations.SerializedName("LabGroupNumber")
    public int labGroupNumber;

    @com.google.gson.annotations.SerializedName("LabGroupLocation")
    public String labGroupLocation;

    @com.google.gson.annotations.SerializedName("LabGroupAssignmentID")
    public int labGroupAssignmentID;

    @com.google.gson.annotations.SerializedName("LabGroupUnit")
    public String labGroupUnit;

    @com.google.gson.annotations.SerializedName("LabGroupDate")
    public Date labGroupDate;

    public int getLabGroupStudentID() {
        return labGroupStudentID;
    }

    public int getLabGroupNumber() {
        return labGroupNumber;
    }

    public int getLabGroupAssignmentID() {
        return labGroupAssignmentID;
    }

    public String getLabGroupUnit() {
        return labGroupUnit;
    }
}
