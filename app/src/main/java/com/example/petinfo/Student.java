package com.example.petinfo;

import android.icu.lang.UCharacter;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private String phone;
    private String gender;
    private String _id;
    private String email;
    private String branch;
    private String year;
    private String attendance;

    public Student(String name, String phone, String gender, String _id, String email, String branch, String year, String attendance) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this._id = _id;
        this.email = email;
        this.branch = branch;
        this.year = year;
        this.attendance = attendance;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getPhone() {
        return phone;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    String getGender() {return gender;
    }

    void setGender() {
        this.gender = gender;
    }

    String get_id() {
        return _id;
    }

    void set_id() {
        this._id = _id;
    }

    String getEmail() {
        return email;
    }

    void setEmail() {
        this.email = email;
    }

    String getBranch() {
        return branch;
    }

    void setBranch() {
        this.branch = branch;
    }

    String getYear() {
        return year;
    }

    void setYear() {
        this.year = year;
    }

    String getAttendance() {
        return attendance;
    }

    void setAttendance() {
        this.attendance = attendance;
    }


}
