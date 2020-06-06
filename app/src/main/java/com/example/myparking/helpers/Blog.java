package com.example.myparking.helpers;

public class Blog {
    private String email;
    private String matricule;
    private String username;

    public Blog(String email, String matricule, String username) {
        this.email = email;
        this.matricule = matricule;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Blog(){

    }
}
