package com.example.biometric_auth_attendence;

public class ListViewBtnItem {
    private int subjectCode;
    private String subjectName, dayOfTheWeek, professor, startTime, endTime, blutoothName, userID;

    public int getSubjectCode(){
        return this.subjectCode;
    }

    public String getSubjectName(){
        return this.subjectName;
    }

    public String getDayOfTheWeek(){
        return this.dayOfTheWeek;
    }

    public String getProfessor(){
        return this.professor;
    }

    public String getStartTime(){
        return this.startTime;
    }

    public String getEndTime(){
        return this.endTime;
    }

    public String getBlutoothName(){
        return this.blutoothName;
    }

    public String getUserID(){
        return this.userID;
    }
}
