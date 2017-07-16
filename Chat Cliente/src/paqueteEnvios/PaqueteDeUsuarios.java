package paqueteEnvios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PaqueteDeUsuarios extends Paquete implements Serializable, Cloneable {

	private ArrayList<String> usuarios;
	private Map<String, PaqueteUsuario> usuariosConectados;

	public PaqueteDeUsuarios() {
	}

	public Map<String, PaqueteUsuario> getUsuariosConectados() {
		return usuariosConectados;
	}

	public PaqueteDeUsuarios(ArrayList<String> usuarios) {
		this.usuarios = usuarios;
	}

	public ArrayList<String> getPersonajes() {
		return usuarios;
	}

	@Override
	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}
}