package intefaces;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import paqueteEnvios.PaqueteUsuario;

public class RenderLista extends JLabel implements ListCellRenderer<Object>{
	 
	private static final long serialVersionUID = 1L;
	Hashtable<String, ImageIcon> elementos;
	
	public static int MAX_WIDTH=50;
    public static int MAX_HEIGHT=50;
	
	 ImageIcon icononulo= PaqueteUsuario.deArchivoAImageIcon("Imagenes/noItem.png");
	 
	 public RenderLista(List<String> list, Hashtable<String, byte[]> hashConectados){
		 elementos=new Hashtable<String, ImageIcon>();
	    if(!hashConectados.isEmpty()) {
	    	for(String username : list) {
	    		System.out.println(username);
		    	ImageIcon fotoOriginal = PaqueteUsuario.deBytesAImageIcon(hashConectados.get(username));
		    	ImageIcon fotoRedimensionada= new ImageIcon(fotoOriginal.getImage().getScaledInstance(
		    			MAX_WIDTH, MAX_HEIGHT, Image.SCALE_DEFAULT));
		    	
				this.elementos.put(username, fotoRedimensionada);
			}
	    }
		
	 }
	 public RenderLista(List<String> list){
		 elementos=new Hashtable<String, ImageIcon>();
		 if(list!=null && !list.isEmpty()) {
			 for(String username : list) {
	    		String nombreArchivo = "Imagenes/"+username+".png";
		    	ImageIcon fotoOriginal = PaqueteUsuario.deArchivoAImageIcon(nombreArchivo);
		    	ImageIcon fotoRedimensionada= new ImageIcon(fotoOriginal.getImage().getScaledInstance(
		    			MAX_WIDTH, MAX_HEIGHT, Image.SCALE_DEFAULT));
		    	
				this.elementos.put(username, fotoRedimensionada);
			}
		 }
	 }
	 
	 public RenderLista(){
		 elementos=new Hashtable<String, ImageIcon>();
		 
		String nombreArchivo = "Imagenes/carla.png";
    	ImageIcon fotoOriginal = PaqueteUsuario.deArchivoAImageIcon(nombreArchivo);
    	ImageIcon fotoRedimensionada= new ImageIcon(fotoOriginal.getImage().getScaledInstance(
    			MAX_WIDTH, MAX_HEIGHT, Image.SCALE_DEFAULT));
    	
		this.elementos.put("carla", fotoRedimensionada);
		
		String nombreArchivo2 = "Imagenes/carla2.png";
    	ImageIcon fotoOriginal2 = PaqueteUsuario.deArchivoAImageIcon(nombreArchivo2);
    	ImageIcon fotoRedimensionada2= new ImageIcon(fotoOriginal2.getImage().getScaledInstance(
    			MAX_WIDTH, MAX_HEIGHT, Image.SCALE_DEFAULT));
    	
		this.elementos.put("carla2", fotoRedimensionada2);
		
		String nombreArchivo3 = "Imagenes/carla3.png";
    	ImageIcon fotoOriginal3 = PaqueteUsuario.deArchivoAImageIcon(nombreArchivo3);
    	ImageIcon fotoRedimensionada3= new ImageIcon(fotoOriginal3.getImage().getScaledInstance(
    			MAX_WIDTH, MAX_HEIGHT, Image.SCALE_DEFAULT));
    	
    	this.elementos.put("carla3", fotoRedimensionada3);
    	
    	String nombreArchivo4 = "Imagenes/carla4.png";
    	ImageIcon fotoOriginal4 = PaqueteUsuario.deArchivoAImageIcon(nombreArchivo4);
    	ImageIcon fotoRedimensionada4= new ImageIcon(fotoOriginal4.getImage().getScaledInstance(
    			MAX_WIDTH, MAX_HEIGHT, Image.SCALE_DEFAULT));
    	
    	this.elementos.put("carla4", fotoRedimensionada4);
	 }


	 @Override
	 public Component getListCellRendererComponent(JList<?> list, Object value,int index, boolean isSelected, boolean cellHasFocus) {
	  if(elementos.get(value)!=null){
	   setIcon(elementos.get(value));
	   setText(""+value);
	   if(isSelected){
	      setFont(new Font("Verdana",Font.BOLD,14));
	   }else{
	    setFont(null);
	   }
	  }else{
	    setIcon(icononulo);
	    setText(""+value);
	    if(isSelected){
	      setFont(new Font("Verdana",Font.BOLD,14));
	   }else{
	    setFont(null);
	   }
	  }
	  return this;
	 }
}
