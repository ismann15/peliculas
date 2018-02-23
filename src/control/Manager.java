package control;

import control.interfacemplementations.DBManagerOracle;
import control.interfacemplementations.DBManagerHibernate;
import control.interfacemplementations.DBManagerMongoDB;
import control.interfacemplementations.DBManagerMySQL;
import control.interfacee.Logica;
import java.util.ArrayList;

import model.Administrador;
import model.Director;
import model.Genero;
import model.Pelicula;
import model.Serie;

public class Manager {

    Logica dbman = null;
    
    public Manager(int i){
        switch(i){
            case 1: dbman= new DBManagerMySQL();
                break;
            case 2: dbman= new DBManagerMongoDB();
                break;
            case 3: dbman= new DBManagerHibernate();
                break;
            case 4: dbman= new DBManagerOracle();
                break;
        }
    }

    //Metodo para comprobar si un usuario existe en la BD
    public boolean comprobarNombreUsu(String usu) {
        boolean ok = false;
        ok = dbman.comprobarNombreUsu(usu);
        return ok;
    }

    public boolean comprobarPass(String nombreU, String pass) {
        boolean ok = false;
        ok = dbman.comprobarPass(nombreU, pass);
        return ok;
    }

    public boolean directorExiste(String nombre, String apell) {
        boolean ok = dbman.directorExiste(nombre, apell);
        return ok;
    }

    public int obtenerIdDirectorMax() {
        int i = dbman.obtenerIdDirectorMax();
        return i;
    }

    public void crearDirector(Director dir) {
        dbman.crearDirector(dir);
    }

    public boolean comprobarGeneroExiste(String nombreGen) {
        boolean ok = dbman.comprobarGeneroExiste(nombreGen);
        return ok;
    }

    public int obtenerIdGeneroMax() {
        int i = dbman.obtenerIdGeneroMax();
        return i;
    }

    public void crearGenero(Genero gen) {
        dbman.crearGenero(gen);

    }

    public ArrayList <Genero> cargarGeneros() {
        return dbman.cargarGeneros();
    }

    public ArrayList<Director> cargarDirectores() {
        return  dbman.cargarDirectores();
    }

    public Director buscarDirector(String nombre, String apell) {
        Director d = dbman.buscarDirector(nombre, apell);
        return d;
    }

    public Genero buscaGenero(String genero) {
        Genero g = dbman.buscaGenero(genero);
        return g;
    }

    public int obtenerIdPeliculaMax() {
        int id = dbman.obtenerIdPeliculaMax();
        return id;
    }

    public void aniadirPelicula(Pelicula p) {
        dbman.aniadirPelicula(p);

    }

    public void aniadirSerie(Serie p) {
        dbman.aniadirPelicula(p);

    }

    public Administrador obtenerAdmin(String uName) {
        Administrador a = dbman.obtenerAdmin(uName);
        return a;
    }

    public ArrayList<Pelicula> obtenerPS(int i, String busqueda) {
        return dbman.obtenerPS(i,busqueda);
    }

    public void eliminarPS(Pelicula pe) {
        dbman.eliminarPS(pe);

    }

    /**
     * Metodo para ver si es una serie, apartir de un identificador
     * @param id identificador 
     * @return true si es serie, false si es pelicula
     */
    public Boolean esSerie(int id) {
        return dbman.esSerie(id);
    }

    public ArrayList <Pelicula> getAllSeries() {
        return dbman.getAllSeries();
    }

    public ArrayList <Pelicula> getAllPeliculas() {
        return dbman.getAllPeliculas();
    }
    /**
     * Metodo que devuelve el director de una pelicula
     * @param id_P identificador de la pelicula
     * @return director de la pelicula 
     */
    public Director getDirectorPelicula(int id) {
        return dbman.getDirectorPelicula(id,0);
    }
    /**
     * Metodo que devuelve los generos de una pelicula
     * @param id_P identificador de la pelicula
     * @return generos de la pelicula 
     */
    public ArrayList<Genero>getGenerosPelicula (int id){
        return dbman.getGenerosPelicula(id,0);
    }

    public void modificarP(Pelicula pel) {
        dbman.eliminarPS(pel);
        dbman.aniadirPelicula(pel);
    }

    public int obtenerIdSerieMax() {
        return dbman.obtenerIdSerieMax();
    }

}
