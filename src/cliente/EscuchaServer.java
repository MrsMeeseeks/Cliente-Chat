package cliente;

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

	public void actualizarListaConectadosSala(PaqueteSala paqueteSala) {
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		synchronized (cliente) {
			try {
				cliente.wait(5);
				cliente.getSalasActivas().get(paqueteSala.getNombreSala())
				.getListaConectadosSala().removeAll();

				if (paqueteSala.getUsuariosConectados() != null) {
					paqueteSala.getUsuariosConectados()
					.remove(cliente.getPaqueteUsuario().getUsername());
					for (String cad : paqueteSala.getUsuariosConectados()) {
						modelo.addElement(cad);
					}
					cliente.getSalasActivas().get(paqueteSala.getNombreSala()).getListaConectadosSala().setModel(modelo);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void actualizarLista() {
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		synchronized (cliente) {
			try {
				cliente.wait(300);
				VentanaPrincipal.getListConectados().removeAll();
				if (cliente.getPaqueteUsuario().getListaDeConectados() != null) {
					cliente.getPaqueteUsuario().getListaDeConectados()
					.remove(cliente.getPaqueteUsuario().getUsername());
					for (String cad : cliente.getPaqueteUsuario().getListaDeConectados()) {
						modelo.addElement(cad);
					}
					VentanaPrincipal.setCantUsuariosCon(modelo.getSize());
					VentanaPrincipal.getListConectados().setModel(modelo);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void actualizarListaSalas() {
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		synchronized (cliente) {
			try {
				cliente.wait(300);
				VentanaPrincipal.getListSalas().removeAll();
				if (cliente.getPaqueteUsuario().getListaDeSalas() != null) {
					for (String cad : cliente.getPaqueteUsuario().getListaDeSalas()) {
						modelo.addElement(cad);
					}
					VentanaPrincipal.getListSalas().setModel(modelo);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

//	public static ArrayList<String> getUsuariosConectados() {
//		return usuariosConectados;
//	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}