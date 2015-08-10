package com.example.testApp;

/**
 * Created by root on 06.08.15.
 */
public class PersonEntry {

    private int id;
    private String firstName;
    private String lastName;
    private int phone;
    private String email;

    public PersonEntry()
    {
        this.firstName = "Default First Name";
        this.lastName = "Default Last Name";
        this.phone = 0;
        this.email = "Default Email";
    }

    public PersonEntry(int id, String firstName, String lastName, int phone, String email)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

}
