package com.example.biometric_auth_attendence;

public class SubjectList {
    int subjectCode;
    private String subjectName, professor, startTime, endTime, bluetoothName, dayOfTheWeek;

    public int getSubjectCode() {
        return this.subjectCode;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public String getProfessor() {
        return this.professor;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String getBluetoothName() {
        return this.bluetoothName;
    }

    public String getDayOfTheWeek() {
        return this.dayOfTheWeek;
    }

    public void setSubjectCode(int subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName){
        this.subjectName = subjectName;
    }

    public void setProfessor(String professor){
        this.professor = professor;
    }

    public void setDayOfTheWeek(String dayOfTheWeek){
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setBluetoothName(String bluetoothName){
        this.bluetoothName = bluetoothName;
    }

    public SubjectList(int subjectCode, String subjectName, String dayOfTheWeek, String professor, String startTime, String endTime, String bluetoothName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.dayOfTheWeek = dayOfTheWeek;
        this.professor = professor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bluetoothName = bluetoothName;
    }
}
