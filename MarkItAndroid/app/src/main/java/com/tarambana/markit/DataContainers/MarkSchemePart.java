package com.tarambana.markit.DataContainers;

public class MarkSchemePart {

    @com.google.gson.annotations.SerializedName("id")
    private String mID;

    @com.google.gson.annotations.SerializedName("PartAssignmentID")
    public int partAssignmentID;

    @com.google.gson.annotations.SerializedName("PartSectionID")
    public int partSectionID;

    @com.google.gson.annotations.SerializedName("PartPartID")
    public int partID;

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    @com.google.gson.annotations.SerializedName("PartName")
    public String partName;

    @com.google.gson.annotations.SerializedName("PartAvailableMarks")
    public int partAvailableMarks;

    @com.google.gson.annotations.SerializedName("PartAuthor")
    public String partAuthor;

    public int getPartSectionID() {
        return partSectionID;
    }

    public String getPartName() {
        return partName;
    }

    public int getPartAvailableMarks() {
        return partAvailableMarks;
    }

}
