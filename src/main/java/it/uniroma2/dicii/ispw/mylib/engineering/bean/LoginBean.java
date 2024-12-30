package it.uniroma2.dicii.ispw.mylib.engineering.bean;

import it.uniroma2.dicii.ispw.mylib.other.SupportedUserTypes;

public class LoginBean {
    private String email;
    private String password;
    private SupportedUserTypes type;

    public LoginBean(String email, String password) {
        this.email = email;
        this.password = password;
        this.type = null;
    }

    public LoginBean(String email, String password, SupportedUserTypes userType) {
        this.email = email;
        this.password = password;
        this.type = userType;
    }

    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}
    public SupportedUserTypes getType() {return this.type;}
}
