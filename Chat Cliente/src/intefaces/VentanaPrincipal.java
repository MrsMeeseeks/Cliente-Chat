package intefaces;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import cliente.EscuchaServer;
import paqueteEnvios.Comando;
import paqueteEnvios.Paquete;
import paqueteEnvios.PaqueteUsuario;

import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JTextArea;

public class VentanaPrincipal extends JFrame {
	private String user = null;
	private Cliente cliente, client;
	private PaqueteUsuario paqueteUsuario;
	private static int cantUsuariosCon;

	private JPanel contentPane;
	private DefaultListModel<String> modelo = new DefaultListModel<String>();
	private static JList<String> list = new JList<String>();
	private JTextField nombreUsuario;
	private static JButton botonChatGeneral;
	private JButton botonCon;
	private static JTextArea chat;
	private static JButton enviarATodos;

	private String ipScanned = "localhost";
	private int puertoScanned = 1234;
	private JTextField texto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 668, 335);
		setLocationRelativeTo(null);

		JTextField ip = new JTextField(5);
		JTextField puerto = new JTextField(5);

		ip.setText(ipScanned);
		puerto.setText(String.valueOf(puertoScanned));

		JPanel myPanel = new JPanel();

		myPanel.setLayout(new GridLayout(2, 2));
		myPanel.add(new JLabel("DIRECCION: "));
		myPanel.add(ip);
		myPanel.add(new JLabel("PUERTO: "));
		myPanel.add(puerto);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (abrirVentanaConfirmaSalir()) {
					if (cliente != null) {
						synchronized (cliente) {
							// NOTIFICA QUE SE DESCONECTO EL USUARIO
							cliente.setAccion(Comando.DESCONECTAR);
							cliente.notify();
						}
						setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					}
					System.exit(0);
				}
			}
		});

		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nombreUsuario = new JTextField();
		nombreUsuario.setForeground(Color.WHITE);
		nombreUsuario.setBackground(Color.DARK_GRAY);
		nombreUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		nombreUsuario.setEditable(false);
		nombreUsuario.setBounds(18, 59, 171, 22);
		contentPane.add(nombreUsuario);
		nombreUsuario.setColumns(10);

		botonCon = new JButton("Ingresar");
		botonCon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user == null) {
					int result = JOptionPane.showConfirmDialog(null, myPanel, "Complete estos campos... ",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						ipScanned = ip.getText();
						puertoScanned = Integer.valueOf(puerto.getText());
						InterfaceLogeo interfaceLogeo = new InterfaceLogeo();
						interfaceLogeo.setTitle("¡Loguearse!");
						interfaceLogeo.setVisible(true);
						interfaceLogeo.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								user = interfaceLogeo.getNombreUsuario();
								if (user != null) {
									cliente = new Cliente(ipScanned, puertoScanned);
									cliente.start();

									while (cliente.getState() != Thread.State.WAITING) {
									}
									logIn(cliente);
									EscuchaServer em = new EscuchaServer(cliente);
									em.start();

									synchronized (this) {
										try {
											this.wait(200);
										} catch (InterruptedException e1) {
											e1.printStackTrace();
										}
									}

									if (cliente.getPaqueteUsuario().getMensaje().equals(Paquete.msjExito)) {
										setTitle("Usuario: " + user);
										nombreUsuario.setText(user);
										refreshListCon(cliente);
										botonCon.setEnabled(false);
										enviarATodos.setEnabled(true);
										texto.setEditable(true);
										chat.setEnabled(true);
									} else {
										try {
											cliente.getSalida().close();
											cliente.getEntrada().close();
											cliente.getSocket().close();
											cliente.stop();
											user = null;
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									}
								}
							}
						});
					}
				}
			}
		});
		botonCon.setBounds(20, 9, 169, 23);
		contentPane.add(botonCon);

		JLabel lblUsuariosConectados = new JLabel("Usuarios Conectados:");
		lblUsuariosConectados.setBounds(20, 103, 138, 16);
		contentPane.add(lblUsuariosConectados);

		JLabel labelUsuario = new JLabel("Mi Usuario");
		labelUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelUsuario.setForeground(Color.BLACK);
		labelUsuario.setBounds(20, 44, 66, 16);
		contentPane.add(labelUsuario);
		list.setBounds(18, 119, 169, 149);
		contentPane.add(list);
		list.setForeground(Color.WHITE);
		list.setBackground(Color.DARK_GRAY);

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					if (list.getSelectedValue() != null) {
						if (!cliente.getChatsActivos().containsKey(list.getSelectedValue())) {
							if (cliente != null) {
								Chat chat = new Chat(cliente);
								cliente.getChatsActivos().put(list.getSelectedValue(), chat);
								chat.setTitle(list.getSelectedValue());
								chat.setVisible(true);
							}
						}
					}
				}
			}
		});

		list.setModel(modelo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 119, 169, 149);
		contentPane.add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(209, 11, 443, 241);
		contentPane.add(scrollPane_1);

		chat = new JTextArea();
		chat.setEnabled(false);
		chat.setEditable(false);
		chat.setForeground(Color.WHITE);
		chat.setBackground(Color.BLACK);
		scrollPane_1.setViewportView(chat);

		texto = new JTextField();
		texto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//manda el msj a todos
				if (!texto.getText().equals("") && !texto.getText().startsWith("@")) {
					chat.append("Yo :" + texto.getText() + "\n");

					// MANDO EL COMANDO PARA QUE ENVIE EL MSJ
					cliente.setAccion(Comando.CHATALL);

					cliente.getPaqueteMensaje().setUserEmisor(cliente.getPaqueteUsuario().getUsername());
					cliente.getPaqueteMensaje().setUserReceptor(getTitle());
					cliente.getPaqueteMensaje().setMensaje(texto.getText());

					synchronized (cliente) {
						cliente.notify();
					}
					texto.setText("");

					texto.requestFocus();
				}
				else
				{
					String[] words;
					words = texto.getText().substring(1).split(" ", 2);
					if (words.length > 1 && words[1] != null) {
						words[1] = words[1].trim();
					}
					if(cliente.getPaqueteUsuario().getListaDeConectados().contains(words[0]) && words[0]!=user){
						chat.append("Yo (Para "+ words[0] +") :" + words[1] + "\n");
						cliente.setAccion(Comando.MP);
						cliente.getPaqueteMensaje().setUserEmisor(cliente.getPaqueteUsuario().getUsername() );
						cliente.getPaqueteMensaje().setUserReceptor(words[0]);
						cliente.getPaqueteMensaje().setMensaje(words[1]);

						if(cliente.getChatsActivos().containsKey(cliente.getPaqueteMensaje().getUserReceptor())){
							cliente.getChatsActivos().get(cliente.getPaqueteMensaje().getUserReceptor()).getChat()
							.append("Yo " + ": "
									+ cliente.getPaqueteMensaje().getMensaje() + "\n");
							cliente.getChatsActivos().get(cliente.getPaqueteMensaje().getUserReceptor()).getTexto().grabFocus();
						}
						else
						{
							Chat chatPropio = new Chat(cliente);

							chatPropio.setTitle(cliente.getPaqueteMensaje().getUserReceptor());
							chatPropio.setVisible(true);

							cliente.getChatsActivos().put(cliente.getPaqueteMensaje().getUserReceptor(), chatPropio);
							chatPropio.getChat().append("Yo " + ": "
									+ cliente.getPaqueteMensaje().getMensaje() + "\n");
						}
						synchronized (cliente) {
							cliente.notify();
						}
						texto.setText("");

						texto.requestFocus();
					}
					else
					{
						chat.append("No Existe el usuario " + words[0] + "\n");

						texto.setText("");
						texto.requestFocus();
					}
				}
			}
		});

		texto.setEditable(false);
		texto.setForeground(Color.WHITE);
		texto.setBackground(Color.BLACK);
		texto.setBounds(209, 256, 302, 39);
		contentPane.add(texto);
		texto.setColumns(10);

		enviarATodos = new JButton("Enviar a Todos");
		enviarATodos.setEnabled(false);
		enviarATodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//manda el msj a todos
				if (!texto.getText().equals("") && !texto.getText().startsWith("@")) {
					chat.append("Yo :" + texto.getText() + "\n");

					// MANDO EL COMANDO PARA QUE ENVIE EL MSJ
					cliente.setAccion(Comando.CHATALL);

					cliente.getPaqueteMensaje().setUserEmisor(cliente.getPaqueteUsuario().getUsername());
					cliente.getPaqueteMensaje().setUserReceptor(getTitle());
					cliente.getPaqueteMensaje().setMensaje(texto.getText());

					synchronized (cliente) {
						cliente.notify();
					}
					texto.setText("");

					texto.requestFocus();
				}
				else
				{
					String[] words;
					words = texto.getText().substring(1).split(" ", 2);
					if (words.length > 1 && words[1] != null) {
						words[1] = words[1].trim();
					}
					if(cliente.getPaqueteUsuario().getListaDeConectados().contains(words[0]) && words[0]!=user){
						chat.append("Yo (Para "+ words[0] +") :" + words[1] + "\n");
						cliente.setAccion(Comando.MP);
						cliente.getPaqueteMensaje().setUserEmisor(cliente.getPaqueteUsuario().getUsername());
						cliente.getPaqueteMensaje().setUserReceptor(words[0]);
						cliente.getPaqueteMensaje().setMensaje(words[1]);

						synchronized (cliente) {
							cliente.notify();
						}
						texto.setText("");

						texto.requestFocus();
					}
					else
					{
						chat.append("No Existe el usuario " + words[0] + "\n");

						texto.setText("");
						texto.requestFocus();
					}
				}

			}

		});
		enviarATodos.setBounds(521, 256, 131, 39);
		contentPane.add(enviarATodos);
	}

	private boolean abrirVentanaConfirmaSalir() {
		int opcion = JOptionPane.showConfirmDialog(this, "¿Desea salir del Chat?", "Confirmación",
				JOptionPane.YES_NO_OPTION);
		if (opcion == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

	private void logIn(final Cliente cliente) {
		cliente.setAccion(Comando.INICIOSESION);
		cliente.getPaqueteUsuario().setUsername(user);
		synchronized (cliente) {
			cliente.notify();
		}
	}

	private void refreshListCon(final Cliente cliente) {
		if (cliente != null) {
			synchronized (cliente) {
				modelo.removeAllElements();
				if (cliente.getPaqueteUsuario().getListaDeConectados() != null) {
					cliente.getPaqueteUsuario().getListaDeConectados()
					.remove(cliente.getPaqueteUsuario().getUsername());
					for (String cad : cliente.getPaqueteUsuario().getListaDeConectados()) {
						modelo.addElement(cad);
					}
					cantUsuariosCon = (modelo.getSize());
					list.setModel(modelo);
				}
			}
			if(cantUsuariosCon == 0)
				enviarATodos.setEnabled(false);
			else
				enviarATodos.setEnabled(true);
		}
	}

	public PaqueteUsuario getPaqueteUsuario() {
		return paqueteUsuario;
	}

	public static int getCantUsuariosCon() {
		return cantUsuariosCon;
	}

	public static JList<String> getList() {
		return list;
	}

	public static void setCantUsuariosCon(int i) {
		cantUsuariosCon = i;
	}

	public static void setTextoChatGeneral (String name, String text) {
		chat.append(name + " : " + text + "\n");
	}

	public static JButton getEnviarATodosBut () {
		return enviarATodos;
	}

}
