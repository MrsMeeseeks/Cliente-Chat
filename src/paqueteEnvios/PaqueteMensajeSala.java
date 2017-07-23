package paqueteEnvios;

import java.io.Serializable;
import java.util.ArrayList;

public class PaqueteMensajeSala extends Paquete implements Serializable, Cloneable {

	private String userEmisor;
	private ArrayList<String> usersDestino;
	private String msj;
	private String NombreSala;

	public PaqueteMensajeSala() {
	}

	public String getMensaje() {
		return msj;
	}

	public void setMensaje(String mensaje) {
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

	public ArrayList<String> getUsersDestino() {
		return usersDestino;
	}

	public void setUsersDestino(ArrayList<String> usersDestino) {
		this.usersDestino = usersDestino;
	}

	public String getNombreSala() {
		return NombreSala;
	}

	public void setNombreSala(String nombreSala) {
		NombreSala = nombreSala;
	}
}
