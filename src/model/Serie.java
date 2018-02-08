package model;

import java.util.Date;

public class Serie extends Pelicula {
	private int numCap;
	private Date fechaFin;
	private String estado;
	public int getNumCap() {
		return numCap;
	}
	public void setNumCap(int numCap) {
		this.numCap = numCap;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/*public void setDatos(){
		System.out.println("Introduce numero de capitulos");
		numCap=Utilidades.leerInt();
		super.setDatos();
	}*/
	
}
