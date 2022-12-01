package com.moutamid.cinemaapp.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class MovieModel implements Serializable {
    String MOVIEname, category, Suitable_age, Director, writer;
    int production_year;
    Date show_date;
    Time show_times;

    public MovieModel() {
    }

    public MovieModel(String MOVIEname, String category, String suitable_age, String director, String writer, int production_year, Date show_date, Time show_times) {
        this.MOVIEname = MOVIEname;
        this.category = category;
        Suitable_age = suitable_age;
        Director = director;
        this.writer = writer;
        this.production_year = production_year;
        this.show_date = show_date;
        this.show_times = show_times;
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

    public Date getShow_date() {
        return show_date;
    }

    public void setShow_date(Date show_date) {
        this.show_date = show_date;
    }

    public Time getShow_times() {
        return show_times;
    }

    public void setShow_times(Time show_times) {
        this.show_times = show_times;
    }
}
