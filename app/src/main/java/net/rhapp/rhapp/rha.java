package net.rhapp.rhapp;

/**
 * Created by GaYoung on 1/31/15.
 */
public class rha {

    String firstName;
    String lastName;
    String resCollege;
    int roomNumber;
    String phoneNumber;

    // constructor
    public rha(String fName, String lName, String resCol, int roomNum, String phoneNum) {
        this.firstName = fName;
        this.lastName = lName;
        this.resCollege = resCol;
        this.roomNumber = roomNum;
        this.phoneNumber = phoneNum;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getResCollege() {
        return this.resCollege;
    }

    public int getRoomNumber() {
        return this.roomNumber;
    }

    public String getLocation() {
        return this.resCollege + " " + this.roomNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
