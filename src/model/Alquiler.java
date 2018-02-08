package model;

import java.sql.Date;

public class Alquiler {
	private int id_P;
	private Date fech_A;
	private Date fech_D;
	public int getId_P() {
		return id_P;
	}
	public void setId_P(int id_P) {
		this.id_P = id_P;
	}
	public Date getFech_A() {
		return fech_A;
	}
	public void setFech_A(Date fech_A) {
		this.fech_A = fech_A;
	}
	public Date getFech_D() {
		return fech_D;
	}
	public void setFech_D(Date fech_D) {
		this.fech_D = fech_D;
	}
	
}
