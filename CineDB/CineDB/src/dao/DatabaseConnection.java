package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/CineDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection = null;
    
    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Error: Driver JDBC no encontrado"+ e.getMessage(), "Error de Driver", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static Connection getConnection()throws SQLException{
        if(connection == null || connection.isClosed()){
            try{
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }catch(SQLException e){
                throw new SQLException("No se pudo conectar a la base de datos. URL: "+URL+", Usuario: "+USER+". Error: "+e.getMessage());
            }
        }
        return connection;
    }
    
    public static void closeConnection(){
        if(connection != null){
            try{
                connection.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error en el cierre de la conexion" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }finally{
                connection = null;
            }
        }
    }
    public static boolean testConnection(){
        try(Connection conn = getConnection()){
            return conn != null && !conn.isClosed();
        }catch(SQLException e){
            return false;
        }
    }
}
