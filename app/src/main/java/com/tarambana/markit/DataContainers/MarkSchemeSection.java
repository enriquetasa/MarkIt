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
    public String sectionAvailableMarks;

    @com.google.gson.annotations.SerializedName("SectionAuthor")
    public String sectionAuthor;

    public int getSectionAssignmentID() {
        return sectionAssignmentID;
    }

    public void setSectionAssignmentID(int sectionAssignmentID) {
        this.sectionAssignmentID = sectionAssignmentID;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionAvailableMarks() {
        return sectionAvailableMarks;
    }

    public void setSectionAvailableMarks(String sectionAvailableMarks) {
        this.sectionAvailableMarks = sectionAvailableMarks;
    }

    public String getSectionAuthor() {
        return sectionAuthor;
    }

    public void setSectionAuthor(String sectionAuthor) {
        this.sectionAuthor = sectionAuthor;
    }
}
