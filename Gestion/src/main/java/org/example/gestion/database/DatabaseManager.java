package org.example.gestion.admin;

import java.util.ArrayList;

public class DatabaseManager {
    private final ArrayList<Produit> produitsDB = new ArrayList<>();

    public ArrayList<Produit> getProduitsDB() {
        return new ArrayList<>(produitsDB);
    }

    public void ajouterProduit(Produit p) {
        produitsDB.add(p);
    }

    public void supprimerProduit(String nom) {
        produitsDB.removeIf(p -> p.getNom().equals(nom));
    }

    public void modifierProduit(Produit p) {
        for (Produit prod : produitsDB) {
            if (prod.getNom().equals(p.getNom())) {
                prod.setPrix(p.getPrix());
                prod.setStock(p.getStock());
            }
        }
    }
}