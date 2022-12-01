package com.moutamid.cinemaapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity(tableName = "Cart")
public class CartModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "movieName")
    String MOVIEname;

    @ColumnInfo(name = "category")
    String category;

    @ColumnInfo(name = "age")
    String Suitable_age;

    @ColumnInfo(name = "director")
    String Director;

    @ColumnInfo(name = "writer")
    String writer;

    @ColumnInfo(name = "productionYear")
    int production_year;

    @ColumnInfo(name = "showDate")
    String show_date;

    @ColumnInfo(name = "showTimes")
    String show_times;

    @ColumnInfo(name = "ticketNumber")
    int Ticket_Number;

    @ColumnInfo(name = "seatNumber")
    int seat_number;

    @ColumnInfo(name = "hallNumber")
    int hallNumber;

    @ColumnInfo(name = "total")
    int total;

    public CartModel() {
    }

    public CartModel(String MOVIEname, String category, String suitable_age, String director, String writer, int production_year, String show_date, String show_times, int ticket_Number, int seat_number, int hallNumber, int total) {
        this.MOVIEname = MOVIEname;
        this.category = category;
        Suitable_age = suitable_age;
        Director = director;
        this.writer = writer;
        this.production_year = production_year;
        this.show_date = show_date;
        this.show_times = show_times;
        Ticket_Number = ticket_Number;
        this.seat_number = seat_number;
        this.hallNumber = hallNumber;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMOVIEname() {
        return MOVIEname;
    }

    public void setMOVIEname(String MOVIEname) {
        this.MOVIEname = MOVIEname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSuitable_age() {
        return Suitable_age;
    }

    public void setSuitable_age(String suitable_age) {
        Suitable_age = suitable_age;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getProduction_year() {
        return production_year;
    }

    public void setProduction_year(int production_year) {
        this.production_year = production_year;
    }

    public String getShow_date() {
        return show_date;
    }

    public void setShow_date(String show_date) {
        this.show_date = show_date;
    }

    public String getShow_times() {
        return show_times;
    }

    public void setShow_times(String show_times) {
        this.show_times = show_times;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
