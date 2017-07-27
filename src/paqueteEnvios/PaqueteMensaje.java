package paqueteEnvios;

import java.io.Serializable;

public class PaqueteMensaje extends Paquete implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String userEmisor;
	private String userReceptor;
	private String msj;
	private String nombreSala;

	public PaqueteMensaje() {
	}
	
	public PaqueteMensaje(String emisor, String destinatario, String msj, String nombreSala) {
		this.userEmisor = emisor;
		this.userReceptor = destinatario;
		this.msj = msj;
		this.nombreSala = nombreSala;
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

	public String getUserReceptor() {
		return userReceptor;
	}

	public void setUserReceptor(String idReceptor) {
		this.userReceptor = idReceptor;
	}
	
	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}

	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}
}
