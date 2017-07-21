package paqueteEnvios;

import java.io.Serializable;
import java.util.ArrayList;

import intefaces.Sala;

public class PaqueteSala extends Paquete implements Serializable, Cloneable {
	
	private String name;
	private String historial = "";
	private ArrayList<String> UsuariosConectados = new ArrayList<String>();
	private String cliente;
	
	

	public PaqueteSala(String name, String chat){
		this.name = name;
		this.historial = chat;
	}
	
	public PaqueteSala() {
	}

	public String getName() {
		return name;
	}
	
	
	public String getHistorial() {
		return historial;
	}
	
	public void setHistorial(String chat) {
		this.historial = chat;
	}
	
	public ArrayList<String> getUsuariosConectados() {
		return UsuariosConectados;
	}
	
	public void setUsuariosConectados(ArrayList<String> usuariosConectados) {
		UsuariosConectados = usuariosConectados;
	}
	
	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

}
