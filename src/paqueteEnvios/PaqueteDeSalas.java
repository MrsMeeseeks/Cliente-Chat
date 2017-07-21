package paqueteEnvios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PaqueteDeSalas extends Paquete implements Serializable, Cloneable {

	private ArrayList<String> salas;
	//private Map<String, PaqueteUsuario> usuariosConectados;

	public PaqueteDeSalas() {
	}

//	public Map<String, PaqueteUsuario> getSalas() {
//		return usuariosConectados;
//	}

	public PaqueteDeSalas(ArrayList<String> salas) {
		this.salas = salas;
	}

	public ArrayList<String> getSalas() {
		return salas;
	}

	@Override
	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}
}