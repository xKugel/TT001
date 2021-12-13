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
public class ExameDAO extends DAO {
    private static ExameDAO instance;

    private ExameDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ExameDAO getInstance() {
        return (instance==null?(instance = new ExameDAO()):instance);
    }

// CRUD    
    public Exame create(String descricao, int idConsulta ) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO exames (descricao, id_consulta) VALUES (?,?)");
            stmt.setString(1, descricao);
            stmt.setInt(2, idConsulta);
            
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("exames","id"));
    }
    
    // Uma pequena gambiarra, depois explico...
    public boolean isLastEmpty(){
        Exame ultimoExame = this.retrieveById(lastId("exames","id"));
        if (ultimoExame != null) {
            return ultimoExame.getDescricao().isBlank();
        }
        return false;
    }

    public Exame buildObject(ResultSet rs) {
        Exame exame = null;
        try {
            exame = new Exame(rs.getInt("id"), rs.getString("descricao"), rs.getInt("id_consulta"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exame;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Exame> exames = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                exames.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exames;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM exames");
    }
    
    // RetrieveLast
    public Exame retrieveLast(){
        List<Exame> exames = this.retrieve("SELECT * FROM exames WHERE id = " + lastId("exames","id"));
        return exames.isEmpty() ? null : exames.get(0);
    }

    // RetrieveById
    public Exame retrieveById(int id) {
        List<Exame> exames = this.retrieve("SELECT * FROM exames WHERE id = " + id);
        return (exames.isEmpty()?null:exames.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM exames WHERE nome LIKE '%" + nome + "%'");
    }    
    
    public Exame retriveByConsultaId(int idConsulta) {
        List<Exame> exames = this.retrieve("SELECT * FROM exames WHERE id_consulta = " + idConsulta);
        return (exames.isEmpty()?null:exames.get(0));
    }    
        
    // Updade
    public void update(Exame exame) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE exames SET descricao=?, id_consulta=? WHERE id=?");
            stmt.setString(1, exame.getDescricao());
            stmt.setInt(2, exame.getIdConsulta());
            stmt.setInt(3, exame.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Exame exame) {
        if (exame == null) return;
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM exames WHERE id = ?");
            stmt.setInt(1, exame.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}

