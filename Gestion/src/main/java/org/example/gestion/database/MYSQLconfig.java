package org.example.gestion.database;

public class MYSQLconfig implements IDatabaseConfig {
    @Override
    public String getUrl() {
        return "jdbc:mysql://localhost:3306/electronique_db";
    }
    @Override
    public String getUser() {
        return "root";
    }
    @Override
    public String getPassword() {
        return "";
    }
    
}
