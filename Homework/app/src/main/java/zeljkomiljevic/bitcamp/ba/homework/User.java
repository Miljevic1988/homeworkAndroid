package zeljkomiljevic.bitcamp.ba.homework;

import java.util.Date;
import java.util.UUID;

public class User {

    private UUID UUID;
    private String Name;
    private String Surname;
    private Date Date;

    public User(String name, String surname) {
        UUID = UUID.randomUUID();
        Name = name;
        Surname = surname;
        Date = new Date();
    }

    public UUID getUUID() {
        return UUID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public Date getDate() {
        return Date;
    }
}