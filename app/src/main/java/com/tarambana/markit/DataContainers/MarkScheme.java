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
}
