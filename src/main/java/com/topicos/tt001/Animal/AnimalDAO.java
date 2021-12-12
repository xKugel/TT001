package com.topicos.tt001.Animal;

import com.topicos.tt001.DBConnection.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kugel
 */
public class AnimalDAO extends DAO {
     private static AnimalDAO instance;
    
    public static AnimalDAO getInstance() {
        return instance == null ? (instance = new AnimalDAO()) : instance;
    }
    
    public Animal create (String nome, Character sexo, int idade, Integer id_especie, Integer id_cliente) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO animais (nome, sexo, idade, id_especie, id_cliente) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, nome);
            stmt.setString(2, sexo.toString());
            stmt.setInt(3, idade);
            stmt.setInt(4, id_especie);
            stmt.setInt(5, id_cliente);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("animais","id"));
    }
    
    public boolean isLastEmpty(){
        Animal ultimoAnimal = this.retrieveById(lastId("animais","id"));
        if (ultimoAnimal != null) {
            return ultimoAnimal.getNome().isBlank();
        }
        return false;
    }

    public Animal buildObject(ResultSet rs) {
        Animal especie = null;
        try {
            especie = new Animal(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    rs.getString("sexo").charAt(0),
                    rs.getInt("idade"),
                    rs.getInt("id_especie"),
                    rs.getInt("id_cliente"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return especie;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Animal> animais = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                animais.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return animais;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM animais");
    }
    
    // RetrieveLast
    public Animal retrieveLast(){
        List<Animal> animais = this.retrieve("SELECT * FROM animais WHERE id = " + lastId("animais","id"));
        return animais.isEmpty() ? null : animais.get(0);
    }

    // RetrieveById
    public Animal retrieveById(int id) {
        List<Animal> animais = this.retrieve("SELECT * FROM animais WHERE id = " + id);
        return (animais.isEmpty()?null:animais.get(0));
    }
    
    // RetrieveById
    public List<Animal> retrieveByClienteId(int id) {
        List<Animal> animais = this.retrieve("SELECT * FROM animais WHERE id_cliente=  " + id);
        return (animais.isEmpty()?null:animais);
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM animais WHERE nome LIKE '%" + nome + "%'");
    }

        
    // Updade
    public void update(Animal animal) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE animais SET nome=?, sexo=?, idade=?, id_especie=?, id_cliente=? WHERE id=?");
            stmt.setString(1, animal.getNome());
            stmt.setString(2, String.valueOf(animal.getSexo()));
            stmt.setInt(3, animal.getIdade());
            stmt.setInt(4, animal.getIdEspecie());            
            stmt.setInt(5, animal.getIdCliente());            

            stmt.setInt(6, animal.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Animal animais) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM animais WHERE id = ?");
            stmt.setInt(1, animais.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
