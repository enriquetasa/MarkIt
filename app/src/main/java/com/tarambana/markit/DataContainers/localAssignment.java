package com.tarambana.markit.DataContainers;

import java.util.List;

public class localAssignment {

    public String assignmentName;
    public int assignmentNumber;
    public int assignmentMarks;

    public List<String> sectionNames;
    public List<Integer> sectionNumbers;
    public List<Integer> sectionMarks;

    public List<String> partNames;
    public List<Integer> partNumbers;
    public List<Integer> partMarks;

    public int studentID;
    public String studentFirstName;
    public String studentLastName;
    public int studentMarks;

    public localAssignment(){

    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public int getAssignmentNumber() {
        return assignmentNumber;
    }

    public void setAssignmentNumber(int assignmentNumber) {
        this.assignmentNumber = assignmentNumber;
    }

    public int getAssignmentMarks() {
        return assignmentMarks;
    }

    public void setAssignmentMarks(int assignmentMarks) {
        this.assignmentMarks = assignmentMarks;
    }

    public List<String> getSectionNames() {
        return sectionNames;
    }

    public void setSectionNames(List<String> sectionNames) {
        this.sectionNames = sectionNames;
    }

    public List<Integer> getSectionNumbers() {
        return sectionNumbers;
    }

    public void setSectionNumbers(List<Integer> sectionNumbers) {
        this.sectionNumbers = sectionNumbers;
    }

    public List<Integer> getSectionMarks() {
        return sectionMarks;
    }

    public void setSectionMarks(List<Integer> sectionMarks) {
        this.sectionMarks = sectionMarks;
    }

    public List<String> getPartNames() {
        return partNames;
    }

    public void setPartNames(List<String> partNames) {
        this.partNames = partNames;
    }

    public List<Integer> getPartNumbers() {
        return partNumbers;
    }

    public void setPartNumbers(List<Integer> partNumbers) {
        this.partNumbers = partNumbers;
    }

    public List<Integer> getPartMarks() {
        return partMarks;
    }

    public void setPartMarks(List<Integer> partMarks) {
        this.partMarks = partMarks;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(int studentMarks) {
        this.studentMarks = studentMarks;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }
}
