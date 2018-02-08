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
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Indexes;
import static com.mongodb.client.model.Indexes.descending;
import java.awt.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author Gigabyte
 */
public class DBManagerMongoDB implements Logica {

    MongoClient cliente;
    MongoDatabase db;
    MongoCollection<Document> coleccion;
    private static final Logger LOGGER = Logger.getLogger("Control");

    public DBManagerMongoDB() {
        cliente = new MongoClient("localhost", 27017);
        db = cliente.getDatabase("videoclubOnline");
    }

    @Override
    public boolean comprobarNombreUsu(String usu) {
        coleccion = db.getCollection("administradores");
        Boolean ok = false;
        Document doc = coleccion.find(eq("nombreUsuario", usu)).first();
        if (!(doc.isEmpty())) {
            //Existe un usuario con ese nombre de suario
            ok = true;
        } else {
            LOGGER.info("no se han escontrado uasuarios " + usu);
        }
        return ok;
    }

    @Override
    public boolean comprobarPass(String nombreU, String pass) {
        coleccion = db.getCollection("administradores");
        Boolean ok = false;
        Document doc = coleccion.find(and(eq("nombreUsuario", nombreU), eq("password", pass))).first();
        if (!(doc.isEmpty())) {
            //Existe un usuario con ese nombre de suario
            ok = true;
        } else {
            LOGGER.info("la contraseña no coincide para " + nombreU);
        }
        return ok;
    }

    @Override
    public boolean directorExiste(String nombre, String apell) {
        coleccion = db.getCollection("directores");
        Boolean ok = false;
        Document doc = coleccion.find(and(eq("nombreDirector", nombre), eq("apellidoDirector", apell))).first();
        if (!(doc.isEmpty())) {
            //Existe un usuario con ese nombre de suario
            ok = true;
        } else {
            LOGGER.info("no se ha encontrado al director  " + nombre + " " + apell);
        }
        return ok;
    }

    /**
     * Metodo publico que devuelve la id de dirtector correcta a usar
     * @return id de director
     */
    @Override
    public int obtenerIdDirectorMax() {
        coleccion = db.getCollection("directores");
        int id = 0;
        //Se optienen todos los documentos de directores y se ordenan de manera descendente por idDirector
        MongoCursor<Document> docs = coleccion.find().sort(descending("idDirector")).iterator();
        while (docs.hasNext()) {
            //Se obtiene la primera instancia, que es el que tiene la id mas alta y sale del while
            id = docs.next().getInteger("idDirector", 0);
            break;
        }
        //cerramos el cursor
        docs.close();
        return id + 1;
    }

    /**
     * Metodo para insertar un director en la BD
     *
     * @param dir director a insertar en la BD
     */
    @Override
    public void crearDirector(Director dir) {
        coleccion = db.getCollection("directores");
        //Se crea un documento
        Document director = new Document();
        //Se insertan los datos
        director.put("idDirector", dir.getId_Dir());
        director.put("nombreDirector", dir.getNombre());
        director.put("apellidoDirector", dir.getApell());
        director.put("paisDirector", dir.getPais_dir());
        //Se inserta el documento
        coleccion.insertOne(director);
    }

