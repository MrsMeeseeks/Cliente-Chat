package paqueteEnvios;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class PaqueteUsuario extends Paquete implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String username;
	private List<String> listaDeConectados;
	private List<String> listaDeSalas;
	private String password;
	private byte[] fotoPerfil;
	
	private ArrayList<String> listaFotosConectados = new ArrayList<String>();

	public PaqueteUsuario() {
	}

	public List<String> getListaDeConectados() {
		return listaDeConectados;
	}

	public void setListaDeConectados(List<String> listaDeConectados) {
		this.listaDeConectados = listaDeConectados;
	}
	
	public void eliminarUsuario(String username) {
		this.listaDeConectados.remove(username);
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

	public void eliminarSala(String nombreSala) {
		this.listaDeSalas.remove(nombreSala);		
	}
	
	public void setFotoPerfil(byte[] fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	
	public static BufferedImage getFotoEnBufferedImage(byte[] fotoPerfil) throws IOException {
		BufferedImage imagen = null;
		if(fotoPerfil != null && fotoPerfil.length!=0) {
			imagen = ImageIO.read(new ByteArrayInputStream(fotoPerfil));
		}
		return imagen;
	}
	
	public byte[] getFotoPerfil(){
		return fotoPerfil;
	}
	
	public static byte[] deArchivoABytes(File archivo) throws FileNotFoundException {
		FileInputStream archFoto = new FileInputStream(archivo);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = archFoto.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        	System.out.println("Error al setear la imagen en paquete usuario");
        }
		return bos.toByteArray();
	}
	
	//convierte la foto que estaba en bytes en archivo
	public static boolean deBytesAFile(byte[] fotoBytes, String archivoDestino) {
		boolean correcto = false;
		
		try {
		     OutputStream out = new FileOutputStream(archivoDestino);
		     out.write(fotoBytes);
		     out.close();        
		     correcto = true;
		   } catch (Exception e) {
		     e.printStackTrace();
		   } 
		return correcto;
	}
	
    /*
    Este método se utiliza para cargar la imagen de disco
    */
    public static ImageIcon deArchivoAImageIcon(String pathName) {
        BufferedImage bimage = null;
        try {
            bimage = ImageIO.read(new File(pathName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ImageIcon(bimage.getScaledInstance(50, 50,Image.SCALE_DEFAULT));
    }
    
    public static ImageIcon deBytesAImageIcon(byte[] fotoEnBytes) {
    	BufferedImage bimage = null;
    	try {
			 bimage = ImageIO.read(new ByteArrayInputStream(fotoEnBytes));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return new ImageIcon(bimage);
    }

	public ArrayList<String> getListaFotosConectados() {
		return listaFotosConectados;
	}

	public void setListaFotosConectados(ArrayList<String> listaFotosConectados) {
		this.listaFotosConectados = listaFotosConectados;
	}
    
//	public static byte[] deArchivoAImageIcon(File archivo) throws FileNotFoundException {
//	String pathArchElegido = archivo.getPath();
//	
//	//muestra la foto elegida
//	BufferedImage bimage = loadImage(pathArchElegido);
//	ImageIcon iconoPerfil = new ImageIcon(
//			bimage.getScaledInstance(MAX_WIDTH, MAX_HEIGHT,
//					Image.SCALE_DEFAULT));
//	labelImagen.setIcon(iconoPerfil);
//	panelPerfil.add(labelImagen);
//  }
}

                                                               