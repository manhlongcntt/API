package com.example.api.model;

import java.io.Serializable;

public class Student implements Serializable {
    private int mID;
    private String mName;
    private int mClass;
    private int mNumber;
    private String mEmail;

    public Student() {

    }
    public Student(int mID, String mName, int mClass, int mNumber, String mEmail) {
        this.mID = mID;
        this.mName = mName;
        this.mClass = mClass;
        this.mNumber = mNumber;
        this.mEmail = mEmail;
    }

    public Student(String mName, int mClass, int mNumber, String mEmail) {
        this.mName = mName;
        this.mClass = mClass;
        this.mNumber = mNumber;
        this.mEmail = mEmail;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmClass() {
        return mClass;
    }

    public void setmClass(int mClass) {
        this.mClass = mClass;
    }

    public int getmNumber() {
        return mNumber;
    }

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
