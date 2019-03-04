package com.tarambana.markit.DataContainers;

public class MarkSchemePart {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("PartAssignmentID")
    public int partAssignmentID;

    @com.google.gson.annotations.SerializedName("PartSectionID")
    public int partSectionID;

    @com.google.gson.annotations.SerializedName("PartName")
    public String partName;

    @com.google.gson.annotations.SerializedName("PartAvailableMarks")
    public int partAvailableMarks;

    @com.google.gson.annotations.SerializedName("PartAuthor")
    public String partAuthor;

    public int getPartAssignmentID() {
        return partAssignmentID;
    }

    public void setPartAssignmentID(int partAssignmentID) {
        this.partAssignmentID = partAssignmentID;
    }

    public int getPartSectionID() {
        return partSectionID;
    }

    public void setPartSectionID(int partSectionID) {
        this.partSectionID = partSectionID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getPartAvailableMarks() {
        return partAvailableMarks;
    }

    public void setPartAvailableMarks(int partAvailableMarks) {
        this.partAvailableMarks = partAvailableMarks;
    }

    public String getPartAuthor() {
        return partAuthor;
    }

    public void setPartAuthor(String partAuthor) {
        this.partAuthor = partAuthor;
    }
}
