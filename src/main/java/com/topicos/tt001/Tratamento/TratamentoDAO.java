package com.topicos.tt001.Tratamento;

import com.topicos.tt001.DBConnection.DAO;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kugel
 */
public class TratamentoDAO extends DAO {
    private static TratamentoDAO instance;

    private TratamentoDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static TratamentoDAO getInstance() {
        return (instance==null?(instance = new TratamentoDAO()):instance);
    }

// CRUD    
    public Tratamento create(String nome, String dataInicio, String dataFim , int idAnimal, int terminou) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO tratamentos (nome, dataInicio, dataFim, idAnimal, terminou) VALUES (?,?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, dataInicio);
            stmt.setString(3, dataFim);
            stmt.setInt(4, idAnimal);
            stmt.setInt(5, terminou);
            
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(TratamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("tratamentos","id"));
    }
    
    // Uma pequena gambiarra, depois explico...
    public boolean isLastEmpty(){
        Tratamento ultimoTratamento = this.retrieveById(lastId("tratamentos","id"));
        if (ultimoTratamento != null) {
            return ultimoTratamento.getNome().isBlank();
        }
        return false;
    }

    public Tratamento buildObject(ResultSet rs) {
        Tratamento tratamento = null;
        try {
            tratamento = new Tratamento(rs.getInt("id"), rs.getString("nome"), rs.getString("dataInicio"), rs.getString("dataFim"), rs.getInt("idAnimal"), rs.getInt("terminou") > 0 ? true : false);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamento;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Tratamento> tratamentos = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                tratamentos.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamentos;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM tratamentos");
    }
    
    // RetrieveLast
    public Tratamento retrieveLast(){
        List<Tratamento> tratamentos = this.retrieve("SELECT * FROM tratamentos WHERE id = " + lastId("tratamentos","id"));
        return tratamentos.isEmpty() ? null : tratamentos.get(0);
    }

    // RetrieveById
    public Tratamento retrieveById(int id) {
        List<Tratamento> tratamentos = this.retrieve("SELECT * FROM tratamentos WHERE id = " + id);
        return (tratamentos.isEmpty()?null:tratamentos.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM tratamentos WHERE nome LIKE '%" + nome + "%'");
    }    
        
    // Updade
    public void update(Tratamento tratamento) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE tratamentos SET nome=?, dataFim=?, dataInicio=?, idAnimal=?, terminou=? WHERE id=?");
            stmt.setString(1, tratamento.getNome());
            stmt.setString(2, tratamento.getDataFim());
            stmt.setString(3, tratamento.getDataInicio());
            stmt.setInt(4, tratamento.getIdAnimal());
            stmt.setInt(5, tratamento.isTerminou() ? 1 : 0);
            stmt.setInt(6, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Tratamento cliente) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamentos WHERE id = ?");
            stmt.setInt(1, cliente.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}

