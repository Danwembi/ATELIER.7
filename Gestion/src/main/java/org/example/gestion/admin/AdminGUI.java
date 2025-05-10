package org.example.gestion.admin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminGUI {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";
    private static final ArrayList<Produit> produits = new ArrayList<>();
    private static final DefaultListModel<String> listModel = new DefaultListModel<>();
    private static final databaseManager databaseManager;

    public  AdminGUI(){
        this.databaseManager = new databaseManager();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminGUI::afficherFenetreConnexion);
    }

    private static void afficherFenetreConnexion() {
        JFrame frame = new JFrame("Connexion Admin");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Connexion");

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (username.equals(USERNAME) && password.equals(PASSWORD)) {
                frame.dispose();
                afficherInterfacePrincipale();
            } else {
                JOptionPane.showMessageDialog(frame, "Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("Utilisateur :"));
        panel.add(userField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void afficherInterfacePrincipale() {
        JFrame frame = new JFrame("Gestion du Magasin");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        produits.clear();
        produits.addAll(databaseManager.getProduits());
        listModel.clear();
        for (Produit p : produits) {
            listModel.addElement(p.toString());
        }

        JList<String> produitList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(produitList);

        JButton ajouter = new JButton("Ajouter");
        JButton supprimer = new JButton("Supprimer");
        JButton modifier = new JButton("Modifier");

        ajouter.addActionListener(e -> ajouterProduit());
        supprimer.addActionListener(e -> supprimerProduit(produitList));
        modifier.addActionListener(e -> modifierProduit(produitList));

        JPanel boutons = new JPanel();
        boutons.add(ajouter);
        boutons.add(supprimer);
        boutons.add(modifier);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(boutons, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void ajouterProduit() {
        String[] options = {"Telephone", "Ordinateur", "Accessoire"};
        String type = (String) JOptionPane.showInputDialog(null, "Type de produit :", "Ajouter Produit", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (type != null) {
            try {
                String nom = JOptionPane.showInputDialog("Nom :");
                double prix = Double.parseDouble(JOptionPane.showInputDialog("Prix :"));
                int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock :"));

                Produit p; switch (type) {
                    case "Telephone" : p = new Telephone(nom, prix, stock);
                    break;
                    case "Ordinateur" :p = new Ordinateur(nom, prix, stock);
                    break;
                    default: p =  new Accessoire(nom, prix, stock);
                    break;
                };

                databaseManager.ajouterProduit(p);
                produits.add(p);
                listModel.addElement(p.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques valides.");
            }
        }
    }

    private static void supprimerProduit(JList<String> produitList) {
        int index = produitList.getSelectedIndex();
        if (index >= 0) {
            Produit p = produits.get(index);
            databaseManager.supprimerProduit(p.getNom());
            produits.remove(index);
            listModel.remove(index);
        } else {
            JOptionPane.showMessageDialog(null, "Sélectionnez un produit à supprimer.");
        }
    }

    private static void modifierProduit(JList<String> produitList) {
        int index = produitList.getSelectedIndex();
        if (index >= 0) {
            try {
                Produit p = produits.get(index);
                double nouveauPrix = Double.parseDouble(JOptionPane.showInputDialog("Nouveau prix :", p.getPrix()));
                int nouveauStock = Integer.parseInt(JOptionPane.showInputDialog("Nouveau stock :", p.getStock()));

                p.setPrix(nouveauPrix);
                p.setStock(nouveauStock);
                databaseManager.modifierProduit(p);

                listModel.set(index, p.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques valides.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sélectionnez un produit à modifier.");
        }
    }
}