    /**
     * Metodo que comprueba si el nombre de genero coincide con alguno que haya
     * en la BD
     *
     * @param nombreGen nombre de genero que se va a comprobar si existe
     * @return true si existe, false si no existe
     */
    @Override
    public boolean comprobarGeneroExiste(String nombreGen) {
        coleccion = db.getCollection("directores");
        //Se buscan todos los directores almacendos
        MongoCursor<Document> docs = coleccion.find().iterator();
        Boolean ok=false;
        while (docs.hasNext()) {
            //Se le asigna a un objeto Document el documento next
            Document doc= docs.next();
            //Si coinciden, cambiamos el valor de ok a true y se sale del while
            if(doc.getString("nombreGenero").equalsIgnoreCase(nombreGen)){
                ok=true;
                break;
            }
        }
        //Se cierra el cursor
        docs.close();
        //se retorna ok
        return ok;
    }
    /**
     * Metodo que devuelve todos los directores almacenados
     * @return directores almacenados
     */
    @Override
    public ArrayList<Director> cargarDirectores() {
        coleccion=db.getCollection("directores");
        //Se crea una lista de diorectores
        ArrayList<Director>directores= new ArrayList<>();
        //Se crea un objeto director
        Director dire;
        //Se crea un iterador, con todos los directores
        MongoCursor<Document> docs=coleccion.find().iterator();
        //Se almacenan en la lista todos los directores
        while(docs.hasNext()){
            dire= new Director();
            dire.setId_Dir(docs.next().getInteger("idDirector"));
            dire.setNombre(docs.next().getString("nombreDirector"));
            dire.setApell(docs.next().getString("apellidoDirector"));
            dire.setPais_dir(docs.next().getString("paisDirector"));
            directores.add(dire);
        }
        //Se cierra el iterador
        docs.close();
        //Se devuelve la lista de directores
        return directores;
    }
    /**
     *  Metodo publico que devuelve la id de dirtector correcta a usar
     * @return id de genero
     */
    @Override
    public int obtenerIdGeneroMax() {
        coleccion = db.getCollection("generos");
        int id = 0;
        //Se optienen todos los documentos de generos y se ordenan de manera descendente por idGenero
        MongoCursor<Document> docs = coleccion.find().sort(descending("idGenero")).iterator();
        while (docs.hasNext()) {
            //Se obtiene la primera instancia, que es el que tiene la id mas alta y sale del while
            id = docs.next().getInteger("idGenero", 0);
            break;
        }
        //cerramos el cursor
        docs.close();
        return id + 1;
    }
    /**
     * Metodo para insertar un genero en la BD
     * @param gen genero a insertar
     */
    @Override
    public void crearGenero(Genero gen) {
        coleccion=db.getCollection("generos");
        //Se crea un objeto Document
        Document doc= new Document();
        //Se agregan los datos a este documento
        doc.put("idGenero", gen.getId_gen());
        doc.put("nombreGenero", gen.getDescrip_gen());
        //Se agrega el documento a la coleccion
        coleccion.insertOne(doc);
    }
    /**
     * Metodo que devuelve todos los generos almacenados
     * @return generos almecenados
     */
    @Override
    public ArrayList<Genero> cargarGeneros() {
        coleccion=db.getCollection("generos");
        //Se crea una lista de generos
        ArrayList<Genero>generos= new ArrayList<>();
        //Se crea un objeto genero
        Genero g;
        //Se optienen todos los generos
        MongoCursor<Document> docs= coleccion.find().iterator();
        //Se obtienen todos los generos y se van almacenando en la lista creada anteriormente
        while(docs.hasNext()){
            g= new Genero();
            g.setId_gen(docs.next().getInteger("idGenero"));
            g.setDescrip_gen(docs.next().getString("nombreGenero"));
            generos.add(g);
            
        }
        //cerramos el cursor
        docs.close();
        //Se retorna la lista
        return generos;
    }
    /**
     * Metodo para buscar un director por su nombre y apellido, en este caso el Director 
     * no contendra null, ya que previamente se ha asegurado que nombre y apell son correctos
     * @param nombre nombre de director
     * @param apell apellido de director
     * @return el objeto director
     */
    @Override
    public Director buscarDirector(String nombre, String apell) {
        coleccion=db.getCollection("directores");
        //Se crea un objeto director
        Director d= new Director();
        //Se crea un documento
        Document doc= new Document();
        //Se almacena el director a buscar en el documento
        doc = coleccion.find(and(eq("nombredirector",nombre),eq("apellidoDirector",apell))).first();
        //Se insertan los datos del documento en el objeto director
        d.setId_Dir(doc.getInteger("idDirector"));
        d.setNombre(doc.getString("nombreDirector"));
        d.setApell(doc.getString("apellidoDirector"));
        d.setPais_dir(doc.getString("paisDirector"));
        //Se retorna el objeto director
        return d;
    }
    /**
     * Metodo para buscar un objeto Genero por su nombre de genero, en este caso el Genero 
     * no contendra null, ya que previamente se ha asegurado que nombreGenero es correcto
     * @param nombreGenero nombre de genero
     * @return el objeto genero
     */
    @Override
    public Genero buscaGenero(String nombreGenero) {
        coleccion=db.getCollection("generos");
        //Se crea un objeto genero
        Genero g= new Genero();
        //Se crea un documento
        Document doc= new Document();
        //Se almacena el genero a buscar en el documento
        doc = coleccion.find(eq("nombreGenero", nombreGenero)).first();
        //Se insertan los datos del documento en el objeto genero
        g.setId_gen(doc.getInteger("idGenero"));
        g.setDescrip_gen(doc.getString("nombreGenero"));
        //Se retorna el objeto genero
        return g;
    }
    /**
     * Metodopublico para obtener el id de pelicula correcta a usar
     * @return id de pelicula
     */
    @Override
    public int obtenerIdPeliculaMax() {
        coleccion=db.getCollection("peliculas");
         int id = 0;
        //Se optienen todos los documentos de peliculas y series y se ordenan de manera descendente por id_P
        MongoCursor<Document> docs = coleccion.find().sort(descending("id_P")).iterator();
        while (docs.hasNext()) {
            //Se obtiene la primera instancia, que es el que tiene la id mas alta y sale del while
            id = docs.next().getInteger("id_P", 0);
            break;
        }
        //cerramos el cursor
        docs.close();
        return id + 1;
        
    }

