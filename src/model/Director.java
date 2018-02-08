package model;

public class Director {

    private String nombre;
    private String apell;
    private String pais_dir;
    private int id_Dir;

    public Director(){
    
    }
    
    public Director(Integer id_Dir, String nombre, String apell, String pais_dir) {
       this.id_Dir=id_Dir;
       this.nombre= nombre;
       this.apell= apell;
       this.pais_dir= pais_dir;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApell() {
        return apell;
    }

    public void setApell(String apell) {
        this.apell = apell;
    }

    public String getPais_dir() {
        return pais_dir;
    }

    public void setPais_dir(String pais_dir) {
        this.pais_dir = pais_dir;
    }

    public int getId_Dir() {
        return id_Dir;
    }

    public void setId_Dir(int id_Dir) {
        this.id_Dir = id_Dir;
    }

}
