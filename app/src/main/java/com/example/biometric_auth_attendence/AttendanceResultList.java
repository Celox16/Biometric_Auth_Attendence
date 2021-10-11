package com.example.biometric_auth_attendence;

public class AttendanceResultList {
    private String userID, userName, arrivalTime, status;
    private int studentNumber;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public AttendanceResultList(String userID, int studentNumber, String userName, String arrivalTime, String status){
        this.userID = userID;
        this.studentNumber = studentNumber;
        this.userName = userName;
        this.arrivalTime = arrivalTime;
        this.status = status;
    }
}
