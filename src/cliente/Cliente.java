package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import comandosEscuchaServer.ComandoCliente;
import intefaces.Chat;
import intefaces.MenuInicio;
import intefaces.Sala;
import intefaces.VentanaPrincipal;
import paqueteEnvios.Paquete;
import paqueteEnvios.PaqueteDeUsuariosYSalas;
import paqueteEnvios.PaqueteMencion;
import paqueteEnvios.PaqueteMensaje;
import paqueteEnvios.Comando;
import paqueteEnvios.PaqueteMensajeSala;
import paqueteEnvios.PaqueteSala;
import paqueteEnvios.PaqueteUsuario;

public class Cliente extends Thread {
	private Socket cliente;
	private static String miIp;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private PaqueteUsuario paqueteUsuario = new PaqueteUsuario();
	private PaqueteMensaje paqueteMensaje = new PaqueteMensaje();
	

	private PaqueteMensajeSala paqueteMensajeSala = new PaqueteMensajeSala();
	private PaqueteMencion paqueteMencion = new PaqueteMencion();
	private Map<String, Chat> chatsActivos = new HashMap<>();
	private Map<String, Sala> salasActivas = new HashMap<>();
	private VentanaPrincipal chat;

	private PaqueteSala paqueteSala = new PaqueteSala();

	private int accion; 

	private final Gson gson = new Gson();

	private String ip;
	private int puerto;
	public Cliente(String newIp, int newPort) {

		this.ip = newIp;
		this.puerto = newPort;

		try {
			cliente = new Socket(ip, puerto);
			miIp = cliente.getInetAddress().getHostAddress(); 
			entrada = new ObjectInputStream(cliente.getInputStream()); //objeto recibido del server
			salida = new ObjectOutputStream(cliente.getOutputStream()); //objeto a enviar al server
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Error al iniciar la app, chequee la conexión al Server" );
			System.exit(1);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		synchronized (this) {
			try {
				new MenuInicio(this).setVisible(true);

				this.wait();

				ComandoCliente comando;
				Paquete paquete = new Paquete();
				
				EscuchaServer es = new EscuchaServer(this);
				es.start();
				
				while (true) {
					paquete.setComando(accion);
					comando = (ComandoCliente) paquete.getObjeto("comandosCliente");
					comando.setCliente(this);
					comando.ejecutar();

					salida.flush();
					
					this.wait();

				}

			} catch (IOException | InterruptedException  e) {
				JOptionPane.showMessageDialog(null, "Fallo la conexión del Cliente.");
				e.printStackTrace();
				System.exit(1);
			} 
		}


	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

	public int getAccion() {
		return accion;
	}

	public Socket getSocket() {
		return cliente;
	}

	public void setSocket(final Socket cliente) {
		this.cliente = cliente;
	}

	public static String getMiIp() {
		return miIp;
	}

	public void setMiIp(final String miIp) {
		this.miIp = miIp;
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(final ObjectInputStream entrada) {
		this.entrada = entrada;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

	public void setSalida(final ObjectOutputStream salida) {
		this.salida = salida;
	}

	public PaqueteUsuario getPaqueteUsuario() {
		return paqueteUsuario;
	}

	public PaqueteMensaje getPaqueteMensaje() {
		return paqueteMensaje;
	}

	public void setPaqueteMensaje(PaqueteMensaje fromJson) {
		this.paqueteMensaje.setMsj(fromJson.getMsj());
		this.paqueteMensaje.setUserEmisor(fromJson.getUserEmisor());
		this.paqueteMensaje.setUserReceptor(fromJson.getUserReceptor());
	}

	public Map<String, Chat> getChatsActivos() {
		return chatsActivos;
	}

	public Map<String, Sala> getSalasActivas() {
		return salasActivas;
	}

	public void setChatsActivos(Map<String, Chat> chatsActivos) {
		this.chatsActivos = chatsActivos;
	}

	public PaqueteSala getPaqueteSala() {
		return paqueteSala;
	}

	public void setPaqueteSala(PaqueteSala paqueteSala) {
		this.paqueteSala = paqueteSala;
	}

	public PaqueteMensajeSala getPaqueteMensajeSala() {
		return paqueteMensajeSala;
	}

	public void setPaqueteMensajeSala(PaqueteMensajeSala paqueteMensajeSala) {
		this.paqueteMensajeSala.setMsj(paqueteMensajeSala.getMsj());
		this.paqueteMensajeSala.setUserEmisor(paqueteMensajeSala.getUserEmisor());
	}

	public PaqueteMencion getPaqueteMencion() {
		return paqueteMencion;
	}

	public void setPaqueteMencion(PaqueteMencion paqueteMencion) {
		this.paqueteMencion.setNombreSala(paqueteMencion.getNombreSala());
		this.paqueteMencion.setUserEmisor(paqueteMencion.getUserEmisor());
		this.paqueteMencion.setUserReceptor(paqueteMencion.getUserReceptor());
		this.paqueteMencion.setMsj(paqueteMencion.getMsj());
	}
	
	public VentanaPrincipal getChat() {
		return chat;
	}

	public void setChat(VentanaPrincipal chat) {
		this.chat = chat;
	}

}