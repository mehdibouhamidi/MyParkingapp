package com.example.myparking.model;

public class Reservation {
    private String parking;
    private String jour;
    private String heure;
    private String prix;

    public  Reservation(){
    }
    public Reservation(String parking,String jour,String heure,String prix){
        this.parking=parking;
        this.jour=jour;
        this.heure=heure;
        this.prix=prix;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}
