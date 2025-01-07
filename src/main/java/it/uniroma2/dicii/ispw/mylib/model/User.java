package it.uniroma2.dicii.ispw.mylib.model;

import it.uniroma2.dicii.ispw.mylib.other.SupportedUserTypes;

public abstract class User {
    private String email;
    private String password;
    private String name;
    private String surname;

    //utile per UserInMemoryDAO
    private SupportedUserTypes type;

    protected User(String email, String name, String surname){
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    protected User(String email, String name, String surname, String password){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    protected User(String email, String name, String surname, String password, SupportedUserTypes type){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.type = type;
    }

    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}

    public String getName() {return this.name;}
    public String getSurname() {return this.surname;}

    public SupportedUserTypes getType() {return this.type;}
}
