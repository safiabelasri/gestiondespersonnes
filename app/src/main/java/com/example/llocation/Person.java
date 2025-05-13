package com.example.llocation;

public class Person {
    private int id;
    private String name;
    private String birthDate;
    private String phone;
    private String photoPath;

    public Person(int id, String name, String birthDate, String phone, String photoPath) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.photoPath = photoPath;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBirthDate() { return birthDate; }
    public String getPhone() { return phone; }
    public String getPhotoPath() { return photoPath; }
}
