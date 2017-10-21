package cliente;

import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.google.gson.Gson;

import comandosEscuchaServer.ComandoEscuchaServer;
import intefaces.VentanaPrincipal;
import paqueteEnvios.Comando;
import paqueteEnvios.Paquete;
import paqueteEnvios.PaqueteSala;

public class EscuchaServer extends Thread {

	private Cliente cliente;
	private ObjectInputStream entrada;
	
	private final Gson gson = new Gson();

protected static ArrayList<String> usuariosConectados = new ArrayList<String>();
//protected static Hashtable<String,byte[]> fotosUsuariosConectados = new Hashtable<String,byte[]>();

	public EscuchaServer(final Cliente cliente) {
		this.cliente = cliente;
		this.entrada = cliente.getEntrada();
	}

	@Override
	public void run() {
		try {

			ComandoEscuchaServer comando;
			Paquete paquete;

			String cadenaLeida = (String) entrada.readObject();
			while (!((paquete = gson.fromJson(cadenaLeida, Paquete.class)).getComando() == Comando.DESCONECTAR)) {

				comando = (ComandoEscuchaServer) paquete.getObjeto("comandosEscuchaServer");
				comando.setCadena(cadenaLeida);
				comando.setEscuchaServer(this);
				comando.ejecutar();

				synchronized (entrada) {
					cadenaLeida = (String) entrada.readObject();
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fallo la conexión con el servidor.");
			e.printStackTrace();
		}
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void actualizarListaConectadosSala(PaqueteSala paqueteSala) {
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		synchronized (cliente) {

			cliente.getSalasActivas().get(paqueteSala.getNombreSala()).eliminarConectados();

			if (paqueteSala.getUsuariosConectados() != null) {
				paqueteSala.eliminarUsuario(cliente.getPaqueteUsuario().getUsername());
				for (String cad : paqueteSala.getUsuariosConectados()) {
					modelo.addElement(cad);
				}
				cliente.getSalasActivas().get(paqueteSala.getNombreSala()).cambiarModelo(modelo);
			}
		}
	}

	public void actualizarLista() throws FileNotFoundException {
		DefaultListModel<String> modelo = new DefaultListModel<String>();
//		ArrayList<String> modeloFotos = new ArrayList<String>();
		
		synchronized (cliente) {

			VentanaPrincipal.eliminarConectados();

			if (cliente.getPaqueteUsuario().getListaDeConectados() != null) {
				cliente.getPaqueteUsuario().eliminarUsuario(cliente.getPaqueteUsuario().getUsername());
				
				for (String cad : cliente.getPaqueteUsuario().getListaDeConectados()) {
					modelo.addElement(cad);
				}
//				modeloFotos = cliente.getPaqueteUsuario().getListaFotosConectados();
				
				VentanaPrincipal.cambiarModelo(modelo);
//				VentanaPrincipal.cambiarModeloFotos(modeloFotos);
			}
		}
	}

	public void actualizarListaSalas() {
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		synchronized (cliente) {
			
			VentanaPrincipal.eliminarSalas();
			
			if (cliente.getPaqueteUsuario().getListaDeSalas() != null) {
				for (String cad : cliente.getPaqueteUsuario().getListaDeSalas()) {
					modelo.addElement(cad);
				}
				
				VentanaPrincipal.cambiarModeloSalas(modelo);
			}
		}
	}
	
	public void actualizarFotoPerfil() {
		synchronized (cliente) {
			if(cliente.getPaqueteUsuario().getFotoPerfil() != null) {
				VentanaPrincipal.ponerFotoEnLabel();
			}
		}
	}
}