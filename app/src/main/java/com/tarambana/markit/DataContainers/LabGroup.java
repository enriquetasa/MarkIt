package com.tarambana.markit.DataContainers;

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

    public int getLabGroupStudentID() {
        return labGroupStudentID;
    }

    public void setLabGroupStudentID(int labGroupStudentID) {
        this.labGroupStudentID = labGroupStudentID;
    }

    public int getLabGroupNumber() {
        return labGroupNumber;
    }

    public void setLabGroupNumber(int labGroupNumber) {
        this.labGroupNumber = labGroupNumber;
    }

    public String getLabGroupLocation() {
        return labGroupLocation;
    }

    public void setLabGroupLocation(String labGroupLocation) {
        this.labGroupLocation = labGroupLocation;
    }

    public int getLabGroupAssignmentID() {
        return labGroupAssignmentID;
    }

    public void setLabGroupAssignmentID(int labGroupAssignmentID) {
        this.labGroupAssignmentID = labGroupAssignmentID;
    }

    public String getLabGroupUnit() {
        return labGroupUnit;
    }

    public void setLabGroupUnit(String labGroupUnit) {
        this.labGroupUnit = labGroupUnit;
    }
}
