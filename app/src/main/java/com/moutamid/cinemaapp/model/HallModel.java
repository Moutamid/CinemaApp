package com.moutamid.cinemaapp.model;

public class HallModel {
    int Hall_Number, Number_of_seats;

    public HallModel() {
    }

    public HallModel(int hall_Number, int number_of_seats) {
        Hall_Number = hall_Number;
        Number_of_seats = number_of_seats;
    }

    public int getHall_Number() {
        return Hall_Number;
    }

    public void setHall_Number(int hall_Number) {
        Hall_Number = hall_Number;
    }

    public int getNumber_of_seats() {
        return Number_of_seats;
    }

    public void setNumber_of_seats(int number_of_seats) {
        Number_of_seats = number_of_seats;
    }
}
