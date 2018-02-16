package control.interfacemplementations;

import control.interfacee.Logica;
import java.util.ArrayList;

/*import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;*/
import model.Administrador;
import model.Director;
import model.Genero;
import model.Pelicula;
import model.Serie;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManagerMySQL implements Logica{

    //CONEXION CON MONGO DB
    /*MongoClient cliente= new MongoClient("localhost",27017);
	MongoDatabase db= cliente.getDatabase("videoclubOnline");
	MongoCollection <Document> collection= db.getCollection("Directores");*/
    //CONEXION CON MYSQL
    private Connection con;
    private Statement stmt;

    private void openConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost/videoclub";
        con = DriverManager.getConnection(url, " ", " ");
        stmt = con.createStatement();
    }

    private void closeConnection() throws SQLException {
        stmt.close();
        con.close();
    }
    /**
     * Metodo que convierte una fecha java.util.Date a java.sql.Date
     * @param uDate fecha a convertir
     * @return fecha convertida a java.sql.Date
     */
    public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        return new java.sql.Date(uDate.getTime());
    }
    /**
     * Metodo que comvierte una fecha java.sql.Date a java.util.Date
     * @param sDate fecha a convertir
     * @return fecha convertida a java.util.Date
     */
    public static java.util.Date convertSlqToUtil(java.sql.Date sDate) {
        return new java.util.Date(sDate.getTime());
    }

    
    /**
     * Metodo que comprueba si el nombre de usuario existe en la BD
     * @param usu nombre de usuario a comprobar
     * @return true si existe, false si no existe
     */
    public boolean comprobarNombreUsu(String usu) {
        System.out.println("usuario correcto");
        ResultSet rs = null;
        boolean ok = false;
        try {
            this.openConnection();
            String select = "SELECT COUNT(nombreUsuario)numUsuarios FROM administradores"
                    + "WHERE nombreUsuario like '"+usu+"'";
            rs = stmt.executeQuery(select);
            if (rs.next()) {
                //Si la select devuelve un numero mayor a 0 es que el usuario existe
                if (rs.getInt(1) > 0) {
                    ok = true;
                }
            }
            rs.close();
            this.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        //ok sera true si existe, y false si no existe
        return ok;
    }
    /**
     * Metodo para comprobar un nombre de usuario con una contraseña
     * @param nombreU nombre de usuario 
     * @param pass contraseña a validar
     * @return true si es correcta, false si es incorrecta
     */
    public boolean comprobarPass(String nombreU, String pass) {
        boolean ok = false;
        ResultSet rs=null;
        try {
            this.openConnection();
            String select="SELECT COUNT(nombreUsuario) FROM administradores "
                    + "WHERE nombreUsuario like '"+nombreU+"' and pass like '"+pass+"'";
            rs=stmt.executeQuery(select);
             if (rs.next()) {
                //Si la select devuelve un numero mayor a 0 es que el usuario y la contraseña coinciden
                if (rs.getInt(1) > 0) {
                    ok = true;
                }
            }
            rs.close();
            this.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        //ok sera false si no coinciden y true si lo hacen
        return ok;
    }
   /**
     * Metodo para comprobar si el director que se va a insertar existe en la BD
     * @param nom nombre de director
     * @param ap apellido de director
     * @return true si existe, false si no existe
     */
    public boolean directorExiste(String nom, String ap) {
        String nombre= nom.toLowerCase();
        String apellido= ap.toLowerCase();
        boolean ok = false;
        ResultSet rs=null;
        try{
            this.openConnection();
            String select="SELECT COUNT(id) FROM directores"
                    +" WHERE LOWER(nombre) like '"+nombre+"' AND LOWER(apellidos) like '"+apellido+"'";
            rs=stmt.executeQuery(select);
            if(rs.next()){
                //Si la select devuelve un numero mayor a 0 es que el director existe
                if (rs.getInt(1) > 0) {
                    ok = true;
                }
            }
            rs.close();
            this.closeConnection();
        }catch (Exception ex){
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ok;
    }
    /**
     * Metodo que devuelve el id a usar para un nuevo director
     * @return id maxima+1
     */
    public int obtenerIdDirectorMax() {
        int i = 0;
        ResultSet rs=null;
        try{
            this.openConnection();
            String select="SELECT MAX(id)+1 FROM directores";
            rs=stmt.executeQuery(select);
            if(rs.next()){
                //Obtenemos el id maximo
                i=rs.getInt(1);
            }
            rs.close();
            this.closeConnection();
        }catch (Exception e){
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return i;
    }
    /**
     * Metodo que almacena un director en la BD
     * @param dir director a almacenar
     */
    public void crearDirector(Director dir) {
        try{
            this.openConnection();
            String insert="INSERT INTO directores VALUES("+dir.getId_Dir()+",'"+dir.getNombre()+"','"+dir.getApell()+"','"+dir.getPais_dir()+"')";
            stmt.executeUpdate(insert);
            this.closeConnection();
        }catch(Exception e){
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * MEtodo para comprobar si el nombre de genero existe en la BD
     * @param nombreGen nombre de genero a comprobar
     * @return true si exiete, false si no
     */
    public boolean comprobarGeneroExiste(String nombreGen) {
        nombreGen=nombreGen.toLowerCase();
        boolean ok = false;
        ResultSet rs=null;
        try{
            this.openConnection();
            String select="SELECT COUNT(id) FROM directores"
                    +" WHERE LOWER(nombre) like '"+nombreGen+"'";
            rs=stmt.executeQuery(select);
            if(rs.next()){
                //Si la select devuelve un numero mayor a 0 es que el genero existe
                if (rs.getInt(1) > 0) {
                    ok = true;
                }
            }
            rs.close();
            this.closeConnection();
        }catch (Exception ex){
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }
    /**
     * Metodo que devuelve el id a usar para un nuevo genero
     * @return id maxima+1
     */
    public int obtenerIdGeneroMax() {
        int i = 0;
        ResultSet rs=null;
        try{
            this.openConnection();
            String select="SELECT MAX(id)+1 FROM generos";
            rs=stmt.executeQuery(select);
            if(rs.next()){
                //Obtenemos el id maximo
                i=rs.getInt(1);
            }
            rs.close();
            this.closeConnection();
        }catch (Exception e){
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return i;
    }
    /**
     * Metodo para insertar un genero
     * @param gen genero a insertar
     */
    public void crearGenero(Genero gen) {
        try{
           this.openConnection();
           String insert="INSERT INTO generos VALUES ('"+gen.getId_gen()+"','"+gen.getDescrip_gen()+"')";
           stmt.executeUpdate(insert);
           this.closeConnection();
        }catch(Exception e){
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Metodo para cargar en una lista, todos los generos almacenados en la BD
     * @return Lista con todos los generos cargados
     */
    public ArrayList <Genero> cargarGeneros() {
        ArrayList <Genero> gen= new ArrayList<>();
        Genero g;
        ResultSet rs=null;
        try {
            this.openConnection();
            String select="SELECT id, descripcion FROM generos ORDER BY id";
            rs=stmt.executeQuery(select);
            //Se añaden los generos 
            while (rs.next()){
                g=new Genero();
                g.setId_gen(rs.getInt(1));
                g.setDescrip_gen(rs.getString(1));
                gen.add(g);
            }
            this.closeConnection();
            rs.close();
        } catch (Exception e) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return gen;
    }
    /**
     * Metodo que carga todos los directores almacenados en la BD
     * @return Lista con los directores cargados
     */
    public ArrayList<Director> cargarDirectores() {
        ArrayList<Director> dir= new ArrayList<>();
        Director d;
        ResultSet rs=null;
        try {
            this.openConnection();
            String select="SELECT id, apellidos, pais FROM directores ORDER BY id";
            rs=stmt.executeQuery(select);
            while (rs.next()){
                d=new Director();
                d.setId_Dir(rs.getInt("id"));
                d.setApell(rs.getString("apellidos"));
                d.setPais_dir(rs.getString("pais"));
                dir.add(d);
            }
            this.closeConnection();
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dir;
    }
   /**
     * Metodo que devuelve un director apartir de su nombre y apellido
     * @param nombre nombre de director
     * @param apell apellido de director
     * @return objeto Director
     */
    public Director buscarDirector(String nombre, String apell) {
        Director d = new Director();
        ResultSet rs=null;
        nombre=nombre.toLowerCase();
        apell=apell.toLowerCase();
        try {
            this.openConnection();
            String select="SELECT id, nombre, apellido, pais FROM directores WHERE LOWER(nombre) like '"+nombre+"' and LOWER(apellido) like '"+apell+"'";
            rs=stmt.executeQuery(select);
            while(rs.next()){
                d.setId_Dir(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                d.setApell(rs.getString("apellido"));
                d.setPais_dir(rs.getString("pais"));
            }
            rs.close();
            this.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    /**
     * Metodo que devuelve un genero apartir de su nombre de genero
     * @param genero nombre de genero
     * @return objeto Genero
     */
    public Genero buscaGenero(String nomGenero) {
        Genero g = new Genero();
        nomGenero=nomGenero.toLowerCase();
        ResultSet rs=null;
        try {
            this.openConnection();
            String select="SELECT id, descripcion FROM generos WHERE LOWER(descripcion) like '"+nomGenero+"'";
            rs=stmt.executeQuery(select);
            while(rs.next()){
                g.setId_gen(rs.getInt("id"));
                g.setDescrip_gen(rs.getString("descripcion"));
            }
            rs.close();
            this.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    /**
     * Metodo que devuelve el id a usar por la nueva pelicula
     * @return maxima id+1
     */
    public int obtenerIdPeliculaMax() {
        int i = 0;
        ResultSet rs=null;
        try{
            this.openConnection();
            String select="SELECT MAX(id)+1 FROM peliculas";
            rs=stmt.executeQuery(select);
            if(rs.next()){
                //Obtenemos el id
                i=rs.getInt(1);
            }
            rs.close();
            this.closeConnection();
        }catch (Exception ex){
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    /**
     * Metodo para añadir los datos de una pelicula a la BD
     * @param p pelicula
     */
    /**
     * Metodo para insertar una pelicula o serie
     * @param p pelicula o serie a insertar
     */
    public void aniadirPelicula(Pelicula p) {
        Boolean esSerie = false;
        java.sql.Date fecha = convertUtilToSql(p.getFechaP());
        //Se comprueba si es una serie
        if (p instanceof Serie) {
                esSerie = true;
        }
        try {
            this.openConnection();
            String insert = "insert into peliculas values ('" + p.getId_P() + "'"
                    + ",'" + p.getTituloP() + "','" + fecha + "','" + p.getDuracionP() + "'"
                    + ",'" + p.getPaisP()  + "','" + p.getNotaUsu() + "'"
                    + ",'" + p.getNotaPren() + "','" + esSerie + "')";
            //Se insertan los datos de una pelicula
            stmt.executeUpdate(insert);
            this.closeConnection();
            //Si es serie, se insertaran los datos de serie en su tabla correspondiente
            if (esSerie) {
                fecha = convertUtilToSql(((Serie) p).getFechaFin());
                insert = "insert into series values ('" + p.getId_P() + "'"
                        + ",'" + ((Serie) p).getNumCap() + "','" + ((Serie) p).getEstado() + "'"
                        + ",'" + fecha + "')";

                this.openConnection();
                stmt.executeUpdate(insert);
                this.closeConnection();
            }
        } catch (Exception ex) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   /**
    * Metodo para obtener los datos de un administrador, apartir de su nombre de usuario
    * @param uName nombre de usuario
    * @return objeto administrador
    */
    public Administrador obtenerAdmin(String uName) {
        Administrador a = new Administrador();
        ResultSet rs = null;
        try {
            this.openConnection();
            String select = "SELECT nombreUsuario, pass FROM administradores WHERE nombreUsuario LIKE '" + uName + "'";
            stmt.executeQuery(select);
            while (rs.next()) {
                a.setAdminUser(rs.getString(1));
                a.setAdminPass(rs.getString(2));
            }
            this.closeConnection();
        } catch (Exception e) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return a;
    }
    /**
     * Metodo que devuelve una lista de peliculas o series filtradas por un titulo
     * @param i identificador, si es 1 se buscan series, si es 2 se buscan peliculas
     * @param titulo titulo a buscar
     * @return lista de peliculas o series filtradas
     */
    public ArrayList<Pelicula> obtenerPS(int i, String titulo) {
        titulo = titulo.toLowerCase();
        ArrayList<Pelicula> pelis = new ArrayList<Pelicula>();
        ResultSet rs = null;
        Pelicula p;
        try {
            String select = null;
            if (i == 1) {//ES UNA SERIE
                select = "SELECT id,tituloPelicula,descripcion,"
                        + "fechaPublicacion,duracion,paisPublicacion,tiempoAlquiler,"
                        + "notaUsuarios,notaPrensa,numCaps,estado,fechaFin FROM peliculas JOIN series WHERE esSerie=1 and lower(tituloPelicula) like '" + titulo + "'";
            } else if (i == 2) {//ES UNA PELICUILA
                select = "SELECT id,tituloPelicula,descripcion,"
                        + "fechaPublicacion,duracion,paisPublicacion,tiempoAlquiler,"
                        + "notaUsuarios,notaPrensa FROM peliculas WHERE esSerie=1 and lower(tituloPelicula) like '" + titulo + "'";
            }
            rs=stmt.executeQuery(select);
            java.util.Date fechaPubli;
            java.util.Date fechaFin;
            while (rs.next()) {
                if (i == 1) {
                    //Si es una serie
                    p = new Serie();
                    ((Serie) p).setNumCap(rs.getInt(10));
                    ((Serie) p).setEstado(rs.getString(11));
                    fechaFin = convertSlqToUtil(rs.getDate(12));
                    ((Serie) p).setFechaFin(fechaFin);
                } else {
                    //si es una pelicula
                    p = new Pelicula();
                }
                p.setId_P(rs.getInt(1));
                p.setTituloP(rs.getString(2));
                p.setDescriP(rs.getString(3));
                fechaPubli = convertSlqToUtil(rs.getDate(4));
                p.setFechaP(fechaPubli);
                p.setDuracionP(rs.getInt(5));
                p.setPaisP(rs.getString(6));
                //p.setDuracionAl(rs.getInt(7));
                p.setNotaUsu(rs.getFloat(8));
                p.setNotaPren(rs.getFloat(9));
                pelis.add(p);
            }
            this.closeConnection();
        } catch (Exception e) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }

        return pelis;
    }
    /**
     * Metodo para eliminar una pelicula almacenada en la BD
     * @param pe pelicula a eliminar
     */
    public void eliminarPS(Pelicula pe) {
        String delete = "DELETE FROM peliculas WHERE  id=" + pe.getId_P();
        try {
            this.openConnection();
            stmt.executeUpdate(delete);
            this.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para comprobar si es una serie, apartir de un identificador
     * @param id identificador
     * @return true si es serie, false si es pelicula
     */
    public Boolean esSerie(Integer id) {
        ResultSet rs = null;
        Boolean ok = null;
        try {
            this.openConnection();
            String select = "SELECT esSerie FROM peliculas WHERE id =" + id;
            rs=stmt.executeQuery(select);
            if (rs.next()) {
                ok = rs.getBoolean(1);
            }
            this.closeConnection();
        } catch (Exception e) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return ok;
    }

    /**
     * Metodo que recoge todas las series almacenadas en la BD
     * @return coleccion de series
     */
    public ArrayList<Pelicula> getAllSeries() {
        ArrayList<Pelicula> pelis = new ArrayList<Pelicula>();
        Serie s;
        java.util.Date fechaPubli = null;
        java.util.Date fechaFin = null;
        ResultSet rs = null;
        try {
            String select = "SELECT id,tituloPelicula,descripcion,"
                    + "fechaPublicacion,duracion,paisPublicacion,tiempoAlquiler,"
                    + "notaUsuarios,notaPrensa,numCaps,estado,fechaFin FROM peliculas JOIN series WHERE esSerie=1";
            rs=stmt.executeQuery(select);
            while (rs.next()) {
                s = new Serie();
                s.setId_P(rs.getInt(1));
                s.setTituloP(rs.getString(2));
                s.setDescriP(rs.getString(3));
                fechaPubli = convertSlqToUtil(rs.getDate(4));
                s.setFechaP(fechaPubli);
                s.setDuracionP(rs.getInt(5));
                s.setPaisP(rs.getString(6));
                //s.setDuracionAl(rs.getInt(7));
                s.setNotaUsu(rs.getFloat(8));
                s.setNotaPren(rs.getFloat(9));
                s.setNumCap(rs.getInt(10));
                s.setEstado(rs.getString(11));
                fechaFin = convertSlqToUtil(rs.getDate(12));
                s.setFechaFin(fechaFin);
                s.setGeneros(this.getGenerosPelicula(s.getId_P(), 1));
                s.setDir(this.getDirectorPelicula(s.getId_P(), 1));
                pelis.add(s);
            }
        } catch (Exception e) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return pelis;
    }

    /**
     * Metodo que devuelve todas las peliculas almacenadas en la BD
     *
     * @return coleccion de peliculas
     */
    public ArrayList<Pelicula> getAllPeliculas() {
        ArrayList<Pelicula> pelis = new ArrayList<Pelicula>();
        Pelicula p;
        java.util.Date fechaPubli;
        ResultSet rs = null;
        try {
            String select = "SELECT id,tituloPelicula,descripcion,"
                    + "fechaPublicacion,duracion,paisPublicacion,tiempoAlquiler,"
                    + "notaUsuarios,notaPrensa FROM peliculas WHERE esSerie=0";
            rs=stmt.executeQuery(select);
            while (rs.next()) {
                p = new Pelicula();
                p.setId_P(rs.getInt(1));
                p.setTituloP(rs.getString(2));
                p.setDescriP(rs.getString(3));
                fechaPubli = convertSlqToUtil(rs.getDate(4));
                p.setFechaP(fechaPubli);
                p.setDuracionP(rs.getInt(5));
                p.setPaisP(rs.getString(6));
                //p.setDuracionAl(rs.getInt(7));
                p.setNotaUsu(rs.getFloat(8));
                p.setNotaPren(rs.getFloat(9));
                p.setGeneros(getGenerosPelicula(p.getId_P(),1));
                p.setDir(getDirectorPelicula(p.getId_P(),1));
                pelis.add(p);
            }
        } catch (Exception e) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return pelis;
    }

    /**
     * Metodo que devuelve el director de una pleicula
     *
     * @param id_P identificador de la pelicula
     * @return director de la pelicula
     */
    public Director getDirectorPelicula(int id, int i) {
        Director dir = null;
        ResultSet rs = null;
        try {
            this.openConnection();
            String select = "SELECT id,nombre,apellido,pais FROM directores where id=(select director from peliculas where id= '" + id + "')";
            rs=stmt.executeQuery(select);
            while (rs.next()) {
                dir = new Director();
                dir.setId_Dir(rs.getInt(1));
                dir.setNombre(rs.getString(2));
                dir.setApell(rs.getString(3));
                dir.setPais_dir(rs.getString(4));
            }
            this.closeConnection();
        } catch (Exception e) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return dir;
    }

    /**
     * Metodo que devuelve los generos de una pelicula
     *
     * @param id identificador de la pelicula
     * @return generos de la pelicula
     */
    public ArrayList<Genero> getGenerosPelicula(int id, int i) {
        Genero g;
        ArrayList<Genero> gens = new ArrayList<Genero>();
        ResultSet rs = null;
        try {
            this.openConnection();
            String select = "SELECT DISTINCT id,descripcion FROM generos JOIN peliculasgeneros WHERE idPelicula=" + id;
            rs=stmt.executeQuery(select);
            while (rs.next()) {
                g = new Genero();
                g.setId_gen(rs.getInt(1));
                g.setDescrip_gen(rs.getString(2));
            }
            this.closeConnection();
        } catch (Exception e) {
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return gens;
    }

    @Override
    public Boolean esSerie(int id) {
        Boolean ok=false;
        ResultSet rs=null;
        try{
            this.openConnection();
            String select="SELECT idPelicula from SERIES WHERE idPelicula= "+id;
            rs=stmt.executeQuery(select);
            while (rs.next()) {
                ok=true;
            }
            this.closeConnection();
        }catch(Exception e){
            Logger.getLogger(DBManagerMySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return ok;
    }

    @Override
    public int obtenerIdSerieMax() {
        return this.obtenerIdPeliculaMax();
    }

    
}
