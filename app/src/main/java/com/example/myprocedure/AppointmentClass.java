package com.example.myprocedure;

public class AppointmentClass {
    Long timeStart;
    Boolean isAvailble;
    String Uid;
    int number;

    public AppointmentClass(Long timeStart, Boolean isAvailble, String uid, int number) {
        this.timeStart = timeStart;
        this.isAvailble = isAvailble;
        Uid = uid;
        this.number = number;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public void setAvailble(Boolean availble) {
        isAvailble = availble;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public Boolean getAvailble() {
        return isAvailble;
    }

    public String getUid() {
        return Uid;
    }

    public int getNumber() {
        return number;
    }
}
