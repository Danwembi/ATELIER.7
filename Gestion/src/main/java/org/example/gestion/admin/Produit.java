package org.example.gestion.admin;

public abstract class Produit {
    private String nom;
    private double prix;
    private int stock;

    public Produit(String nom, double prix, int stock) {
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
    }

    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public int getStock() { return stock; }

    public void setPrix(double prix) { this.prix = prix; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return nom + " - Prix: " + prix + " - Stock: " + stock;
    }
}