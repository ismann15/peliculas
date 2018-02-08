package model.hibernate;
// Generated 08-feb-2018 21:48:54 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Generos generated by hbm2java
 */
public class Generos  implements java.io.Serializable {


     private int id;
     private String descripcion;
     private Set peliculases = new HashSet(0);

    public Generos() {
    }

	
    public Generos(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    public Generos(int id, String descripcion, Set peliculases) {
       this.id = id;
       this.descripcion = descripcion;
       this.peliculases = peliculases;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Set getPeliculases() {
        return this.peliculases;
    }
    
    public void setPeliculases(Set peliculases) {
        this.peliculases = peliculases;
    }




}


