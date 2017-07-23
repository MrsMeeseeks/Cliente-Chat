package paqueteEnvios;

import java.io.Serializable;
import java.util.ArrayList;

import intefaces.Sala;

public class PaqueteSala extends Paquete implements Serializable, Cloneable {
	
	private String nombreSala;
	private String historial = "";
	private String texto;

	private ArrayList<String> UsuariosConectados = new ArrayList<String>();
	private String cliente;
	
	

	public PaqueteSala(String name, String chat){
		this.nombreSala = name;
		this.historial = chat;
	}
	
	public PaqueteSala() {
	}

	public String getNombreSala() {
		return nombreSala;
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

	public void setNombreSala(String name) {
		this.nombreSala = name;
	}
	
	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}


	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
