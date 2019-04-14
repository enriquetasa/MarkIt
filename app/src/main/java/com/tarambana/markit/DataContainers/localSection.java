package com.tarambana.markit.DataContainers;

import java.util.HashMap;

public class localSection {

    public int assignmentID;
    public int sectionID;
    public String sectionName;
    public HashMap<Integer, String> partIDPartName = new HashMap<>();
    public HashMap<Integer, Boolean> partIDPartCorrect = new HashMap<>();
    public HashMap<String, Integer> partNamePartMark = new HashMap<>();

    public localSection(){

    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
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

    public void setPartIDPartName(Integer partID, String partName) {
        this.partIDPartName.put(partID, partName);
    }

    public void setPartNamePartMark(String partName, Integer partMark) {
        this.partNamePartMark.put(partName, partMark);
    }

    public void setPartIDPartCorrect(Integer partID, Boolean correct) {
        this.partIDPartCorrect.put(partID, correct);
    }
}
