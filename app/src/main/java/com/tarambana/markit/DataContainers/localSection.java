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

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
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

    public HashMap<Integer, String> getPartIDPartName() {
        return partIDPartName;
    }

    public void setPartIDPartName(Integer partID, String partName) {
        this.partIDPartName.put(partID, partName);
    }

    public HashMap<String, Integer> getPartNamePartMark() {
        return partNamePartMark;
    }

    public void setPartNamePartMark(String partName, Integer partMark) {
        this.partNamePartMark.put(partName, partMark);
    }

    public HashMap<Integer, Boolean> getPartIDPartCorrect() {
        return partIDPartCorrect;
    }

    public void setPartIDPartCorrect(Integer partID, Boolean correct) {
        this.partIDPartCorrect.put(partID, correct);
    }
}
