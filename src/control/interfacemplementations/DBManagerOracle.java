/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.interfacemplementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import control.interfacee.Logica;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Administrador;
import model.Director;
import model.Genero;
import model.Pelicula;
import model.Serie;

/**
 *
 * @author Gigabyte
 */
public class DBManagerOracle implements Logica {

    private static final Logger logger = Logger.getLogger(DBManagerHibernate.class.getName());
    private Connection con;

    /**
     * Metodo para abrir una conexion con la BD
     */
    public void openConection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "isma", "isma");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "has ocurred an error oppening the connection : " + e.getMessage());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has ocurred an error oppening the connection : " + e.getMessage());
        }
    }

    /**
     * Metodo para cerrar la conexion con la BD
     */
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has ocurred an error clossing the connection : " + e.getMessage());
        }
    }

    @Override
    public boolean comprobarNombreUsu(String usu) {
        Boolean ok = false;
        String sql = "SELECT * FROM TABLEADMINISTRADOR WHERE adminuser='" + usu + "'";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (!rs.next()) {
                ok = true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has ocurred an error on comprobarNombreUSu : " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return ok;
    }

    @Override
    public boolean comprobarPass(String nombreU, String pass) {
        Boolean ok = false;
        String sql = "SELECT * FROM TABLEADMINISTRADOR WHERE adminuser=`" + nombreU + "' and adminpassword ='" + pass + "'";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (!rs.next()) {
                ok = true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has ocurred an error on comprobarPass : " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return ok;
    }

    @Override
    public boolean directorExiste(String nombre, String apell) {
        Boolean ok = false;
        nombre = nombre.toLowerCase();
        apell = apell.toLowerCase();
        String sql = "SELECT nombre, apellidos FROM TABLEDIRECTOR WHERE lower(nombre)='" + nombre + "' and lower(apellidos)='" + apell + "'";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (!rs.next()) {
                ok = true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on directorExiste : " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return ok;
    }

    @Override
    public int obtenerIdDirectorMax() {
        Integer id = 0;
        String sql = "SELECT id_D+1 AS id FROM TABLEDIRECTOR WHERE id_D=(SELECT MAX(id_D) FROM TABLEDIRECTOR)";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (rs != null) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on obtenerIdDirectorMax: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return id;
    }

    @Override
    public void crearDirector(Director dir) {
        String sql = "INSERT INTO TABLEDIRECTOR VALUES(" + dir.getId_Dir() + ",'" + dir.getNombre() + "','" + dir.getApell() + "','" + dir.getPais_dir() + "')";
        this.openConection();
        try {
            PreparedStatement insert = con.prepareStatement(sql);
            insert.executeUpdate();
            con.commit();
            logger.log(Level.INFO, "the diirector with id {0} has been inserted succesfully", dir.getId_Dir());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error insering the director with id {0}: " + e.getMessage(), dir.getId_Dir());
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public boolean comprobarGeneroExiste(String nombreGen) {
        Boolean ok = false;
        nombreGen = nombreGen.toLowerCase();
        String sql = "SELECT descripcion FROM TABLEGENERO WHERE lower(descipcion)='" + nombreGen + "'";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (!rs.next()) {
                ok = true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on comprobarGeneroExiste : " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return ok;
    }

    @Override
    public int obtenerIdGeneroMax() {
        Integer id = 0;
        String sql = "SELECT id_G+1 AS id FROM TABLEGENERO WHERE id_G=(SELECT MAX(id_G) FROM TABLEGENERO)";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (rs != null) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on obtenerIdGeneroMax: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return id;
    }

    @Override
    public void crearGenero(Genero gen) {
        String sql = "INSERT INTO TABLEGENERO VALUES(" + gen.getId_gen() + ",'" + gen.getDescrip_gen() + "')";
        this.openConection();
        try {
            PreparedStatement insert = con.prepareStatement(sql);
            insert.executeUpdate();
            con.commit();
            logger.log(Level.INFO, "the genere with id {0} has been inserted succesfully", gen.getId_gen());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error insering the genere with id {0}: " + e.getMessage(), gen.getId_gen());
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public ArrayList<Genero> cargarGeneros() {
        ArrayList<Genero> generos = null;
        String sql = "SELECT id_G AS id, descripccion AS nombre FROM TABLEGENERO";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            generos = new ArrayList<>();
            while (rs.next()) {
                generos.add(new Genero(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occured an error on cargarGeneros: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return generos;
    }

    @Override
    public ArrayList<Director> cargarDirectores() {
        ArrayList<Director> directores = null;
        String sql = "SELECT id_D AS id, nombre, apellidos,pais FROM TABLEDIRECTOR ";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            directores = new ArrayList<>();
            while (rs.next()) {
                directores.add(new Director(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("pais")));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occured an error on cargarDirectores: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return directores;
    }

    @Override
    public Director buscarDirector(String nombre, String apell) {
        Director d = null;
        nombre = nombre.toLowerCase();
        apell = apell.toLowerCase();
        String sql = "SELECT * FROM TABLEDIRECTOR WHERE lower(nombre)='" + nombre + "' and lower(apellidos)='" + apell + "'";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (rs != null) {
                d = new Director(rs.getInt("id_D"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("pais"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occured an error on burcarDirector: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return d;
    }

    @Override
    public Genero buscaGenero(String genero) {
        Genero g = null;
        genero = genero.toLowerCase();
        String sql = "SELECT * FROM TABLEGENEROS WHERE lower(descripccion)='" + genero + "'";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (rs != null) {
                g = new Genero(rs.getInt("id_G"), rs.getString("descripccion"));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "has occurred an error on buscaGenero: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return g;
    }

    @Override
    public int obtenerIdPeliculaMax() {
        Integer id = 0;
        String sql = "SELECT id_P+1 AS id FROM TABLEPELICULA WHERE id_P=(SELECT MAX(id_P) FROM TABLEPELICULA)";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (rs != null) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on obtenerIdPeliculaMax: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return id;
    }

    @Override
    public int obtenerIdSerieMax() {
        Integer id = 0;
        String sql = "SELECT id_P+1 AS id FROM TABLESERIE WHERE id_P=(SELECT MAX(id_P) FROM TABLESERIE)";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (rs != null) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on obtenerIdPeliculaMax: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return id;
    }

    @Override
    public void aniadirPelicula(Pelicula p) {
        //Formateador de fecha
        SimpleDateFormat dma = new SimpleDateFormat("dd/MM/yyyy");
        //Director de la pelicula
        Director dire = p.getDir();
        //fecha de publicacion de la pelicula en String con formato
        String fechaPublicacion = dma.format(p.getFechaP());
        //Sentencia sql de insert
        String sql = sql = "INSERT INTO TABLEPELICULA VALUES (" + p.getId_P() + ",'" + p.getTituloP() + "','" + p.getPaisP() + "',TO_DATE('" + fechaPublicacion + "','DD/MM/YYYY')," + p.getDuracionP() + ""
                + "," + p.getNotaUsu() + "," + p.getNotaPren() + ",director(" + dire.getId_Dir() + ",'" + dire.getNombre() + "','" + dire.getApell() + "','" + dire.getPais_dir() + "listgeneros(";
        //añadimos a la sentencia sql los generos
        for (int i = 0; i < p.getGeneros().size(); i++) {
            sql = sql + "genero(" + p.getGeneros().get(i).getId_gen() + ",'" + p.getGeneros().get(i).getDescrip_gen() + "'),";
        }
        //se hace un substring, para eliminar la ultima coma que sobra al salir del for y se le añaden los parentesis de cierre
        sql = sql.substring(0, sql.length() - 1) + "))";
        //si se intenta insertar una serie
        if ((p) instanceof Serie) {
            //se añaden los datos extra
            String fechaFin = dma.format(((Serie) p).getFechaFin());
            sql = sql.substring(0, sql.length() - 1) + "," + ((Serie) p).getNumCap() + ",'" + ((Serie) p).getEstado() + "',TO_DATE('" + fechaFin + "','DD/MM/YYYY'))";
            //se cambia el nombre de la tabla donde se va a insertar
            sql = sql.replace("TABLEPELICULA", "TABLESERIE");
        }
        this.openConection();
        try {
            PreparedStatement insert = con.prepareStatement(sql);
            insert.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Has occurred an error on aniadirPelicula: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Has occurred an error on aniadirPelicula: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public Administrador obtenerAdmin(String uName) {
        Administrador admin = null;
        String sql = "SELECT * FROM TABLEADMINISTRADOR WHERE adminuser='" + uName + "'";
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (rs != null) {
                admin = new Administrador(rs.getString("adminuser"), rs.getString("adminpassword"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on obtenerAdmin: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "has occurred an error on obtenerAdmin: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return admin;
    }

    @Override
    public ArrayList<Pelicula> obtenerPS(int i, String busqueda) {
        ArrayList<Pelicula> resultado = null;
        String sql = null;
        busqueda = busqueda.toLowerCase();
        if (i == 1) {
            sql = "SELECT * FROM TABLESERIE WHERE LOWER(titulo)='" + busqueda + "'";
        } else {
            sql = "SELECT * FROM TABLEPELICULA WHERE LOWER(titulo)='" + busqueda + "'";
        }
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            resultado = new ArrayList<>();
            if (i == 1) {
                while (rs.next()) {
                    Serie s = new Serie();
                    s.setId_P(rs.getInt("id_p"));
                    s.setTituloP(rs.getString("titulo"));
                    s.setPaisP(rs.getString("pais"));
                    s.setFechaP(rs.getDate("fechap"));
                    s.setDuracionP(rs.getInt("duracion"));
                    s.setDescriP(rs.getString("descripcion"));
                    s.setNotaUsu(rs.getFloat("notausuarios"));
                    s.setNotaPren(rs.getFloat("notaprensa"));
                    s.setDir(getDirectorPelicula(s.getId_P(), 0));
                    s.setGeneros(getGenerosPelicula(s.getId_P(), 0));
                    s.setEstado(rs.getString("estado"));
                    s.setFechaFin(rs.getDate("fechafin"));
                    s.setNumCap(rs.getInt("numcapitulos"));
                    resultado.add(s);
                }
            } else {
                while (rs.next()) {
                    Pelicula p = new Pelicula();
                    p.setId_P(rs.getInt("id_p"));
                    p.setTituloP(rs.getString("titulo"));
                    p.setPaisP(rs.getString("pais"));
                    p.setFechaP(rs.getDate("fechap"));
                    p.setDuracionP(rs.getInt("duracion"));
                    p.setDescriP(rs.getString("descripcion"));
                    p.setNotaUsu(rs.getFloat("notausuarios"));
                    p.setNotaPren(rs.getFloat("notaprensa"));
                    p.setDir(getDirectorPelicula(p.getId_P(), 1));
                    p.setGeneros(getGenerosPelicula(p.getId_P(), 1));
                    resultado.add(p);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on obtenerPS: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "has occurred an error on obtenerPS: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return resultado;
    }

    @Override
    public void eliminarPS(Pelicula pe) {
        String sql;
        if ((pe) instanceof Serie) {
            sql = " DELETE FROM  TABLESERIE WHERE id_P= " + pe.getId_P();
        } else {
            sql = " DELETE FROM  TABLEPELICULA WHERE id_P= " + pe.getId_P();
        }
        this.openConection();
        try {
            PreparedStatement delete = con.prepareStatement(sql);
            delete.executeUpdate();
            con.commit();
            logger.log(Level.INFO, " PS with id {0} deleted correctly", pe.getId_P());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on eliminarPS: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "has occurred an error on eliminarPS: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public Boolean esSerie(int id) {
        Boolean ok = false;
        String sql = "SELECT * FROM TABLESERIE WHERE id_P = " + id;
        this.openConection();
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (rs != null) {
                ok = true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on esSerie: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "has occurred an error on esSerie: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return ok;
    }

    @Override
    public ArrayList<Pelicula> getAllSeries() {
        ArrayList<Pelicula> resultado = null;
        String sql = null;
        sql = "SELECT * FROM TABLESERIE ";
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            resultado = new ArrayList<>();
            while (rs.next()) {
                Serie s = new Serie();
                s.setId_P(rs.getInt("id_p"));
                s.setTituloP(rs.getString("titulo"));
                s.setPaisP(rs.getString("pais"));
                s.setFechaP(rs.getDate("fechap"));
                s.setDuracionP(rs.getInt("duracion"));
                s.setDescriP(rs.getString("descripcion"));
                s.setNotaUsu(rs.getFloat("notausuarios"));
                s.setNotaPren(rs.getFloat("notaprensa"));
                s.setDir(getDirectorPelicula(s.getId_P(), 0));
                s.setGeneros(getGenerosPelicula(s.getId_P(), 0));
                s.setEstado(rs.getString("estado"));
                s.setFechaFin(rs.getDate("fechafin"));
                s.setNumCap(rs.getInt("numcapitulos"));
                resultado.add(s);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on getAllSeries: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "has occurred an error on getAllSeries: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return resultado;
    }

    @Override
    public ArrayList<Pelicula> getAllPeliculas() {
        ArrayList<Pelicula> resultado = null;
        String sql = null;
        sql = "SELECT * FROM TABLEPELICULA ";
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            resultado = new ArrayList<>();
            while (rs.next()) {
                Pelicula p = new Pelicula();
                p.setId_P(rs.getInt("id_p"));
                p.setTituloP(rs.getString("titulo"));
                p.setPaisP(rs.getString("pais"));
                p.setFechaP(rs.getDate("fechap"));
                p.setDuracionP(rs.getInt("duracion"));
                p.setDescriP(rs.getString("descripcion"));
                p.setNotaUsu(rs.getFloat("notausuarios"));
                p.setNotaPren(rs.getFloat("notaprensa"));
                p.setDir(getDirectorPelicula(p.getId_P(), 1));
                p.setGeneros(getGenerosPelicula(p.getId_P(), 1));
                resultado.add(p);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on getAllPeliculas: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "has occurred an error on getAllPeliculas: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
        return resultado;
    }

    @Override
    public Director getDirectorPelicula(int id, int i) {
        Director d = null;
        String sql;
        if (i == 1) {
            sql = "select director.* from tablepelicula, table(dire) director where id_P=" + id;
        } else {
            sql = "select director.* from tableserie, table(dire) director where id_P=" + id;
        }
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            if (rs != null) {
                d = new Director(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on getAllPeliculas: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "has occurred an error on getAllPeliculas: " + e.getMessage());
        }
        return d;
    }

    @Override
    public ArrayList<Genero> getGenerosPelicula(int id, int i) {
        ArrayList<Genero> generos = null;
        String sql;
        if (i == 1) {
            sql = "select genero.* from tablepelicula, table(generos) genero where id_P=" + id;
        } else {
            sql = "select genero.* from tableserie, table(generos) genero where id_P=" + id;
        }
        try {
            PreparedStatement select = con.prepareStatement(sql);
            ResultSet rs = select.executeQuery();
            generos = new ArrayList<>();
            if (rs != null) {
                generos.add(new Genero(rs.getInt(0), rs.getString(1)));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "has occurred an error on getAllPeliculas: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "has occurred an error on getAllPeliculas: " + e.getMessage());
        }
        return generos;
    }

}
