package paqueteEnvios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PaqueteDeUsuariosYSalas extends Paquete implements Serializable, Cloneable {

	
	private static final long serialVersionUID = 1L;
	private ArrayList<String> usuarios;
	private ArrayList<String> salas;
	private ArrayList<String> fotos = new ArrayList<String>();
//	private ArrayList<PaqueteUsuario> paqUsuarios;
	
	private Map<String, PaqueteUsuario> usuariosConectados;

	public PaqueteDeUsuariosYSalas() {
	}

	public Map<String, PaqueteUsuario> getUsuariosConectados() {
		return usuariosConectados;
	}
	
	public PaqueteDeUsuariosYSalas(ArrayList<String> usuarios, ArrayList<String> salas) {
		this.usuarios = usuarios;
		this.salas = salas;
	}
	
	public PaqueteDeUsuariosYSalas(ArrayList<String> usuarios, ArrayList<String> salas,
			ArrayList<String> fotos) {
		this.usuarios = usuarios;
		this.salas = salas;
		this.fotos = fotos;
	}

	public PaqueteDeUsuariosYSalas(ArrayList<String> usuarios) {
		this.usuarios = usuarios;
//		this.fotos = fotos;
	}

	public ArrayList<String> getUsuarios() {
		return usuarios;
	}

	public ArrayList<String> getSalas() {
		return salas;
	}
	
	public ArrayList<String> getFotos() {
		return fotos;
	}
	
	@Override
	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}
	
}