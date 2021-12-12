package com.topicos.tt001.DBConnection;

/**
 * Implementação da Classe de Conexão ao banco.
 *
 * @author kugel
 */
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {
    public static final String DBURL = "jdbc:sqlite:vet2021.db";
    private static Connection con;
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // Connect to SQLite
    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(DBURL);
                if (con != null) {
                    DatabaseMetaData meta = con.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }
            } catch (SQLException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
        return con;
    }

    protected ResultSet getResultSet(String query) {
        Statement s;
        ResultSet rs = null;
        try {
            s = (Statement) con.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return rs;
    }

    protected int executeUpdate(PreparedStatement queryStatement) throws SQLException {
        int update;
        update = queryStatement.executeUpdate();
        return update;
    }

    protected int lastId(String tableName, String primaryKey) {
        Statement s;
        int lastId = -1;
        try {
            s = (Statement) con.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(" + primaryKey + ") AS id FROM " + tableName);
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return lastId;
    }

    public static void terminar() {
        try {
            (DAO.getConnection()).close();
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Create table SQLite
    protected final boolean createTable() {
        try {
            PreparedStatement stmt;
            
            // Tabela de Clientes
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS clientes( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "endereco VARCHAR, \n"
                    + "cep VARCHAR, \n"
                    + "email VARCHAR, \n"
                    + "telefone VARCHAR); \n");
            executeUpdate(stmt);

            // Tabela de Especies
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS especies( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "nome_especie VARCHAR); \n");
            executeUpdate(stmt);
            
            // Tabela de Animais
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS animais( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "idade INTEGER, \n"
                    + "sexo VARCHAR, \n"
                    + "id_especie INTEGER, \n"
                    + "id_cliente INTEGER, \n"
                    + "FOREIGN KEY(id_especie) REFERENCES especies(id), \n"
                    + "FOREIGN KEY(id_cliente) REFERENCES clientes(id)); ");
            executeUpdate(stmt);
            
             // Tabela de Veterinarios
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS veterinarios( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "endereco VARCHAR, \n"
                    + "telefone VARCHAR);");
            executeUpdate(stmt);
            
            // Tabela de tratamentos:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS tratamentos( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "id_animal INTEGER, \n"
                    + "nome VARCHAR, \n"
                    + "dataInicio TEXT, \n"
                    + "dataFim TEXT, \n"
                    + "terminado INTEGER, \n"
                    + "FOREIGN KEY(id_animal) REFERENCES animais(id)); ");
            executeUpdate(stmt);
            
            // Tabela de consultas:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS consultas( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "data TEXT, \n"
                    + "hora VARCHAR, \n"
                    + "comentarios VARCHAR, \n"
                    + "id_animal INTEGER, \n"
                    + "id_vet INTEGER, \n"
                    + "id_tratamento INTEGER, \n"
                    + "terminado INTEGER,"
                    + "FOREIGN KEY(id_animal) REFERENCES animais(id),"
                    + "FOREIGN KEY(id_vet) REFERENCES veterinarios(id),"
                    + "FOREIGN KEY(id_tratamento) REFERENCES tratamentos(id)); \n");
            executeUpdate(stmt);
            
             // tabela de exames
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS exames( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "id_consulta INTEGER,"
                    + "FOREIGN KEY(id_consulta) REFERENCES consultas(id)); \n");
            executeUpdate(stmt);   
            
            
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
