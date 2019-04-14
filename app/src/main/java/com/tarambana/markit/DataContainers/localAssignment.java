package com.tarambana.markit.DataContainers;

import java.util.HashMap;

public class localAssignment {

    public int assignmentID;

    // Section
    public HashMap<Integer, String> sectionIDSectionName = new HashMap<>();

    // Part
    public HashMap<Integer, String> partIDPartName = new HashMap<>();
    public HashMap<String, Integer> partNamePartMark = new HashMap<>();
    public HashMap<Integer, Integer> partIDSectionID = new HashMap<>();
    public HashMap<Integer, Boolean> partIDPartCorrect = new HashMap<>();

    // Student
    public int studentID;
    public String studentFirstName;
    public String studentLastName;
    public int studentMarks;

    public localAssignment(){

    }

    public int getAssignmentNumber() {
        return assignmentID;
    }

    public void setAssignmentNumber(int assignmentNumber) {
        this.assignmentID = assignmentNumber;
    }

    public HashMap<Integer, String> getSectionIDSectionName() {
        return sectionIDSectionName;
    }

    public void setSectionIDSectionName(Integer sectionID, String sectionName) {
        this.sectionIDSectionName.put(sectionID, sectionName);
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

    public void setPartIDSectionID(Integer partID, Integer sectionID) {
        this.partIDSectionID.put(partID, sectionID);
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public int getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(int studentMarks) {
        this.studentMarks = studentMarks;
    }

    public void setPartIDPartCorrect(Integer partID, Boolean correct) {
        this.partIDPartCorrect.put(partID, correct);
    }
}
