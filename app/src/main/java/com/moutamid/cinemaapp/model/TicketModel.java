package com.moutamid.cinemaapp.model;

public class TicketModel {
    int Ticket_Number, seat_number;
    int hallNumber;
    String movie_NAme;

    public TicketModel() {
    }

    public TicketModel(int ticket_Number, int seat_number, int hallNumber, String movie_NAme) {
        Ticket_Number = ticket_Number;
        this.seat_number = seat_number;
        this.hallNumber = hallNumber;
        this.movie_NAme = movie_NAme;
    }

    public int getTicket_Number() {
        return Ticket_Number;
    }

    public void setTicket_Number(int ticket_Number) {
        Ticket_Number = ticket_Number;
    }

    public int getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(int seat_number) {
        this.seat_number = seat_number;
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }

    public String getMovie_NAme() {
        return movie_NAme;
    }

    public void setMovie_NAme(String movie_NAme) {
        this.movie_NAme = movie_NAme;
    }
}
