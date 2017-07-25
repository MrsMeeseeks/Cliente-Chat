package intefaces;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import paqueteEnvios.Comando;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JButton;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Sala extends JFrame  {

	private JPanel contentPane;
	private JTextField texto;

	private String ownerSala;

	private JLabel lblNombreUsuario;
	private JTextArea chat;

	private String nombreSala;

	private static JList<String> listaConectadosSala = new JList<String>();

	public Sala(Cliente cli) {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				dispose();
			}
		});
		
		this.nombreSala = cli.getPaqueteSala().getNombreSala();
		this.ownerSala = cli.getPaqueteSala().getOwnerSala();
		setTitle(nombreSala);
		setResizable(false);
		setBounds(100, 100, 646, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.GRAY);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPaneChat = new JScrollPane();
		scrollPaneChat.setBounds(194, 11, 421, 194);
		contentPane.add(scrollPaneChat);


		chat = new JTextArea();
		chat.setForeground(Color.WHITE);
		chat.setBackground(Color.DARK_GRAY);
		chat.setText(cli.getPaqueteSala().getHistorial());
		chat.setEnabled(false);
		chat.setEditable(false);
		scrollPaneChat.setViewportView(chat);

		JScrollPane scrollPaneConectados = new JScrollPane();
		scrollPaneConectados.setBounds(10, 33, 170, 171);
		contentPane.add(scrollPaneConectados);
		scrollPaneConectados.setViewportView(listaConectadosSala);
		listaConectadosSala.setForeground(Color.WHITE);
		listaConectadosSala.setBackground(Color.DARK_GRAY);
		listaConectadosSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					if (listaConectadosSala.getSelectedValue() != null) {
						if (!cli.getChatsActivos().containsKey(listaConectadosSala.getSelectedValue())) {
							if (cli != null) {
								Chat chat = new Chat(cli);
								cli.getChatsActivos().put(listaConectadosSala.getSelectedValue(), chat);
								chat.setTitle(listaConectadosSala.getSelectedValue());
								chat.setVisible(true);
							}
						}
					}
				}
			}
		});


		texto = new JTextField();
		texto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!texto.getText().equals("") && !texto.getText().startsWith("@") && !texto.getText().contains(" @")) {

					chat.append(cli.getPaqueteUsuario().getUsername() + ": " + texto.getText() + "\n");

					cli.setAccion(Comando.CHATSALA);
					cli.getPaqueteMensajeSala().setUserEmisor(cli.getPaqueteUsuario().getUsername());
					cli.getPaqueteMensajeSala().setMsj(texto.getText());
					cli.getPaqueteMensajeSala().setNombreSala(nombreSala);

					texto.setText("");
					texto.requestFocus();
					synchronized (cli) {
						cli.notify();
					}
				}
				else if(!texto.getText().equals("") && texto.getText().startsWith("@")){
					String[] words;
					words = texto.getText().substring(1).split(" ", 2);
					if (words.length > 1 && words[1] != null) {
						words[1] = words[1].trim();
					}
					if(cli.getPaqueteUsuario().getListaDeConectados().contains(words[0]) && words[0]!=cli.getPaqueteUsuario().getUsername() && words.length > 1 && words[1]!=null){
						//chat.append(cli.getPaqueteUsuario().getUsername() + " --> " + words[0] +":" + words[1] + "\n");
						cli.setAccion(Comando.MP);
						cli.getPaqueteMensaje().setUserEmisor(cli.getPaqueteUsuario().getUsername());
						cli.getPaqueteMensaje().setUserReceptor(words[0]);
						cli.getPaqueteMensaje().setMsj(words[1]);

						if(cli.getChatsActivos().containsKey(cli.getPaqueteMensaje().getUserReceptor())){
							cli.getChatsActivos().get(cli.getPaqueteMensaje().getUserReceptor()).getChat()
							.append(cli.getPaqueteUsuario().getUsername() + ": "
									+ cli.getPaqueteMensaje().getMsj() + "\n");
							cli.getChatsActivos().get(cli.getPaqueteMensaje().getUserReceptor()).getTexto().grabFocus();
						}
						else if(words[0]!=cli.getPaqueteUsuario().getUsername() && !words[1].equals(""))
						{
							Chat chatPropio = new Chat(cli);

							chatPropio.setTitle(cli.getPaqueteMensaje().getUserReceptor());
							chatPropio.setVisible(true);

							cli.getChatsActivos().put(cli.getPaqueteMensaje().getUserReceptor(), chatPropio);
							chatPropio.getChat().append(cli.getPaqueteUsuario().getUsername() + ": "
									+ cli.getPaqueteMensaje().getMsj() + "\n");
						}
						synchronized (cli) {
							cli.notify();
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
				} else if(!texto.getText().equals("")){

					chat.append(cli.getPaqueteUsuario().getUsername() + ": " + texto.getText() + "\n");

					String mensaje;
					String[] mensajeArray;
					mensaje = texto.getText();
					int pos = mensaje.indexOf("@") + 1;
					mensajeArray = mensaje.substring(pos).split(" ", 2);
					if (mensajeArray.length > 1 && mensajeArray[1] != null) {
						mensajeArray[1] = mensajeArray[1].trim();
					}


					cli.setAccion(Comando.MENCIONSALA);
					cli.getPaqueteMencion().setUserEmisor(cli.getPaqueteUsuario().getUsername());
					cli.getPaqueteMencion().setUserReceptor(mensajeArray[0]);
					cli.getPaqueteMencion().setNombreSala(nombreSala);
					cli.getPaqueteMencion().setMsj(texto.getText());

					texto.setText("");
					texto.requestFocus();
					synchronized (cli) {
						cli.notify();
					}

				}
			}
		});

		texto.setBounds(194, 209, 320, 41);
		texto.setForeground(Color.WHITE);
		texto.setBackground(Color.DARK_GRAY);
		texto.setCaretColor(Color.WHITE);
		contentPane.add(texto);
		texto.setColumns(10);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!texto.getText().equals("") && !texto.getText().startsWith("@") && !texto.getText().contains(" @")) {

					chat.append(cli.getPaqueteUsuario().getUsername() + ": " + texto.getText() + "\n");

					cli.setAccion(Comando.CHATSALA);
					cli.getPaqueteMensajeSala().setUserEmisor(cli.getPaqueteUsuario().getUsername());
					cli.getPaqueteMensajeSala().setMsj(texto.getText());
					cli.getPaqueteMensajeSala().setNombreSala(nombreSala);

					texto.setText("");
					texto.requestFocus();
					synchronized (cli) {
						cli.notify();
					}
				}
				else if(!texto.getText().equals("") && texto.getText().startsWith("@")){
					String[] words;
					words = texto.getText().substring(1).split(" ", 2);
					if (words.length > 1 && words[1] != null) {
						words[1] = words[1].trim();
					}
					if(cli.getPaqueteUsuario().getListaDeConectados().contains(words[0]) && words[0]!=cli.getPaqueteUsuario().getUsername() && words.length > 1 && words[1]!=null){
						//chat.append(cli.getPaqueteUsuario().getUsername() + " --> " + words[0] +":" + words[1] + "\n");
						cli.setAccion(Comando.MP);
						cli.getPaqueteMensaje().setUserEmisor(cli.getPaqueteUsuario().getUsername());
						cli.getPaqueteMensaje().setUserReceptor(words[0]);
						cli.getPaqueteMensaje().setMsj(words[1]);

						if(cli.getChatsActivos().containsKey(cli.getPaqueteMensaje().getUserReceptor())){
							cli.getChatsActivos().get(cli.getPaqueteMensaje().getUserReceptor()).getChat()
							.append(cli.getPaqueteUsuario().getUsername() + ": "
									+ cli.getPaqueteMensaje().getMsj() + "\n");
							cli.getChatsActivos().get(cli.getPaqueteMensaje().getUserReceptor()).getTexto().grabFocus();
						}
						else if(words[0]!=cli.getPaqueteUsuario().getUsername() && !words[1].equals(""))
						{
							Chat chatPropio = new Chat(cli);

							chatPropio.setTitle(cli.getPaqueteMensaje().getUserReceptor());
							chatPropio.setVisible(true);

							cli.getChatsActivos().put(cli.getPaqueteMensaje().getUserReceptor(), chatPropio);
							chatPropio.getChat().append(cli.getPaqueteUsuario().getUsername() + ": "
									+ cli.getPaqueteMensaje().getMsj() + "\n");
						}
						synchronized (cli) {
							cli.notify();
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
				} else if(!texto.getText().equals("")){

					chat.append(cli.getPaqueteUsuario().getUsername() + ": " + texto.getText() + "\n");

					String mensaje;
					String[] mensajeArray;
					mensaje = texto.getText();
					int pos = mensaje.indexOf("@") + 1;
					mensajeArray = mensaje.substring(pos).split(" ", 2);
					if (mensajeArray.length > 1 && mensajeArray[1] != null) {
						mensajeArray[1] = mensajeArray[1].trim();
					}


					cli.setAccion(Comando.MENCIONSALA);
					cli.getPaqueteMencion().setUserEmisor(cli.getPaqueteUsuario().getUsername());
					cli.getPaqueteMencion().setUserReceptor(mensajeArray[0]);
					cli.getPaqueteMencion().setNombreSala(nombreSala);
					cli.getPaqueteMencion().setMsj(texto.getText());

					texto.setText("");
					texto.requestFocus();
					synchronized (cli) {
						cli.notify();
					}

				}
			}
		});
		btnEnviar.setBounds(518, 209, 97, 41);
		contentPane.add(btnEnviar);

		JButton btnDesconectarse = new JButton("Salir de la Sala");
		btnDesconectarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (abrirVentanaConfirmaSalir()) {
					if (cli != null) {
						synchronized (cli) {
							cli.setAccion(Comando.DESCONECTAR);
							cli.notify();
						}
						setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					}
					System.exit(0);
				}
			}
		});
		btnDesconectarse.setBounds(10, 209, 170, 41);
		contentPane.add(btnDesconectarse);

		JLabel lblUsuario = new JLabel("Usuario ");
		lblUsuario.setBounds(10, 11, 54, 14);
		contentPane.add(lblUsuario);

		lblNombreUsuario = new JLabel(cli.getPaqueteUsuario().getUsername());
		lblNombreUsuario.setBounds(57, 11, 123, 14);
		contentPane.add(lblNombreUsuario);
		setVisible(true);
	}

	public String getName() {
		return nombreSala;
	}

	public void setName(String name) {
		this.nombreSala = name;
	}

	public void setNombreUsuario(String nombre){
		this.lblNombreUsuario.setText(nombre);
	}

	public JList<String> getListaConectadosSala() {
		return listaConectadosSala;
	}
	public static void setListaConectadosSala(JList<String> listaConectadosSala) {
		Sala.listaConectadosSala = listaConectadosSala;
	}

	public JTextArea getChat() {
		return chat;
	}

	public void setChat(JTextArea chat) {
		this.chat = chat;
	}

	public JTextField getTexto() {
		return texto;
	}

	public void setTexto(JTextField texto) {
		this.texto = texto;
	}

	private boolean abrirVentanaConfirmaSalir() {
		int opcion = JOptionPane.showConfirmDialog(this, "¿Desea salir del Chat?", "Confirmación",
				JOptionPane.YES_NO_OPTION);
		if (opcion == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

	public String getOwnerSala() {
		return ownerSala;
	}

	public void setOwnerSala(String ownerSala) {
		this.ownerSala = ownerSala;
	}

	
}
