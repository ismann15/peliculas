package model.hibernate;
// Generated 12-feb-2018 13:40:12 by Hibernate Tools 4.3.1



/**
 * Administradores generated by hbm2java
 */
public class Administradores  implements java.io.Serializable {


     private String nombreUsuario;
     private String pass;

    public Administradores() {
    }

    public Administradores(String nombreUsuario, String pass) {
       this.nombreUsuario = nombreUsuario;
       this.pass = pass;
    }
   
    public String getNombreUsuario() {
        return this.nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getPass() {
        return this.pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }




}