    @Override
    public void aniadirPelicula(Pelicula p) {
        Boolean ok=false;
        SimpleDateFormat dma= new SimpleDateFormat("dd/MM/yyyy");
        coleccion=db.getCollection("peliculas");
        //Se crea un documento
        Document doc= new Document();
        //Comprueba que la eplicula es una serie, y si lo es le inserta los datos de serie
        if(p instanceof Serie){
            ok=true;
            doc.put("numCap",((Serie)p).getNumCap());
            doc.put("fechaFin",dma.format(((Serie)p).getFechaFin()));
            doc.put("estado",((Serie)p).getEstado());
        }
        //inserta el resto de datos comunes
        doc.put("id:P", p.getId_P());
        doc.put("tituloP", p.getTituloP());
        doc.put("paisP",p.getPaisP());
        doc.put("fechaP", dma.format(p.getFechaP()));
        doc.put("duracionP",p.getDuracionP());
        doc.put("descriP", p.getDescriP());
        double notaUsu= p.getNotaUsu();
        doc.put("notaUsu", notaUsu);
        double notaPren= p.getNotaPren();
        doc.put("notaPren", notaPren);
        //Se crea un nuevo documento para añadir los datos del director
        Document director= new Document();
        director.put("idDirector",p.getDir().getId_Dir());
        director.put("nombreDirector",p.getDir().getNombre());
        director.put("apellidoDirector",p.getDir().getApell());
        director.put("paisDirector",p.getDir().getPais_dir());
        //se añade al docuemnto que contiene los datos de la pelicula, 
        //el documento que contiene los datos del director
        doc.put("director",director);
        //Para insertar los generos, se recogen los generos en una lista
        ArrayList<Genero> gens=p.getGeneros();
        //Se crea una lista de documentos para añadir los datos de los generos
        ArrayList <Document> generos= new ArrayList<>();
        //Se insertan los datos de los generos
        for(int i=0;i<gens.size();i++){
            generos.get(i).put("idGenero",gens.get(i).getId_gen());
            generos.get(i).put("nombreGenero",gens.get(i).getDescrip_gen());
        }
        //se agregan los generos al documento de la pelicula
        doc.put("generos", generos);
        //si es serie sera true
        doc.put("esSerie", ok);
    }
    /**
     * Metodo para devolver un administrador apartir de su id
     * @param uName identidicador de administrador
     * @return objeto administrador
     */
    @Override
    public Administrador obtenerAdmin(String uName) {
        coleccion= db.getCollection("administradores");
        //Se guarda al administrador en un documento
        Document doc= coleccion.find(eq("nombreUsuario", uName)).first();
        return new Administrador(doc.getString("nombreUsuario"),doc.getString("password"));
    }
    /**
     * Metodo que busca series o peliculas por titulo
     * @param i identificador, si es 1 busca series, si es 2 busca peliculas
     * @param titulo titulo a buscar
     * @return lista de peliculas o series que coincidan con el titulo
     */
    @Override
    public ArrayList<Pelicula> obtenerPS(int i, String titulo) {
        titulo=titulo.toLowerCase();
        Boolean ok;
        if(i==1){//BUSCA SERIES
            ok=true;
        }else{//BUSCA PELICULAS
            ok=false;
        }
        SimpleDateFormat dma= new SimpleDateFormat("dd/MM/yyyy");
        coleccion= db.getCollection("peliculas");
        //Se recogen en una lista todos los documentos  que contienen peliculas
        ArrayList <Document> docs= (ArrayList <Document>) coleccion.find(and(eq("esSerie",ok),eq("tituloP",titulo)));
        //Se crea una lista de peliculas para almacenar todas
        ArrayList<Pelicula> peliculas= new ArrayList<>();
        //Se recogen los datos de cada pelicula y se insertan en la lista de peliculas
        for (Document doc : docs) {
            Pelicula p= new Pelicula();
            p.setId_P(doc.getInteger("id_P"));
            p.setTituloP(doc.getString("tituloP"));
            p.setPaisP(doc.getString("paisP"));
            //Se intenta parsear la fecha que viene como string a date
            try {
                p.setFechaP(dma.parse(doc.getString("fechaP")));
            } catch (ParseException ex) {
                //Si salta la excepcion, la fecha se modificara a la fecha actual
                p.setFechaP(new Date());
            }
            p.setDuracionP(doc.getInteger("duracionP"));
            p.setDescriP(doc.getString("descriP"));
            double notaPren=doc.getDouble("notaPren");
            p.setNotaPren((float)notaPren);
            double notaUsu=doc.getDouble("notaUsu");
            p.setNotaUsu((float)notaUsu);
            p.setDir(getDirectorPelicula(doc.getInteger("id_P")));
            p.setGeneros(getGenerosPelicula(doc.getInteger("id_P")));
            if(ok){
                ((Serie)p).setEstado(doc.getString("estado"));
                ((Serie)p).setNumCap(doc.getInteger("numCap"));
                try {
                    ((Serie)p).setFechaFin(dma.parse(doc.getString("fechaF")));
                } catch (ParseException ex) {
                    //Si salta la excepcion, la fecha se modificara a la fecha actual
                ((Serie)p).setFechaFin(new Date());
                }
            }
            peliculas.add(p);
        }
        return peliculas;
        
    }
    /**
     * Metodo que elimina una pelicula almacenada
     * @param pe pelicula a eliminar
     */
    @Override
    public void eliminarPS(Pelicula pe) {
         coleccion= db.getCollection("peliculas");
        //Se recogen en una lista todos los documentos  que contienen peliculas
        Document doc= coleccion.find(eq("id_P",pe.getId_P())).first();
        coleccion.deleteOne(doc);
    }
    
