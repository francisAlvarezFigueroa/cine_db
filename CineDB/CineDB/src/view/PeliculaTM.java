package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Pelicula;

public class PeliculaTM extends AbstractTableModel{
    private final String[] columnNames = {"ID", "Titulo", "Director", "Anio", "Duracion (min)", "Genero"};
    private List<Pelicula> data = new ArrayList<>();
    public void setData(List<Pelicula> nuevaData){
        this.data = nuevaData != null ? nuevaData : new ArrayList<>();
        fireTableDataChanged();
    }
    public Pelicula getPeliculaAt(int rowIndex){
        return(rowIndex >= 0 && rowIndex < data.size()) ? data.get(rowIndex) : null;
    }
    
    public List<Pelicula> getData(){
        return new ArrayList<>(data);
    }
    
    @Override
    public int getRowCount(){
        return data.size();
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return columnNames[columnIndex];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        return switch (columnIndex){
            case 0,3,4 -> Integer.class;
            default -> String.class;
        };        
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        if(rowIndex <0 || rowIndex >= data.size()){
            return "";
        }
        Pelicula pelicula = data.get(rowIndex);
        
        return switch (columnIndex){
            case 0 -> pelicula.getId();
            case 1 -> pelicula.getTitulo();
            case 2 -> pelicula.getDirector();
            case 3 -> pelicula.getAnio();
            case 4 -> pelicula.getDuracion();
            case 5 -> pelicula.getGenero();
            default -> "";
        };
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }
}
