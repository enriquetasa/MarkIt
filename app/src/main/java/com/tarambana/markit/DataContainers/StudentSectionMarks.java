package com.tarambana.markit.DataContainers;

public class StudentSectionMarks {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("SectionStudentID")
    public int sectionStudentID;

    @com.google.gson.annotations.SerializedName("SectionAssignmentID")
    public int sectionAssignmentID;

    @com.google.gson.annotations.SerializedName("SectionPartID")
    public int sectionPartID;

    @com.google.gson.annotations.SerializedName("SectionID")
    public int sectionSectionID;

    @com.google.gson.annotations.SerializedName("SectionStudentMarks")
    public int sectionStudentMarks;

    @com.google.gson.annotations.SerializedName("SectionAuthor")
    public String sectionAuthor;

    public String getSectionAuthor() {
        return sectionAuthor;
    }

    public void setSectionAuthor(String sectionAuthor) {
        this.sectionAuthor = sectionAuthor;
    }

    public int getSectionStudentMarks() {
        return sectionStudentMarks;
    }

    public void setSectionStudentMarks(int sectionStudentMarks) {
        this.sectionStudentMarks = sectionStudentMarks;
    }

    public int getSectionSectionID() {
        return sectionSectionID;
    }

    public void setSectionSectionID(int sectionSectionID) {
        this.sectionSectionID = sectionSectionID;
    }

    public int getSectionPartID() {
        return sectionPartID;
    }

    public void setSectionPartID(int sectionPartID) {
        this.sectionPartID = sectionPartID;
    }

    public int getSectionAssignmentID() {
        return sectionAssignmentID;
    }

    public void setSectionAssignmentID(int sectionAssignmentID) {
        this.sectionAssignmentID = sectionAssignmentID;
    }

    public int getSectionStudentID() {
        return sectionStudentID;
    }

    public void setSectionStudentID(int sectionStudentID) {
        this.sectionStudentID = sectionStudentID;
    }
}