    /**
     * Metodo que comprueba si es una pelicula o una serie, apartir de una id
     * @param id identificador de pelicula o serie
     * @return true si es serie, false si es pelicula
     */
    @Override
    public Boolean esSerie(int id) {
        Boolean ok=false;
        coleccion= db.getCollection("peliculas");
        //Se recoge la pelicula en un documento
        Document doc= coleccion.find(eq("id_P", id)).first();
        //se comprueba si el campo esSerie es true
        if(doc.getBoolean("esSerie")){
            ok=true;
        }
        return ok;
    }

    /**
     * Metodo para recuperar todas las series
     * @return todas las series almacenadas
     */
    @Override
    public ArrayList<Pelicula> getAllSeries() {
        SimpleDateFormat dma= new SimpleDateFormat("dd/MM/yyyy");
        coleccion= db.getCollection("peliculas");
        //Se recogen en una lista todos los documentos  que contienen peliculas
        ArrayList <Document> docs= (ArrayList <Document>) coleccion.find(eq("esSerie",true));
        //Se crea una lista de peliculas para almacenar todas
        ArrayList<Pelicula> series= new ArrayList<>();
        //Se recogen los datos de cada pelicula y se insertan en la lista de peliculas
        for (Document doc : docs) {
            Serie s= new Serie();
            s.setId_P(doc.getInteger("id_P"));
            s.setTituloP(doc.getString("tituloP"));
            s.setPaisP(doc.getString("paisP"));
            //Se intenta parsear la fecha que viene como string a date
            try {
                s.setFechaP(dma.parse(doc.getString("fechaP")));
            } catch (ParseException ex) {
                //Si salta la excepcion, la fecha se modificara a la fecha actual
                s.setFechaP(new Date());
            }
            s.setDuracionP(doc.getInteger("duracionP"));
            s.setDescriP(doc.getString("descriP"));
            double notaPren=doc.getDouble("notaPren");
            s.setNotaPren((float)notaPren);
            double notaUsu=doc.getDouble("notaUsu");
            s.setNotaUsu((float)notaUsu);
            s.setEstado(doc.getString("estado"));
            s.setNumCap(doc.getInteger("numCap"));
            try {
                s.setFechaFin(dma.parse(doc.getString("fechaF")));
            } catch (ParseException ex) {
                //Si salta la excepcion, la fecha se modificara a la fecha actual
                s.setFechaFin(new Date());
            }
            s.setDir(getDirectorPelicula(doc.getInteger("id_P")));
            s.setGeneros(getGenerosPelicula(doc.getInteger("id_P")));
            series.add(s);
        }
        return series;
    }
    /**
     * Metodo para obtener todas las peliculas 
     * @return peliculas almecenadas 
     */
    @Override
    public ArrayList<Pelicula> getAllPeliculas() {
        SimpleDateFormat dma= new SimpleDateFormat("dd/MM/yyyy");
        coleccion= db.getCollection("peliculas");
        //Se recogen en una lista todos los documentos  que contienen peliculas
        ArrayList <Document> docs= (ArrayList <Document>) coleccion.find(eq("esSerie",false));
        //Se crea una lista de peliculas para almacenar todas
        ArrayList<Pelicula> peliculas= new ArrayList<>();
        //Se recogen los datos de cada pelicula y se insertan en la lista de peliculas
        for (Document doc : docs) {
            Pelicula p= new Pelicula();
            p.setId_P(doc.getInteger("id_P"));
            p.setTituloP(doc.getString("tituloP"));
            p.setPaisP(doc.getString("paisP"));
            //Se intenta parsear la fecha que viene como string a date
            try {
                p.setFechaP(dma.parse(doc.getString("fechaP")));
            } catch (ParseException ex) {
                //Si salta la excepcion, la fecha se modificara a la fecha actual
                p.setFechaP(new Date());
            }
            p.setDuracionP(doc.getInteger("duracionP"));
            p.setDescriP(doc.getString("descriP"));
            double notaPren=doc.getDouble("notaPren");
            p.setNotaPren((float)notaPren);
            double notaUsu=doc.getDouble("notaUsu");
            p.setNotaUsu((float)notaUsu);
            p.setDir(getDirectorPelicula(doc.getInteger("id_P")));
            p.setGeneros(getGenerosPelicula(doc.getInteger("id_P")));
            peliculas.add(p);
        }
        return peliculas;
    }
    /**
     * Metodo para obtener el director de una pelicula, apartir de la id de la pelicula
     * @param id identificador de la pleicula, que se quiere saber su director
     * @return director de la pelicula
     */
    @Override
    public Director getDirectorPelicula(int id) {
        coleccion=db.getCollection("peliculas");
        //Recogemos en un documento la pelicula con id_P igual a id
        Document doc= coleccion.find(eq("id_P",id)).first();
        //Se crea un documento para almacenar los dfatos del director
        Document d= (Document) doc.get("director");
        //se devuleve el director
        return new Director(d.getInteger("idDirector"),d.getString("nombreDirector"),d.getString("apellidoDirector"),d.getString("paisDirector"));
    }
    /**
     * Metodo para obtener los generos de una pelicula apartir de la id
     * @param id identificador de la pelicula, que se quieren saber sus generos
     * @return lista de generos de la pelicula
     */
    @Override
    public ArrayList<Genero> getGenerosPelicula(int id) {
        coleccion=db.getCollection("peliculas");
        //Se crea una lista de generos
        ArrayList<Genero> generos= new ArrayList<>();
        //Recogemos en un documento la pelicula con id_P igual a id
        Document doc= coleccion.find(eq("id_P",id)).first();
        //Se crea una lista de documentos y se le inserta la lista de generos que tiene la pelicula
        ArrayList <Document> gens= (ArrayList<Document>) doc.get("generos");
        //Se insertan los datos de cada genero en la lista
        for (Document gen : gens) {
            generos.add(new Genero(gen.getInteger("idGenero"),gen.getString("nombreGenero")));   
        }
        //se devuelve la lista con los generos 
        return generos;
    }

}
