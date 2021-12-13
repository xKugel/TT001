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
public class ClienteDAO extends DAO {
    private static ClienteDAO instance;

    private ClienteDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ClienteDAO getInstance() {
        return (instance==null?(instance = new ClienteDAO()):instance);
    }

// CRUD    
    public Cliente create(String nome, String end, String cep, String email, String telefone) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO clientes (nome, endereco, telefone, cep, email) VALUES (?,?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, end);
            stmt.setString(3, telefone);
            stmt.setString(4, cep);
            stmt.setString(5, email);
            
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("clientes","id"));
    }
    
    // Uma pequena gambiarra, depois explico...
    public boolean isLastEmpty(){
        Cliente lastClient = this.retrieveById(lastId("clientes","id"));
        if (lastClient != null) {
            return lastClient.getNome().isBlank();
        }
        return false;
    }

    public Cliente buildObject(ResultSet rs) {
        Cliente cliente = null;
        try {
            cliente = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("endereco"), rs.getString("telefone"), rs.getString("cep"), rs.getString("email"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return cliente;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Cliente> clientes = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                clientes.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return clientes;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM clientes");
    }
    
    // RetrieveLast
    public Cliente retrieveLast(){
        List<Cliente> clientes = this.retrieve("SELECT * FROM clientes WHERE id = " + lastId("clientes","id"));
        return clientes.isEmpty() ? null : clientes.get(0);
    }

    // RetrieveById
    public Cliente retrieveById(int id) {
        List<Cliente> clientes = this.retrieve("SELECT * FROM clientes WHERE id = " + id);
        return (clientes.isEmpty()?null:clientes.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM clientes WHERE nome LIKE '%" + nome + "%'");
    }    
        
    // Updade
    public void update(Cliente cliente) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE clientes SET nome=?, endereco=?, cep=?, email=?, telefone=? WHERE id=?");
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getCep());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefone());
            stmt.setInt(6, cliente.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Cliente cliente) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM clientes WHERE id = ?");
            stmt.setInt(1, cliente.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}

