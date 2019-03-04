package com.tarambana.markit.DataContainers;

public class MarkScheme {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("MarkSchemeAssignmentID")
    public int markSchemeAssignmentID;

    @com.google.gson.annotations.SerializedName("MarkSchemeAssignmentName")
    public String markSchemeAssignmentName;

    @com.google.gson.annotations.SerializedName("MarkSchemeAssignmentAvailableMarks")
    public int markSchemeAssignmentAvailableMarks;

    @com.google.gson.annotations.SerializedName("MarkSchemeAuthor")
    public String markSchemeAuthor;

    public int getMarkSchemeAssignmentID() {
        return markSchemeAssignmentID;
    }

    public void setMarkSchemeAssignmentID(int markSchemeAssignmentID) {
        this.markSchemeAssignmentID = markSchemeAssignmentID;
    }

    public String getMarkSchemeAssignmentName() {
        return markSchemeAssignmentName;
    }

    public void setMarkSchemeAssignmentName(String markSchemeAssignmentName) {
        this.markSchemeAssignmentName = markSchemeAssignmentName;
    }

    public int getMarkSchemeAssignmentAvailableMarks() {
        return markSchemeAssignmentAvailableMarks;
    }

    public void setMarkSchemeAssignmentAvailableMarks(int markSchemeAssignmentAvailableMarks) {
        this.markSchemeAssignmentAvailableMarks = markSchemeAssignmentAvailableMarks;
    }

    public String getMarkSchemeAuthor() {
        return markSchemeAuthor;
    }

    public void setMarkSchemeAuthor(String markSchemeAuthor) {
        this.markSchemeAuthor = markSchemeAuthor;
    }
}
