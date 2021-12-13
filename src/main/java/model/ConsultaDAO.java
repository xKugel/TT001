/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
public class ConsultaDAO extends DAO {
    private static ConsultaDAO instance;

    private ConsultaDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ConsultaDAO getInstance() {
        return (instance==null?(instance = new ConsultaDAO()):instance);
    }

// CRUD    
    public Consulta create(Calendar data, int hora, String comentarios, int idAnimal, int idVet, boolean terminado) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO consultas (data, hora, comentarios, id_animal, id_vet, terminado) VALUES (?,?,?,?,?,?)");
            stmt.setDate(1, new Date(data.getTimeInMillis()));
            stmt.setInt(2, hora);
            stmt.setString(3, comentarios);
            stmt.setInt(4, idAnimal);
            stmt.setInt(5, idVet);
            stmt.setInt(6, terminado ? 1 : 0);

            
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("consultas","id"));
    }
    
    // Uma pequena gambiarra, depois explico...
    public boolean isLastEmpty(){
        Consulta lastClient = this.retrieveById(lastId("consultas","id"));
        if (lastClient != null) {
            return lastClient.getComentarios().isBlank();
        }
        return false;
    }

    public Consulta buildObject(ResultSet rs) {
        Consulta consulta = null;
        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(rs.getDate("data"));
            consulta = new Consulta(rs.getInt("id"), dt, rs.getInt("hora"), rs.getString("comentarios"), rs.getInt("id_animal"), rs.getInt("id_vet"), rs.getInt("terminado") > 0 ? true : false);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consulta;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Consulta> consultas = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        if (rs == null) return consultas;
        try {
            while (rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM consultas");
    }
    
    // RetrieveLast
    public Consulta retrieveLast(){
        List<Consulta> consultas = this.retrieve("SELECT * FROM consultas WHERE id = " + lastId("consultas","id"));
        return consultas.isEmpty() ? null : consultas.get(0);
    }

    // RetrieveById
    public Consulta retrieveById(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consultas WHERE id = " + id);
        return (consultas.isEmpty()?null:consultas.get(0));
    }
    
    public Consulta retrieveByAnimalId(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consultas WHERE id_animal = " + id);
        return (consultas.isEmpty()?null:consultas.get(0));
    }
    
    public Consulta retrieveByVetId(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consultas WHERE id_vet = " + id);
        return (consultas.isEmpty()?null:consultas.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM consultas WHERE nome LIKE '%" + nome + "%'");
    }    
        
    // Updade
    public void update(Consulta consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE consultas SET comentarios=?, data=?, hora=?, id_animal=?, id_vet=?, terminado=? WHERE id=?");
            stmt.setString(1, consulta.getComentarios());
            stmt.setDate(2, new Date(consulta.getData().getTimeInMillis()));
            stmt.setInt(3, consulta.getHora());
            stmt.setInt(4, consulta.getIdAnimal());
            stmt.setInt(5, consulta.getIdVet());
            stmt.setInt(6, consulta.isTerminado() ? 1 : 0);
            stmt.setInt(7, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Consulta consulta) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consultas WHERE id = ?");
            stmt.setInt(1, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}

