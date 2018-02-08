package model;

import java.util.ArrayList;
import java.util.Date;

public class Pelicula {

    private int id_P;
    private String tituloP;
    private String paisP;
    private Date fechaP;
    private int duracionP;
    private String descriP;
    private float notaUsu;
    private float notaPren;
    private ArrayList<Genero> gen;
    private Director dir;
    private int duracionAl;

    public ArrayList<Genero> getGen() {
        return gen;
    }

    public void setGen(ArrayList<Genero> gen) {
        this.gen = gen;
    }

    public int getId_P() {
        return id_P;
    }

    public void setId_P(int id_P) {
        this.id_P = id_P;
    }

    public String getTituloP() {
        return tituloP;
    }

    public void setTituloP(String tituloP) {
        this.tituloP = tituloP;
    }

    public String getPaisP() {
        return paisP;
    }

    public void setPaisP(String paisP) {
        this.paisP = paisP;
    }

    public Date getFechaP() {
        return fechaP;
    }

    public void setFechaP(Date fechaP) {
        this.fechaP = fechaP;
    }

    public int getDuracionP() {
        return duracionP;
    }

    public void setDuracionP(int duracionP) {
        this.duracionP = duracionP;
    }

    public String getDescriP() {
        return descriP;
    }

    public void setDescriP(String descriP) {
        this.descriP = descriP;
    }

    public float getNotaUsu() {
        return notaUsu;
    }

    public void setNotaUsu(float notaUsu) {
        this.notaUsu = notaUsu;
    }

    public float getNotaPren() {
        return notaPren;
    }

    public void setNotaPren(float notaPren) {
        this.notaPren = notaPren;
    }

    public int getDuracionAl() {
        return duracionAl;
    }

    public void setDuracionAl(int duracionAl) {
        this.duracionAl = duracionAl;
    }

    public Director getDir() {
        return dir;
    }

    public void setDir(Director dir) {
        this.dir = dir;
    }

    public boolean buscaGenero(Genero g) {
        boolean ok = false;
        for (int i = 0; i < gen.size(); i++) {
            if (gen.get(i).getId_gen() == g.getId_gen()) {
                ok = true;
                break;
            }
        }
        return ok;
    }

    public void setGenero(Genero g) {
        gen.add(g);
    }

    public void delGen(Genero g) {
        for (Genero genero : gen) {
            if (genero.getId_gen() == g.getId_gen()) {
                gen.remove(g);
                break;
            }
        }

    }

    public void borrarGeneros() {
        gen.clear();
    }

    public String getDirector() {
        String director = dir.getNombre() +" "+ dir.getApell();
        return director;
    }

    public ArrayList<Genero> getGeneros(ArrayList<Genero> g) {
        g.clear();
        for (Genero genero : gen) {
            g.add(genero);
        }
        return g;
    }
    
    public ArrayList<Genero> getGeneros() {
        ArrayList<Genero> g= new ArrayList<>();
        for (Genero genero : gen) {
            g.add(genero);
        }
        return g;
    }

    public Genero getGenero(int i) {
        return gen.get(i);
    }
    
    public void setGeneros(ArrayList<Genero> g){
        for (Genero genero : g) {
            gen.add(genero);
        }
    }

}
