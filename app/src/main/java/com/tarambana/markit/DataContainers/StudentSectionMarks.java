package com.tarambana.markit.DataContainers;

public class StudentSectionMarks {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("SectionStudentID")
    public String sectionStudentID;

    @com.google.gson.annotations.SerializedName("SectionAssignmentID")
    public String sectionAssignmentID;

    @com.google.gson.annotations.SerializedName("SectionPartID")
    public String sectionPartID;

    @com.google.gson.annotations.SerializedName("SectionID")
    public String sectionSectionID;

    @com.google.gson.annotations.SerializedName("SectionStudentMarks")
    public String sectionStudentMarks;

    @com.google.gson.annotations.SerializedName("SectionAuthor")
    public String sectionAuthor;

    public String getSectionAuthor() {
        return sectionAuthor;
    }

    public void setSectionAuthor(String sectionAuthor) {
        this.sectionAuthor = sectionAuthor;
    }

    public String getSectionStudentMarks() {
        return sectionStudentMarks;
    }

    public void setSectionStudentMarks(String sectionStudentMarks) {
        this.sectionStudentMarks = sectionStudentMarks;
    }

    public String getSectionSectionID() {
        return sectionSectionID;
    }

    public void setSectionSectionID(String sectionSectionID) {
        this.sectionSectionID = sectionSectionID;
    }

    public String getSectionPartID() {
        return sectionPartID;
    }

    public void setSectionPartID(String sectionPartID) {
        this.sectionPartID = sectionPartID;
    }

    public String getSectionAssignmentID() {
        return sectionAssignmentID;
    }

    public void setSectionAssignmentID(String sectionAssignmentID) {
        this.sectionAssignmentID = sectionAssignmentID;
    }

    public String getSectionStudentID() {
        return sectionStudentID;
    }

    public void setSectionStudentID(String sectionStudentID) {
        this.sectionStudentID = sectionStudentID;
    }
}
