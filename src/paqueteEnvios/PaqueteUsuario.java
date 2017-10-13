package paqueteEnvios;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class PaqueteUsuario extends Paquete implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String username;
	private List<String> listaDeConectados;
	private List<String> listaDeSalas;
	private String password;
//	private ImageIcon fotoPerfil;

	public PaqueteUsuario() {
	}

	public List<String> getListaDeConectados() {
		return listaDeConectados;
	}

	public void setListaDeConectados(List<String> listaDeConectados) {
		this.listaDeConectados = listaDeConectados;
	}

	public PaqueteUsuario(String user) {
		username = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getListaDeSalas() {
		return listaDeSalas;
	}

	public void setListaDeSalas(List<String> listaDeSalas) {
		this.listaDeSalas = listaDeSalas;
	}

	public void eliminarUsuario(String username) {
		this.listaDeConectados.remove(username);
	}

	public void eliminarSala(String nombreSala) {
		this.listaDeSalas.remove(nombreSala);		
	}
	
//	public ImageIcon getFotoPerfil() {
//		return fotoPerfil;
//	}
//	
//	public String getNombreFoto() {
//		return fotoPerfil.toString();
//	}
//	
//	public void setFotoPerfil(ImageIcon fotoPerfil) {
//		this.fotoPerfil = fotoPerfil;
//	}
//	
//	public ImageIcon buscarFotoPerfil(String nombreFoto) {
//		BufferedImage foto;
//		try {
//			foto = ImageIO.read(new File("resources/"+nombreFoto));
//			return new ImageIcon(foto);
//		} catch (IOException e) {
//			System.out.println("Error al buscar foto en paquete de usuario.");
////			e.printStackTrace();
//		}
//		return null;
//	}
}

                                                               