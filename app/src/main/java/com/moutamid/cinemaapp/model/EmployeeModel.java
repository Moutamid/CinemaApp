package com.moutamid.cinemaapp.model;

import java.sql.Date;

public class EmployeeModel {
    String FirstName, LastName, Address, sex;
    int ESSN, Salary;
    Date BirthDate;

    public EmployeeModel() {
    }

    public EmployeeModel(String firstName, String lastName, String address, String sex, int ESSN, int salary, Date birthDate) {
        FirstName = firstName;
        LastName = lastName;
        Address = address;
        this.sex = sex;
        this.ESSN = ESSN;
        Salary = salary;
        BirthDate = birthDate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getESSN() {
        return ESSN;
    }

    public void setESSN(int ESSN) {
        this.ESSN = ESSN;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date birthDate) {
        BirthDate = birthDate;
    }
}
