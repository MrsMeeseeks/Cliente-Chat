package intefaces;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import paqueteEnvios.Comando;
import paqueteEnvios.PaqueteMensaje;
import paqueteEnvios.PaqueteUsuario;

import java.awt.Font;
import javax.swing.JTextArea;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private String user = null;
	private Cliente cliente;
	private PaqueteUsuario paqueteUsuario;
	private static int cantUsuariosCon;
	
	private ArrayList<Sala> salasDisponibles = new ArrayList<>();
	private Map<String,Sala> mapaSalas = new HashMap<>();

	private JPanel contentPane;
	private DefaultListModel<String> modelo = new DefaultListModel<String>();
	private DefaultListModel<String> modeloSalas = new DefaultListModel<String>();

	private static JList<String> listaUsuariosChatGeneral = new JList<String>();
	private static JList<String> listaSalas = new JList<String>();


	private static JTextArea chat;
	private static JButton enviarATodos;
	private JLabel labelNombreUsuario;

	private JTextField texto;


	public VentanaPrincipal(Cliente cli) {


		cliente = cli;
		user = cliente.getPaqueteUsuario().getUsername();
		setTitle("Ventana Principal");
		setResizable(false);
		setBounds(100, 100, 673, 537);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (abrirVentanaConfirmaSalir()) {
					synchronized (cliente) {
						cliente.setAccion(Comando.DESCONECTAR);
						cliente.notify();
					}
					dispose();
				}
			}
		});

		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel labelUsuario = new JLabel("Mi Usuario: ");
		labelUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelUsuario.setForeground(Color.BLACK);
		labelUsuario.setBounds(22, 11, 66, 16);
		contentPane.add(labelUsuario);

		JScrollPane panelListaUsuarios = new JScrollPane();
		panelListaUsuarios.setBounds(18, 56, 171, 228);
		contentPane.add(panelListaUsuarios);
		panelListaUsuarios.setViewportView(listaUsuariosChatGeneral);
		listaUsuariosChatGeneral.setForeground(Color.WHITE);
		listaUsuariosChatGeneral.setBackground(Color.DARK_GRAY);

		listaUsuariosChatGeneral.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					if (listaUsuariosChatGeneral.getSelectedValue() != null) {
						if (!cliente.getChatsActivos().containsKey(listaUsuariosChatGeneral.getSelectedValue())) {
							if (cliente != null) {
								Chat chat = new Chat(cliente);
								cliente.getChatsActivos().put(listaUsuariosChatGeneral.getSelectedValue(), chat);
								chat.setTitle(listaUsuariosChatGeneral.getSelectedValue());
								chat.setVisible(true);
							}
						}
					}
				}
			}
		});

		listaUsuariosChatGeneral.setModel(modelo);

		JScrollPane panelListaSalas = new JScrollPane();
		panelListaSalas.setBounds(18, 329, 171, 117);
		contentPane.add(panelListaSalas);
		panelListaSalas.setViewportView(listaSalas);
		listaSalas.setForeground(Color.WHITE);
		listaSalas.setBackground(Color.DARK_GRAY);



		listaSalas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					if (listaSalas.getSelectedValue() != null) {
						if (!cliente.getSalasActivas().containsKey(listaSalas.getSelectedValue())) {
							cliente.getPaqueteSala().setNombreSala(listaSalas.getSelectedValue());
							cliente.getPaqueteSala().setCliente(cliente.getPaqueteUsuario().getUsername());
							synchronized (cliente) {
								cliente.setAccion(Comando.ENTRARSALA);
								cliente.notify();
							}
						} else {
							JOptionPane.showMessageDialog(null, "Ya se encuentra en esta sala.");
						}
					}
				}
			}
		});
		listaSalas.setModel(modeloSalas);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(209, 11, 443, 436);
		contentPane.add(scrollPane_1);

		chat = new JTextArea();
		chat.setEnabled(true);
		chat.setEditable(false);
		chat.setForeground(Color.WHITE);
		chat.setBackground(Color.DARK_GRAY);
		chat.setColumns(30);
		chat.setLineWrap(true);
		scrollPane_1.setViewportView(chat);

		texto = new JTextField();
		texto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!texto.getText().equals("") && !texto.getText().startsWith("@")) {

					chat.append(user + " : " + texto.getText() + "\n");

					cliente.setAccion(Comando.CHATALL);
					PaqueteMensaje paqueteMsj = new PaqueteMensaje(cliente.getPaqueteUsuario().getUsername(),null,texto.getText(),null);
					cliente.setPaqueteMensaje(paqueteMsj);
					texto.setText("");
					texto.requestFocus();
					synchronized (cliente) {
						cliente.notify();
					}
				}
				else if(!texto.getText().equals("")){
					String[] words;
					words = texto.getText().substring(1).split(" ", 2);
					if (words.length > 1 && words[1] != null) {
						words[1] = words[1].trim();
					}
					if(cliente.getPaqueteUsuario().getListaDeConectados().contains(words[0]) && words[0]!=user){
						cliente.setAccion(Comando.MP);
						PaqueteMensaje paqueteMsj = new PaqueteMensaje(cliente.getPaqueteUsuario().getUsername(),words[0],words[1],null);
						cliente.setPaqueteMensaje(paqueteMsj);

						if(cliente.getChatsActivos().containsKey(words[0])){
							cliente.getChatsActivos().get(words[0]).getChat()
							.append(user + ": "
									+ words[1] + "\n");
							cliente.getChatsActivos().get(words[0]).getTexto().grabFocus();
						} else {
							Chat chatPropio = new Chat(cliente);

							chatPropio.setTitle(words[0]);
							chatPropio.setVisible(true);

							cliente.getChatsActivos().put(words[0], chatPropio);
							chatPropio.getChat().append(user + ": "
									+ words[1] + "\n");
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

		texto.setEditable(true);
		texto.setForeground(Color.WHITE);
		texto.setBackground(Color.DARK_GRAY);
		texto.setCaretColor(Color.WHITE);
		texto.setBounds(209, 458, 302, 39);
		contentPane.add(texto);
		texto.setColumns(10);

		enviarATodos = new JButton("Enviar a Todos");
		enviarATodos.setEnabled(true);
		enviarATodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!texto.getText().equals("") && !texto.getText().startsWith("@")) {
					chat.append(user + ": " + texto.getText() + "\n");

					cliente.setAccion(Comando.CHATALL);
					cliente.getPaqueteMensaje().setUserEmisor(cliente.getPaqueteUsuario().getUsername());
					cliente.getPaqueteMensaje().setUserReceptor(getTitle());
					cliente.getPaqueteMensaje().setMsj(texto.getText());
					synchronized (cliente) {
						cliente.notify();
					}
					texto.setText("");

					texto.requestFocus();
				}
				else if(!texto.getText().equals("")){
					String[] words;
					words = texto.getText().substring(1).split(" ", 2);
					if (words.length > 1 && words[1] != null) {
						words[1] = words[1].trim();
					}
					if(cliente.getPaqueteUsuario().getListaDeConectados().contains(words[0]) && words[0]!=user){
						cliente.setAccion(Comando.MP);
						PaqueteMensaje paqueteMsj = new PaqueteMensaje(cliente.getPaqueteUsuario().getUsername(),words[0],words[1],null);
						cliente.setPaqueteMensaje(paqueteMsj);

						if(cliente.getChatsActivos().containsKey(words[0])){
							cliente.getChatsActivos().get(words[0]).getChat()
							.append(user + ": "
									+ words[1] + "\n");
							cliente.getChatsActivos().get(words[0]).getTexto().grabFocus();
						} else {
							Chat chatPropio = new Chat(cliente);

							chatPropio.setTitle(words[0]);
							chatPropio.setVisible(true);

							cliente.getChatsActivos().put(words[0], chatPropio);
							chatPropio.getChat().append(user + ": "
									+ words[1] + "\n");
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
		enviarATodos.setBounds(521, 458, 131, 39);
		contentPane.add(enviarATodos);




		labelNombreUsuario = new JLabel(user);
		labelNombreUsuario.setForeground(Color.BLACK);
		labelNombreUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelNombreUsuario.setBounds(86, 11, 103, 16);
		contentPane.add(labelNombreUsuario);

		JLabel labelUsuariosConectados = new JLabel("Usuarios Online");
		labelUsuariosConectados.setForeground(Color.BLACK);
		labelUsuariosConectados.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelUsuariosConectados.setBounds(22, 38, 111, 16);
		contentPane.add(labelUsuariosConectados);

		JLabel labelSalas = new JLabel("Salas Disponibles");
		labelSalas.setForeground(Color.BLACK);
		labelSalas.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelSalas.setBounds(22, 306, 111, 16);
		contentPane.add(labelSalas);

		JButton btnCrearSala = new JButton("Crear Sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuCreacionSala(cliente).setVisible(true);
			}
		});
		btnCrearSala.setEnabled(true);
		btnCrearSala.setBounds(18, 452, 171, 16);
		contentPane.add(btnCrearSala);

		JButton btnNewButton = new JButton("Eliminar Sala");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listaSalas.getSelectedValue()!=null) {
					if (abrirVentanaConfirmaEliminarSalaChat()) {
						synchronized (cliente) {
							cliente.getPaqueteSala().setNombreSala(listaSalas.getSelectedValue());
							cliente.getPaqueteSala().setCliente(cliente.getPaqueteUsuario().getUsername());
							cliente.setAccion(Comando.ELIMINARSALA);
							cliente.notify();
						}
					} 
				} else {
					JOptionPane.showMessageDialog(null, "Primero seleccione la sala que quiere eliminar.");
				}

			}
		});
		btnNewButton.setBounds(18, 473, 171, 16);
		contentPane.add(btnNewButton);
		setVisible(true);
	}

	private boolean abrirVentanaConfirmaSalir() {
		int opcion = JOptionPane.showConfirmDialog(this, "¿Desea salir del Chat?", "Confirmación",
				JOptionPane.YES_NO_OPTION);
		if (opcion == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}	


	private boolean abrirVentanaConfirmaEliminarSalaChat() {
		int opcion = JOptionPane.showConfirmDialog(this, "¿Desea Eliminar Sala De Chat?", "Confirmación",
				JOptionPane.YES_NO_OPTION);
		if (opcion == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}	

	public PaqueteUsuario getPaqueteUsuario() {
		return paqueteUsuario;
	}

	public static int getCantUsuariosCon() {
		return cantUsuariosCon;
	}

	public static JList<String> getListConectados() {
		return listaUsuariosChatGeneral;
	}

	public static JList<String> getListSalas() {
		return listaSalas;
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



	public ArrayList<Sala> getSalasDisponibles() {
		return salasDisponibles;
	}


	public void setSalasDisponibles(ArrayList<Sala> salasDisponibles) {
		this.salasDisponibles = salasDisponibles;
	}


	public Map<String,Sala> getMapaSalas() {
		return mapaSalas;
	}

	public void setMapaSalas(Map<String,Sala> mapaSalas) {
		this.mapaSalas = mapaSalas;
	}
}
