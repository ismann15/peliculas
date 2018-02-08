package model;

public class Genero {
	private int id_gen;
	private String descrip_gen;
        
        public Genero(){
        
        }
        
        public Genero(int id_gen, String descrip_gen) {
            this.descrip_gen=descrip_gen;
            this.id_gen=id_gen;
        }
	public int getId_gen() {
		return id_gen;
	}
	public void setId_gen(int id_gen) {
		this.id_gen = id_gen;
	}
	public String getDescrip_gen() {
		return descrip_gen;
	}
	public void setDescrip_gen(String descrip_gen) {
		this.descrip_gen = descrip_gen;
	}
	
}
