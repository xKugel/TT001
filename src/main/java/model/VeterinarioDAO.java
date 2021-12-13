/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Veterinario;
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
public class VeterinarioDAO extends DAO{
    private static VeterinarioDAO instance;
    
    public static VeterinarioDAO getInstance() {
        return instance == null ? (instance = new VeterinarioDAO()) : instance;
    }
    
    public Veterinario create (String nomeVeterinario, String endereco, String telefone ) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO veterinarios (nome, endereco, telefone) VALUES (?, ?, ?)");
            stmt.setString(1, nomeVeterinario); 
            stmt.setString(2, endereco);
            stmt.setString(3, telefone);
            
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("veterinarios","id"));
    }
    
    public boolean isLastEmpty(){
        Veterinario ultimoVeterinario = this.retrieveById(lastId("veterinarios","id"));
        if (ultimoVeterinario != null) {
            return ultimoVeterinario.getNome().isBlank();
        }
        return false;
    }

    public Veterinario buildObject(ResultSet rs) {
        Veterinario veterinario = null;
        try {
            veterinario = new Veterinario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinario;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Veterinario> veterinarios = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                veterinarios.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinarios;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM veterinarios");
    }
    
    // RetrieveLast
    public Veterinario retrieveLast(){
        List<Veterinario> veterinarios = this.retrieve("SELECT * FROM veterinarios WHERE id = " + lastId("veterinarios","id"));
        return veterinarios.isEmpty() ? null : veterinarios.get(0);
    }

    // RetrieveById
    public Veterinario retrieveById(int id) {
        List<Veterinario> veterinarios = this.retrieve("SELECT * FROM veterinarios WHERE id = " + id);
        return (veterinarios.isEmpty()?null:veterinarios.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM veterinarios WHERE nome LIKE '%" + nome + "%'");
    }    
        
    // Updade
    public void update(Veterinario veterinarios) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE veterinarios SET nome=?, endereco=?, telefone=? WHERE id=?");
            stmt.setString(1, veterinarios.getNome());
            stmt.setString(2, veterinarios.getEndereco());
            stmt.setString(3, veterinarios.getTelefone());
            stmt.setInt(4, veterinarios.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Veterinario veterinarios) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM veterinarios WHERE id = ?");
            stmt.setInt(1, veterinarios.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
