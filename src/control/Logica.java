/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.Administrador;
import model.Director;
import model.Genero;
import model.Pelicula;
import model.Serie;
import java.util.ArrayList;

/**
 *
 * @author Gigabyte
 */
public interface Logica {
    public boolean comprobarNombreUsu(String usu);
    public boolean comprobarPass(String nombreU, String pass);
    public boolean directorExiste(String nombre, String apell);
    public int obtenerIdDirectorMax();
    public void crearDirector(Director dir);
    public boolean comprobarGeneroExiste(String nombreGen);
    public int obtenerIdGeneroMax();
    public void crearGenero(Genero gen);
    public ArrayList <Genero> cargarGeneros();
    public ArrayList<Director> cargarDirectores();
    public Director buscarDirector(String nombre, String apell);
    public Genero buscaGenero(String genero);
    public int obtenerIdPeliculaMax();
    public void aniadirPelicula(Pelicula p);
    public Administrador obtenerAdmin(String uName);
    public ArrayList<Pelicula> obtenerPS(int i, String busqueda);
    public void eliminarPS(Pelicula pe);
    public Boolean esSerie(int id);
    public ArrayList <Pelicula> getAllSeries();
    public ArrayList <Pelicula> getAllPeliculas();
    public Director getDirectorPelicula(int id);
    public ArrayList<Genero>getGenerosPelicula (int id);
    
}
