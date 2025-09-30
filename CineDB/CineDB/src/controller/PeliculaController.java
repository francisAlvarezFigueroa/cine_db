package controller;

import dao.PeliculaDAO;
import java.util.List;
import model.Pelicula;

public class PeliculaController {
    private final PeliculaDAO dao = new PeliculaDAO();
    public List<Pelicula> listar(){
        return dao.listarPeliculas();
    }
    
    public List<Pelicula> buscarTitulo(String patron){
        if(patron == null) patron = "";
        return patron.isBlank() ? dao.listarPeliculas() : dao.buscarTitulo(patron);
    }
    
    public List<Pelicula> buscarDirector(String director){
        if(director == null || director.isBlank()){
            return dao.listarPeliculas();
        }
        List<Pelicula> peliculas = dao.listarPeliculas();
        return peliculas.stream().filter(p -> p.getDirector().toLowerCase().contains(director.toLowerCase())).toList();
    }
    
    public List<Pelicula> buscarGenero(String genero){
        if(genero == null || genero.isBlank()){
            return dao.listarPeliculas();
        }
        List<Pelicula> peliculas = dao.listarPeliculas();
        return peliculas.stream().filter(p -> p.getGenero().equalsIgnoreCase(genero)).toList();
    }
    
    public List<Pelicula> buscarRangoAnios(int anioInicio, int anioFinal){
        List<Pelicula> peliculas = dao.listarPeliculas();
        return peliculas.stream().filter(p -> p.getAnio() >= anioInicio && p.getAnio()<= anioFinal).toList();
    }
    
    public boolean crear(Pelicula pelicula){
        String error = validar(pelicula);
        if(error != null){
            return false;
        }
        if(existePelicula(pelicula.getTitulo(), pelicula.getAnio(), 0)){
            return false;
        }
        return dao.crearPelicula(pelicula);
    }
    
    public boolean crear(String titulo, String director, int anio, int duracion, String genero){
        Pelicula pelicula = new Pelicula(titulo, director, anio, duracion, genero);
        return crear(pelicula);
    }
    
    public boolean actualizar(Pelicula pelicula){
        if(pelicula.getId() <= 0) return false;
        String error = validar(pelicula);
        if(error != null){
            return false;
        }
        if(existePelicula(pelicula.getTitulo(), pelicula.getAnio(), pelicula.getId())){
            return false;
        }
        return dao.actualizarPelicula(pelicula);
    }
    
    public boolean actualizar(int id, String titulo, String director, int anio, int duracion, String genero){
        Pelicula pelicula = new Pelicula(titulo, director, anio, duracion, genero);
        pelicula.setId(id);
        return actualizar(pelicula);
    }
    
    public boolean eliminar(int id){
        if(id <= 0) return false;
        return dao.eliminarPelicula(id);
    }
    
    private String validar(Pelicula pelicula){
        if(pelicula.getTitulo() == null || pelicula.getTitulo().isBlank()){
            return "El titulo es obligatorio";
        }
        if(pelicula.getDirector() == null || pelicula.getDirector().isBlank()){
            return "El director es obligatorio";
        }
        if(pelicula.getGenero() == null || pelicula.getGenero().isBlank()){
            return "El genero es obligatorio";
        }
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        if(pelicula.getAnio() < 1888 || pelicula.getAnio() > currentYear + 1){
            return "El año debe estar entre 1888 y " + (currentYear + 1);
        }
        if(pelicula.getDuracion() <= 0 || pelicula.getDuracion() > 480){
            return "La duracion debe estar entre 1 y 480 minutos";
        }
        if(pelicula.getTitulo().length() > 150){
            return "El titulo no puede tener mas de 150 caracteres";
        }
        if(pelicula.getDirector().length() > 50){
            return "El nombre del director no puede tener mas de 50 caracteres";
        }
        
        String[] generosPermitidos = {"Comedia", "Drama", "Accion", "Terror", "Romance", "Infantil"};
        boolean generoValido = false;
        for(String generoPermitido : generosPermitidos){
            if(generoPermitido.equalsIgnoreCase(pelicula.getGenero())){
                generoValido = true;
                break;
            }
        }
        if(!generoValido){
            return "El genero debe corresponder a uno de los siguientes: Comedia, drama, accion, terror, romance o infantil";
        }
        return null;
    }
    
    public Pelicula obtenerId(int id){
        List<Pelicula> peliculas = listar();
        for(Pelicula pelicula : peliculas){
            if(pelicula.getId() == id){
                return pelicula;
            }
        }
        return null;
    }
    
    public boolean existePelicula(String titulo, int anio, int idExcluir){
        List<Pelicula> peliculas = listar();
        for(Pelicula pelicula : peliculas){
            if(pelicula.getId() != idExcluir && pelicula.getTitulo().equalsIgnoreCase(titulo) && pelicula.getAnio() == anio){
                return true;
            }
        }
        return false;
    }
    
    public String[] obtenerGeneros(){
        return new String[]{"Comedia", "Drama", "CienciaFiccion", "Terror", "Romance", "Infantil"};
    }
    
    public String[] obtenerEstadis(){
        List<Pelicula> peliculas = listar();
        if(peliculas.isEmpty()){
            return new String[]{"Total peliculas: 0", "No hay peliculas registradas"};
        }
        int total = peliculas.size();
        Pelicula masReciente = peliculas.get(0);
        Pelicula masAntigua = peliculas.get(0);
        int totalDuracion = 0;
        
        for(Pelicula pelicula : peliculas){
            if(pelicula.getAnio() > masReciente.getAnio()){
                masReciente = pelicula;
            }
            if(pelicula.getAnio() < masAntigua.getAnio()){
                masAntigua = pelicula;
            }
            totalDuracion += pelicula.getDuracion();
        }
        int duracionPromedio = totalDuracion/total;
        
        return new String[]{"Total peliculas: "+total, "Pelicula mas reciente: "+masReciente.getTitulo()+" ("+masReciente.getAnio() + ")", "Pelicula mas antigua: "+masAntigua.getTitulo()+ " ("+masAntigua.getAnio()+")", "Duracion promedio: "+duracionPromedio+" minutos"};
    }
    
}
