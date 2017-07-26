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

	private int accion; //accion que realiza el usuario

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
				// Creo el paquete que le voy a enviar al servidor
				paqueteUsuario = new PaqueteUsuario();
				new MenuInicio(this).setVisible(true);

				// Espero a que el usuario seleccione alguna accion
				this.wait();

				while (!paqueteUsuario.isInicioSesion()) {

					switch (getAccion()) {

					case Comando.INICIOSESION:
						paqueteUsuario.setComando(Comando.INICIOSESION);
						// Le envio el paquete al servidor
						salida.writeObject(gson.toJson(paqueteUsuario));
						break;

					case Comando.MENCIONSALA:
						paqueteMencion.setComando(Comando.MENCIONSALA);
						// Le envio el paquete al servidor
						salida.writeObject(gson.toJson(paqueteMencion));
						break;

					case Comando.NEWSALA:
						paqueteSala.setComando(Comando.NEWSALA);
						salida.writeObject(gson.toJson(paqueteSala));
						break;

					case Comando.ELIMINARSALA:
						paqueteSala.setComando(Comando.ELIMINARSALA);
						salida.writeObject(gson.toJson(paqueteSala));
						break;
						
					case Comando.TALK:
						paqueteMensaje.setComando(Comando.TALK);
						// Le envio el paquete al servidor
						salida.writeObject(gson.toJson(paqueteMensaje));

						break;

					case Comando.CHATSALA:
						paqueteMensajeSala.setComando(Comando.CHATSALA);
						// Le envio el paquete al servidor
						salida.writeObject(gson.toJson(paqueteMensajeSala));
						break;	

					case Comando.CHATALL:
						paqueteMensaje.setComando(Comando.CHATALL);
						// Le envio el paquete al servidor
						salida.writeObject(gson.toJson(paqueteMensaje));
						break;

					case Comando.DESCONECTAR:
						paqueteUsuario.setIp(getMiIp());
						paqueteUsuario.setComando(Comando.DESCONECTAR);
						// Le envio el paquete al servidor
						salida.writeObject(gson.toJson(paqueteUsuario));
						break;

					case Comando.MP:
						paqueteMensaje.setComando(Comando.MP);
						salida.writeObject(gson.toJson(paqueteMensaje));
						break;

					case Comando.REGISTRO:
						paqueteUsuario.setComando(Comando.REGISTRO);
						salida.writeObject(gson.toJson(paqueteUsuario));
						break;

					case Comando.ENTRARSALA:
						paqueteSala.setComando(Comando.ENTRARSALA);
						salida.writeObject(gson.toJson(paqueteSala));
						break;
						
					case Comando.DESCONECTARDESALA:
						paqueteSala.setComando(Comando.DESCONECTARDESALA);
						salida.writeObject(gson.toJson(paqueteSala));
						break;
					default:
						break;
					}

					salida.flush();


					if (getAccion() == Comando.DESCONECTAR || getAccion() == Comando.INICIOSESION || getAccion() ==Comando.REGISTRO) {
						// Recibo el paquete desde el servidor
						String cadenaLeida = (String) entrada.readObject();
						Paquete paquete = gson.fromJson(cadenaLeida, Paquete.class);
						switch (paquete.getComando()) {

						case Comando.REGISTRO:
							paqueteUsuario.setMsj(paquete.getMsj());
							if (paquete.getMsj().equals(Paquete.msjExito)) {

								JOptionPane.showMessageDialog(null, "Registro exitoso.");
								paqueteUsuario.setMsj(paquete.getMsj());
								ArrayList<String> salas = (ArrayList<String>) gson.fromJson(cadenaLeida, PaqueteDeUsuariosYSalas.class)
										.getSalas();
								paqueteUsuario.setListaDeSalas(salas);
								chat = new VentanaPrincipal(this);
								chat.run();
							} else {
								if (paquete.getMsj().equals(Paquete.msjFracaso))
									JOptionPane.showMessageDialog(null, "No se pudo registrar.");
								new MenuInicio(this).setVisible(true);
								// El usuario no pudo iniciar sesi�n
								paqueteUsuario.setInicioSesion(false);
							}
							break;

						case Comando.INICIOSESION:
							paqueteUsuario.setMsj(paquete.getMsj());
							ArrayList<String> salas = (ArrayList<String>) gson.fromJson(cadenaLeida, PaqueteDeUsuariosYSalas.class)
									.getSalas();
							paqueteUsuario.setListaDeSalas(salas);
							if (paquete.getMsj().equals(Paquete.msjExito)) {

								chat = new VentanaPrincipal(this);
								chat.run();
							} else {
								JOptionPane.showMessageDialog(null,
											"Error al iniciar sesión. Revise el usuario y la contraseña");
								this.paqueteUsuario.setUsername(null);
								new MenuInicio(this).setVisible(true);
							}
							break;

						case Comando.DESCONECTAR:
							// El usuario no pudo iniciar sesi�n
							paqueteUsuario.setInicioSesion(false);
							salida.writeObject(gson.toJson(new Paquete(Comando.DESCONECTAR), Paquete.class));
							cliente.close();
							break;

						default:
							break;
						}
					}
					this.wait();

				}

				paqueteUsuario.setIp(miIp);

			} catch (IOException | InterruptedException | ClassNotFoundException  e) {
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

}