package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Pelicula;

public class PeliculaDAO {
    //CREATE
    public boolean crearPelicula(Pelicula pelicula){
        String sqsl = "INSERT INTO Cartelera (titulo, director, anio, duracion, genero) VALUES (?,?,?,?,?)";
        try(
            Connection conn = DatabaseConnection.getConnection();PreparedStatement pstmt = conn.prepareStatement(sqsl, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, pelicula.getTitulo());
            pstmt.setString(2, pelicula.getDirector());
            pstmt.setInt(3, pelicula.getAnio());
            pstmt.setInt(4, pelicula.getDuracion());
            pstmt.setString(5, pelicula.getGenero());
            
            int affectedRows = pstmt.executeUpdate();
            
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        pelicula.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al crear pelicula: "+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    //READ
    public List<Pelicula> listarPeliculas(){
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT * FROM Cartelera ORDER BY titulo";
        
        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
                
           while(rs.next()){
               Pelicula pelicula = resultSetToPelicula(rs);
               peliculas.add(pelicula);
           } 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al listar peliculas: "+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return peliculas;
    }
    //UPDATE
    public boolean actualizarPelicula(Pelicula pelicula){
        String sql = "UPDATE Cartelera SET titulo=?, director=?, anio=?, duracion=?, genero=? WHERE id=?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, pelicula.getTitulo());
            pstmt.setString(2, pelicula.getDirector());
            pstmt.setInt(3, pelicula.getAnio());
            pstmt.setInt(4, pelicula.getDuracion());
            pstmt.setString(5, pelicula.getGenero());
            pstmt.setInt(6, pelicula.getId());
            
            return pstmt.executeUpdate() > 0;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la pelicula: "+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    //DELETE
    public boolean eliminarPelicula(int id){
        String sql = "DELETE FROM Cartelera WHERE id=?";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No se pudo eliminar pelicula: "+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    //Buscar por titulo
    public List<Pelicula> buscarTitulo(String titulo){
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT * FROM Cartelera WHERE titulo LIKE ? ORDER BY titulo";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, "%" + titulo + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                Pelicula pelicula = resultSetToPelicula(rs);
                peliculas.add(pelicula);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No se encontro pelicula: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }return peliculas;
    }
    //ResultSet a Pelicula
    private Pelicula resultSetToPelicula(ResultSet rs) throws SQLException{
        Pelicula pelicula = new Pelicula();
        pelicula.setId(rs.getInt("id"));
        pelicula.setTitulo(rs.getString("titulo"));
        pelicula.setDirector(rs.getString("director"));
        pelicula.setAnio(rs.getInt("anio"));
        pelicula.setDuracion(rs.getInt("duracion"));
        pelicula.setGenero(rs.getString("genero"));
        return pelicula;
    }
}
