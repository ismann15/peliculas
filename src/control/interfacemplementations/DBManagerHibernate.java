/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.interfacemplementations;

import control.interfacee.Logica;
import util.hibernateConfiguration.HibernateUtil;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Administrador;
import model.Director;
import model.Genero;
import model.Pelicula;
import model.Serie;
import model.hibernate.*;

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
            Query query = session.createQuery(hql);
            //se insertan los parametros
            query.setParameter("nombre", nombre);
            query.setParameter("apellidos", apell);
            //el resultado se almacena
            Directores dir = (Directores) query.uniqueResult();
            //si el resultado no es null se insertan los datos en un objeto Director
            if (dir != null) {
                director.setId_Dir(dir.getId());
                director.setNombre(dir.getNombre());
                director.setApell(dir.getApellidos());
                director.setPais_dir(dir.getPais());
            }
        } catch (HibernateException e) {
            logger.severe("Has ocurred an error on buscarDirector \n" + e.getMessage());
        } finally {
            session.close();
        }
        return director;
    }

    @Override
    public Genero buscaGenero(String nombreGenero) {
        Genero genero = new Genero();
        String hql = "from Generos where descripcion =: descripcion";
        try {
            //se crea la consulta
            Query query = session.createQuery(hql);
            //se insertan los parametros para la consulta
            query.setParameter("descripcion", nombreGenero);
            //se recoje el resultado
            Generos gen = (Generos) query.uniqueResult();
            //si el resultado no es nulo se almacenan los datos en un objeto Genero
            if (gen != null) {
                genero.setId_gen(gen.getId());
                genero.setDescrip_gen(gen.getDescripcion());
            }
        } catch (HibernateException e) {
            logger.severe("has occurred an error on buscaGenero\n" + e.getMessage());
        } finally {
            session.close();
        }
        return genero;
    }

    @Override
    public int obtenerIdPeliculaMax() {
        Peliculas pelicula = null;
        Boolean ok = false;
        //sentencia hql
        String hql = "select p from Peliculas p where p.id= (select max(pp.id) from Peliculas pp)";
        try {
            //se inserta en un objeto query la sentencia hql
            Query query = session.createQuery(hql);
            //se almacena el resultado en un objeto Peliculas
            pelicula = (Peliculas) query.uniqueResult();
        } catch (Exception e) {
            logger.severe("Error on obtenerIdDirectorMax:\n" + e.getMessage());
        } finally {
            session.close();
        }
        return pelicula.getId();
    }

    @Override
    public void aniadirPelicula(Pelicula p) {
        Boolean esSerie = false;
        Transaction tx = null;
        Object objectToInsert=null;
        //si es serie
        //Obtener los datos de generos
        Set<Generos> genmeroses = new HashSet<>(getHasGeneros(p.getGeneros()));
        //Obtener los datos de director
        Directores d = new Directores(p.getDir().getId_Dir(), p.getDir().getNombre(), p.getDir().getApell(), p.getDir().getPais_dir());
        //obtener los datos de pelicula
        Peliculas pelicula = new Peliculas(p.getId_P(), d, p.getTituloP(), p.getDescriP(), p.getFechaP(), p.getDuracionP(), p.getPaisP(), p.getNotaUsu(), p.getNotaPren(), true, genmeroses);
        //si es una serie
        if ((p) instanceof Serie) {
            //obtener los datos exra de serie
            objectToInsert = new Series(p.getId_P(), pelicula, ((Serie) p).getNumCap(),
                    ((Serie) p).getEstado(), ((Serie) p).getFechaFin());
        }else{
            objectToInsert= new Peliculas();
            objectToInsert=pelicula;
        }
        
        try{
            //se inicia una transaccion
            tx=session.beginTransaction();
            //se almacena el objeto a insertar
            session.save(objectToInsert);
            //se guardan los cambios
            tx.commit();
        }catch(HibernateException e){
            if(tx!=null){
                tx.rollback();
            }
            logger.log(Level.SEVERE, "An error has "
                    + "ocurred while inserting {0}: " + e.getMessage(), p.getTituloP());
        }finally{
            session.close();
        }
    }

    /**
     * Metodo para devolver una lista de Generos apartir de una lista de Genero
     *
     * @param gens lista de objetos Genro
     * @return lista de objetos Generos
     */
    private ArrayList<Generos> getHasGeneros(ArrayList<Genero> gens) {
        ArrayList<Generos> resultado = new ArrayList<>();
        for (Genero genero : gens) {
            resultado.add(new Generos(genero.getId_gen(), genero.getDescrip_gen()));
        }
        return resultado;
    }

    @Override
    public Administrador obtenerAdmin(String uName) {
        Administrador admin=null;
        String hql="from Administradores where nombreUsuario =: nombreUsuario";
        try{
            //crear la query
            Query query= session.createQuery(hql);
            //insertar parametros
            query.setParameter("nombreUsuario", uName);
            //recoger resultado
            Administradores resultado= (Administradores)query.uniqueResult();
            //si el resultado no es nulo
            if(resultado!=null){
                admin= new Administrador(resultado.getNombreUsuario(), resultado.getPass());
            }
        }catch(HibernateException e){
            logger.log(Level.SEVERE, "An error has "
                    + "ocurred while obtenerAdmin {0}: " + e.getMessage(), uName);
        }finally{
            session.close();
        }
        return admin;
    }

    @Override
    public ArrayList<Pelicula> obtenerPS(int i, String busqueda) {
        String hql;
        ArrayList<Pelicula> resultado = new ArrayList<>();
        busqueda = busqueda.toLowerCase();
        if (i == 1) {
            //Busca serie
            List<Series> peliculas = null;
            hql = "from Series where lower(peliculas.tituloPelicula)=: tituloPelicula";
            try {
                //se inserta en un objeto query la sentencia hql
                Query query = session.createQuery(hql);
                //Se insertan los parametros
                query.setParameter("tituloPelicula", busqueda);
                //se almacena el resultado en una lista
                peliculas = query.list();

                //añadir al resultado
                for (Series pelicula : peliculas) {
                    Serie s = setDatosSerie(pelicula);
                    resultado.add(s);
                }

            } catch (Exception e) {
                logger.severe("Error on obtenerIdDirectorMax:\n" + e.getMessage());
            } finally {
                session.close();
            }
        } else {
            //Busca pelicula
            List<Peliculas> peliculas = null;
            hql = "from Peliculas where lower(tituloPelicula)=: tituloPelicula";
            try {
                //se inserta en un objeto query la sentencia hql
                Query query = session.createQuery(hql);
                //Se insertan los parametros
                query.setParameter("tituloPelicula", busqueda);
                //se almacena el resultado en una lista
                peliculas = query.list();

                //añadir al resultado
                for (Peliculas pelicula : peliculas) {
                    Pelicula p = setDatosPelicula(pelicula);
                    resultado.add(p);
                }

            } catch (Exception e) {
                logger.severe("Error on obtenerIdDirectorMax:\n" + e.getMessage());
            } finally {
                session.close();
            }
        }

        return resultado;
    }

    /**
     * Metodo para insertar los datos de una pelicula
     *
     * @param pelicula datos de la pelicula
     * @return objeto Pelicula con los datos insertados
     */
    private Pelicula setDatosPelicula(Peliculas pelicula) {
        Pelicula p = new Pelicula();
        p.setId_P(pelicula.getId());
        p.setTituloP(pelicula.getTituloPelicula());
        p.setDescriP(pelicula.getDescripcion());
        p.setFechaP(pelicula.getFechaPublicacion());
        p.setDuracionP(pelicula.getDuracion());
        p.setPaisP(pelicula.getPaisPublicacion());
        p.setNotaUsu(pelicula.getNotaUsuarios());
        p.setNotaPren(pelicula.getNotaPrensa());
        p.setDir(new Director(pelicula.getDirectores().getId(),
                pelicula.getDirectores().getNombre(),
                pelicula.getDirectores().getApellidos(),
                pelicula.getDirectores().getPais()));
        p.setGen(generosPelicula(pelicula.getGeneroses().toArray()));
        return p;
    }

    /**
     * Metodo para insertar los datos de una serie
     *
     * @param serie datos de la serie
     * @return objeto Serie con los datos insertados
     */
    private Serie setDatosSerie(Series serie) {
        Serie s = new Serie();
        s.setEstado(serie.getEstado());
        s.setFechaFin(serie.getFechaFin());
        s.setNumCap(serie.getNumCaps());
        s.setId_P(serie.getPeliculas().getId());
        s.setTituloP(serie.getPeliculas().getTituloPelicula());
        s.setDescriP(serie.getPeliculas().getDescripcion());
        s.setFechaP(serie.getPeliculas().getFechaPublicacion());
        s.setDuracionP(serie.getPeliculas().getDuracion());
        s.setPaisP(serie.getPeliculas().getPaisPublicacion());
        s.setNotaUsu(serie.getPeliculas().getNotaUsuarios());
        s.setNotaPren(serie.getPeliculas().getNotaPrensa());
        s.setDir(new Director(serie.getPeliculas().getDirectores().getId(),
                serie.getPeliculas().getDirectores().getNombre(),
                serie.getPeliculas().getDirectores().getApellidos(),
                serie.getPeliculas().getDirectores().getPais()));
        s.setGen(generosPelicula(serie.getPeliculas().getGeneroses().toArray()));
        return s;
    }

    /**
     * Metodo que añade los generos de una pleicula o serie a una lista
     *
     * @param generos arry de generos
     * @return lista de generos
     */
    private ArrayList<Genero> generosPelicula(Object[] generos) {
        ArrayList<Genero> resultado = new ArrayList<Genero>();
        for (int i = 0; i < generos.length; i++) {
            Generos g = (Generos) generos[i];
            resultado.add(new Genero(g.getId(), g.getDescripcion()));
        }
        return resultado;
    }

    @Override
    public void eliminarPS(Pelicula pe) {
        Boolean esSerie = false;
        Transaction tx = null;
        //objeto a eliminar
        Object toDelete;
        //sentencia hql
        String hql = "from Peliculas where id=: id";
        //si es serie
        if ((pe) instanceof Serie) {
            esSerie = true;
            //cambia la sentencia hql
            hql = "from Series where id=:id";
        }
        try {
            //inicia transaccion
            tx = session.beginTransaction();
            //se ejecuta la query
            Query query = session.createQuery(hql);
            //se asigna el parametro
            query.setParameter("id", pe.getId_P());
            if (!esSerie) {
                toDelete = (Peliculas) query.uniqueResult();
            } else {
                toDelete = (Series) query.uniqueResult();
            }
            //si el objeto devuelto no es nulo, se elimina
            if (toDelete != null) {
                session.delete(toDelete);
                logger.log(Level.INFO, "deleted {0}", pe.getTituloP());
            }
            //se guardan los cambios
            tx.commit();
        } catch (HibernateException e) {
            //si la transaccion no es nula se ejecuta un rollback
            if (tx != null) {
                tx.rollback();
            }
            logger.log(Level.SEVERE, "An error has "
                    + "ocurred while deleting {0}: " + e.getMessage(), pe.getTituloP());
        } finally {
            //se cierra la sesion y la transaccion
            session.close();
        }
    }

    @Override
    public Boolean esSerie(int id) {
        //sentencia hql
        String hql = "from Series where id=: id";
        Series s = null;
        Boolean ok = false;
        try {
            //se crea la query
            Query query = session.createQuery(hql);
            //se asignan los parametros
            query.setParameter("id", id);
            //se inserta el resultado de la query
            s = (Series) query.uniqueResult();
            //si el resultado no es nulo
            if (s != null) {
                ok = true;
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "An error has occured on esSerie: " + e.getMessage());
        } finally {
            session.close();
        }
        return ok;
    }

    @Override
    public ArrayList<Pelicula> getAllSeries() {
        ArrayList<Pelicula> result = new ArrayList<>();
        String hql = "from Series ";

        try {
            //obteniendo todas las series almacenadas
            List<Series> series = session.createQuery(hql).list();
            //recoger los datos de todas las series
            if (series != null) {
                for (Series serie : series) {
                    Serie s = setDatosSerie(serie);
                    result.add(s);
                }
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "An error has ocurred while getting all series: " + e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public ArrayList<Pelicula> getAllPeliculas() {
        ArrayList<Pelicula> result = new ArrayList<>();
        String hql = "from Peliculas ";

        try {
            //obteniendo todas las series almacenadas
            List<Peliculas> peliculas = session.createQuery(hql).list();
            //recoger los datos de todas las peliculas
            if (peliculas != null) {
                for (Peliculas pelicula : peliculas) {
                    Pelicula p = setDatosPelicula(pelicula);
                    result.add(p);
                }
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "An error has ocurred while getting all peliculas: " + e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Director getDirectorPelicula(int id) {
        Director d = null;
        String hql = "from Peliculas where id=: id";
        try {
            //crear la query
            Query query = session.createQuery(hql);
            //insertar los parametros
            query.setParameter("id", id);
            //recoger el resultado
            Peliculas p = (Peliculas) query.uniqueResult();
            //si el resultado no es nulo recoger el director
            if (p != null) {
                d = new Director(p.getDirectores().getId(),
                        p.getDirectores().getNombre(), p.getDirectores().getApellidos(),
                        p.getDirectores().getPais());
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ha ocurrido un error durante getDirectorPelicula: " + e.getMessage());
        } finally {
            session.close();
        }
        return d;
    }

    @Override
    public ArrayList<Genero> getGenerosPelicula(int id) {
        ArrayList<Genero> generos = null;
        String hql = "from Peliculas where id=:id";
        try {
            //crear la query
            Query query = session.createQuery(hql);
            //insertar los parametros
            query.setParameter("id", id);
            //recoger el resultado
            Peliculas p = (Peliculas) query.uniqueResult();
            //si el resultado no es nulo recoger los generos
            if (p != null) {
                generos = generosPelicula(p.getGeneroses().toArray());
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ha ocurrido un error durante getGenerosPelicula: " + e.getMessage());
        } finally {
            session.close();
        }
        return generos;
    }

}
