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
	    	System.out.println("entró");
	    	for(String username : list) {
	    		System.out.println("entró a mapear "+username);
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
