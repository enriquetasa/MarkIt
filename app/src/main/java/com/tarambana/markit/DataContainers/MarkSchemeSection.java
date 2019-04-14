package com.tarambana.markit.DataContainers;

public class MarkSchemeSection {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("SectionAssignmentID")
    public int sectionAssignmentID;

    @com.google.gson.annotations.SerializedName("SectionID")
    public int sectionID;

    @com.google.gson.annotations.SerializedName("SectionName")
    public String sectionName;

    @com.google.gson.annotations.SerializedName("SectionAvailableMarks")
    public int sectionAvailableMarks;

    @com.google.gson.annotations.SerializedName("SectionAuthor")
    public String sectionAuthor;

    public int getSectionID() {
        return sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }
}
