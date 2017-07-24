package paqueteEnvios;

import java.io.Serializable;
import java.util.ArrayList;

public class PaqueteMensajeSala extends Paquete implements Serializable, Cloneable {

	private String userEmisor;
	private String msj;
	private String NombreSala;

	public PaqueteMensajeSala() {
	}

	public String getMsj() {
		return msj;
	}

	public void setMsj(String mensaje) {
		this.msj = mensaje;
	}

	public String getUserEmisor() {
		return userEmisor;
	}

	public void setUserEmisor(String idEmisor) {
		this.userEmisor = idEmisor;
	}


	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}

	public String getNombreSala() {
		return NombreSala;
	}

	public void setNombreSala(String nombreSala) {
		NombreSala = nombreSala;
	}
}
