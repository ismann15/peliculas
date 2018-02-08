/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import hibernateConfiguration.HibernateUtil;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Administrador;
import model.Director;
import model.Genero;
import model.Pelicula;
import model.hibernate.Administradores;
import model.hibernate.Directores;
import model.hibernate.Generos;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Gigabyte
 */
public class DBManagerHibernate implements Logica {

    private static final Logger logger = Logger.getLogger(DBManagerHibernate.class.getName());
    Session session = HibernateUtil.getSessionFactory().openSession();

    @Override
    public boolean comprobarNombreUsu(String usu) {
        Boolean ok = false;
        Administradores admin = null;
        //sentencia hql
        String hql = "from Administradores where nombreUsuario =: nombreUsuario";
        try {
            //se inserta en un objeto query la sentencia hql
            Query query = session.createQuery(hql);
            //se le da valor al parametro
            query.setParameter("nombreUsuario", usu);
            //se almacena el resultado en un objeto Administradores
            admin = (Administradores) query.uniqueResult();
        } catch (Exception e) {
            logger.severe("Error on comprobarNombreUsu:\n" + e.getMessage());
        } finally {
            session.close();
        }
        //si el objeto Administradores no es nulo, existe el nombre de usuario
        ok = admin != null ? true : false;
        return ok;
    }

    @Override
    public boolean comprobarPass(String nombreU, String pass) {
        Administradores admin = null;
        Boolean ok = false;
        //sentencia hql
        String hql = "from Administradores where nombreUsuario =: nombreUsuario";
        try {
            //se inserta en un objeto query la sentencia hql
            Query query = session.createQuery(hql);
            //se le da valor al parametro
            query.setParameter("nombreUsuario", nombreU);
            //se almacena el resultado en un objeto Administradores
            admin = (Administradores) query.uniqueResult();
            //si las contraseñas coinciden ok es true y false si no coinciden
            ok = (admin.getPass().equals(pass)) ? true : false;
        } catch (Exception e) {
            logger.severe("Error on comprobarPass:\n" + e.getMessage());
        } finally {
            session.close();
        }
        return ok;
    }

    @Override
    public boolean directorExiste(String nombre, String apell) {
        Directores dir = null;
        Boolean ok = false;
        //sentencia hql
        String hql = "from Directores where nombre =: nombre and apellidos =: apellidos";
        try {
            //se inserta en un objeto query la sentencia hql
            Query query = session.createQuery(hql);
            //se le da valor a los parametros
            query.setParameter("nombreUsuario", nombre);
            query.setParameter("apellidos", apell);
            //se almacena el resultado en un objeto Directores
            dir = (Directores) query.uniqueResult();
            //di el objeto no es nulo, existe el director
            ok = (dir != null) ? true : false;
        } catch (Exception e) {
            logger.severe("Error on directorExiste:\n" + e.getMessage());
        } finally {
            session.close();
        }
        return ok;
    }

    @Override
    public int obtenerIdDirectorMax() {
        Directores dir = null;
        Boolean ok = false;
        //sentencia hql
        String hql = "select d from Directores d where d.id= (select max(dd.id) from Directores dd)";
        try {
            //se inserta en un objeto query la sentencia hql
            Query query = session.createQuery(hql);
            //se almacena el resultado en un objeto Directores
            dir = (Directores) query.uniqueResult();
        } catch (Exception e) {
            logger.severe("Error on obtenerIdDirectorMax:\n" + e.getMessage());
        } finally {
            session.close();
        }
        return dir.getId();
    }

    @Override
    public void crearDirector(Director dir) {
        //cargamos con los datos el objeto a almacenar
        Directores director = new Directores(dir.getId_Dir(), dir.getNombre(), dir.getApell(), dir.getPais_dir());
        Transaction tx = null;
        try {
            //se abre una transaccion
            tx = session.beginTransaction();
            //se almacena el objeto
            session.save(dir);
            //se hace un commit
            tx.commit();
            logger.info("Director created succesfully");
        } catch (HibernateException e) {
            //si ocurre algun error al almacenar el objeto se retrocede la orden
            if (tx != null) {
                tx.rollback();
            }
            logger.severe("Error inserting a Director " + e.getMessage());
        } finally {
            //se cierra la sesion
            session.close();
        }
    }

    @Override
    public boolean comprobarGeneroExiste(String nombreGen) {
        Generos genero = null;
        Boolean ok = false;
        //sentencia hql
        String hql = "from Generos where descripcion =: descripcion";
        try {
            //se inserta en un objeto query la sentencia hql
            Query query = session.createQuery(hql);
            //se le da valor a al parametro
            query.setParameter("descripcion", nombreGen);
            //se almacena el resultado en un objeto Generos
            genero = (Generos) query.uniqueResult();
            //di el objeto no es nulo, existe el director
            ok = (genero != null) ? true : false;
        } catch (Exception e) {
            logger.severe("Error on comprobarGeneroExiste:\n" + e.getMessage());
        } finally {
            session.close();
        }
        return ok;
    }

