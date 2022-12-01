package com.moutamid.cinemaapp.model;

public class UserModel {
    String UserName, Email, PhoneNumber, userPassword;
    int TicketNumber, seatNumber;

    public UserModel() { }

    public UserModel(String userName, String email, String phoneNumber, String userPassword, int ticketNumber, int seatNumber) {
        UserName = userName;
        Email = email;
        PhoneNumber = phoneNumber;
        this.userPassword = userPassword;
        TicketNumber = ticketNumber;
        this.seatNumber = seatNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getTicketNumber() {
        return TicketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        TicketNumber = ticketNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
