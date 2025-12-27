package com.mycompany.contactdbapp;

public class Contact {
    private int id;
    private String national_id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;

    public Contact() {}

    public Contact(int id, String firstName, String middleName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getNational_id() { return national_id; }
    public void setNational_id(String national_id) {this.national_id = national_id; }

    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