    @Override
    public int obtenerIdGeneroMax() {
        Generos genero = null;
        Boolean ok = false;
        //sentencia hql
        String hql = "select g from Generos g where g.id= (select max(gg.id) from Generos gg)";
        try {
            //se inserta en un objeto query la sentencia hql
            Query query = session.createQuery(hql);
            //se almacena el resultado en un objeto Generos
            genero = (Generos) query.uniqueResult();
        } catch (Exception e) {
            logger.severe("Error on obtenerIdGeneroMax:\n" + e.getMessage());
        } finally {
            session.close();
        }
        return genero.getId();
    }

    @Override
    public void crearGenero(Genero gen) {
        //Se cargan los datos a almacenar
        Generos g = new Generos(gen.getId_gen(), gen.getDescrip_gen());
        Transaction tx = null;
        try {
            //se abre una transaccion
            tx = session.beginTransaction();
            //se almacena el objeto
            session.save(g);
            //se hace un commit
            tx.commit();
            logger.info("Director created succesfully");
        } catch (HibernateException e) {
            //si ocurre algun error al almacenar el objeto se retrocede la orden
            if (tx != null) {
                tx.rollback();
            }
            logger.severe("Error inserting a Genero " + e.getMessage());
        } finally {
            //se cierra la sesion
            session.close();
        }
    }

    @Override
    public ArrayList<Genero> cargarGeneros() {
        ArrayList<Genero> generosToReturn = new ArrayList<>();
        String hql = "from Generos";
        try {
            //se recogen todos los generos en una lista
            List<Generos> generos = session.createQuery(hql).list();
            if (generos != null) {
                //se pasan los generos
                for (Generos g : generos) {
                    generosToReturn.add(new Genero(g.getId(), g.getDescripcion()));
                }
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Has ocurred an error on cargargeneros \n" + e.getMessage());
        } finally {
            session.close();
        }
        return generosToReturn;
    }

    @Override
    public ArrayList<Director> cargarDirectores() {
        ArrayList<Director> directorToReturn = new ArrayList<>();
        String hql = "from Directores";
        try {
            //se recogen todos los directores en una lista
            List<Directores> directores = session.createQuery(hql).list();
            if (directores != null) {
                //se pasan los directores
                for (Directores d : directores) {
                    directorToReturn.add(new Director(d.getId(), d.getNombre(), d.getApellidos(), d.getPais()));
                }
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Has ocurred an error on cargarDirectores \n" + e.getMessage());
        } finally {
            session.close();
        }
        return directorToReturn;
    }

    @Override
    public Director buscarDirector(String nombre, String apell) {
        Director director = new Director();
        //Consulta hql
        String hql = "from Directores where nombre =: nombre and apellidos =: apellidos";
        try {
            //se crea la consulta
            Query query= session.createQuery(hql);
            //se insertan los parametros
            query.setParameter("nombre", nombre);
            query.setParameter("apellidos", apell);
            //el resultado se almacena
            Directores dir= (Directores) query.uniqueResult();
            //si el resultado no es null se insertan los datos en un objeto Director
            if(dir!=null){
                director.setId_Dir(dir.getId());
                director.setNombre(dir.getNombre());
                director.setApell(dir.getApellidos());
                director.setPais_dir(dir.getPais());
            }
        } catch (HibernateException e) {
            logger.severe("Has ocurred an error on buscarDirector \n"+e.getMessage());
        } finally {
            session.close();
        }
        return director;
    }
    
    @Override
    public Genero buscaGenero(String nombreGenero) {
        Genero genero= new Genero();
        String hql= "from Generos where descripcion =: descripcion";
        try{
            //se crea la consulta
            Query query= session.createQuery(hql);
            //se insertan los parametros para la consulta
            query.setParameter("descripcion", nombreGenero);
            //se recoje el resultado
            Generos gen= (Generos) query.uniqueResult();
            //si el resultado no es nulo se almacenan los datos en un objeto Genero
            if(gen!=null){
                genero.setId_gen(gen.getId());
                genero.setDescrip_gen(gen.getDescripcion());
            }
        }catch(HibernateException e){
            logger.severe("has occurred an error on buscaGenero\n"+e.getMessage());
        }finally{
            session.close();
        }
        return genero;
    }

    @Override
    public int obtenerIdPeliculaMax() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aniadirPelicula(Pelicula p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Administrador obtenerAdmin(String uName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pelicula> obtenerPS(int i, String busqueda) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarPS(Pelicula pe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean esSerie(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pelicula> getAllSeries() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pelicula> getAllPeliculas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Director getDirectorPelicula(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Genero> getGenerosPelicula(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
