package it.uniroma2.dicii.ispw.mylib.engineering.bean;

public class RegisterBean extends LoginBean{
    private String name;
    private String surname;

    public RegisterBean(String name, String surname, String email, String password) {
        super(email, password);
        this.name = name;
        this.surname = surname;
    }


    public String getName() {return this.name;}
    public String getSurname() {return this.surname;}

}
