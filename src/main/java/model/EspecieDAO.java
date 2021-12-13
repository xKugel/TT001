/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kugel
 */
public class EspecieDAO extends DAO{
    private static EspecieDAO instance;
    
    public static EspecieDAO getInstance() {
        return instance == null ? (instance = new EspecieDAO()) : instance;
    }
    
    public Especie create (String nomeEspecie) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO especies (nome_especie) VALUES (?)");
            stmt.setString(1, nomeEspecie);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(EspecieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("especies","id"));
    }
    
    public boolean isLastEmpty(){
        Especie ultimaEspecie = this.retrieveById(lastId("especies","id"));
        if (ultimaEspecie != null) {
            return ultimaEspecie.getNomeEspecie().isBlank();
        }
        return false;
    }

    public Especie buildObject(ResultSet rs) {
        Especie especie = null;
        try {
            especie = new Especie(rs.getInt("id"), rs.getString("nome_especie"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return especie;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Especie> especies = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                especies.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return especies;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM especies");
    }
    
    // RetrieveLast
    public Especie retrieveLast(){
        List<Especie> especies = this.retrieve("SELECT * FROM especies WHERE id = " + lastId("especies","id"));
        return especies.isEmpty() ? null : especies.get(0);
    }

    // RetrieveById
    public Especie retrieveById(int id) {
        List<Especie> especies = this.retrieve("SELECT * FROM especies WHERE id = " + id);
        return (especies.isEmpty()?null:especies.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM especies WHERE nome_especie LIKE '%" + nome + "%'");
    }

    public List retriveAllAnimalWithEspecie(int id) {
        return this.retrieve("Select a.id from especies as e join animais as a on a.id_especie = e.id where e.id = " + id);
    }
        
    // Updade
    public void update(Especie especies) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE especies SET nome_especie=? WHERE id=?");
            stmt.setString(1, especies.getNomeEspecie());
            stmt.setInt(2, especies.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Especie especies) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM especies WHERE id = ?");
            stmt.setInt(1, especies.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
