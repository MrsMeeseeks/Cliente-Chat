package intefaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import paqueteEnvios.Comando;

import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;

public class Chat extends JFrame {

	private JPanel contentPane;
	private JTextField texto;
	private JTextArea chat;
	private Cliente client;

	

	public Chat(final Cliente cliente) {
		this.client = cliente;
		setTitle("Mi Chat");

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 485, 315);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 11, 446, 218);
		contentPane.add(scrollPane);

		chat = new JTextArea();
		chat.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 15));
		chat.setForeground(Color.WHITE);
		chat.setBackground(Color.DARK_GRAY);
		chat.setEditable(false);
		scrollPane.setViewportView(chat);

		texto = new JTextField();
		texto.setForeground(Color.WHITE);
		texto.setCaretColor(Color.WHITE);
		texto.setBackground(new Color(64, 64, 64));
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				texto.requestFocus();
			}
		});


		texto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!texto.getText().equals("")) {
					chat.append(cliente.getPaqueteUsuario().getUsername() + ": " + texto.getText() + "\n");
					
					cliente.setAccion(Comando.TALK);

					cliente.getPaqueteMensaje().setUserEmisor(cliente.getPaqueteUsuario().getUsername());
					cliente.getPaqueteMensaje().setUserReceptor(getTitle());
					cliente.getPaqueteMensaje().setMsj(texto.getText());

					synchronized (cliente) {
						cliente.notify();
					}
					texto.setText("");
				}
				texto.requestFocus();
			}
		});


		JButton enviar = new JButton("Enviar");
		enviar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		enviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!texto.getText().equals("")) {
					chat.append(cliente.getPaqueteUsuario().getUsername() + ": " + texto.getText() + "\n");

					/*
					 * Para la aplicacion de las salas
					 * 
					if (getTitle() != "Sala") {
						cliente.setAccion(Comando.TALK);
					} else {
						cliente.setAccion(Comando.CHATALL);
					}
					*/
					
					cliente.setAccion(Comando.TALK);

					cliente.getPaqueteMensaje().setUserEmisor(cliente.getPaqueteUsuario().getUsername());
					cliente.getPaqueteMensaje().setUserReceptor(getTitle());
					cliente.getPaqueteMensaje().setMsj(texto.getText());

					synchronized (cliente) {
						cliente.notify();
					}
					texto.setText("");
				}
				texto.requestFocus();
			}
		});
		enviar.setBounds(357, 240, 88, 27);
		contentPane.add(enviar);


		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				mostrarVentanaConfirmacion();
			}
		});
		texto.setBounds(10, 240, 337, 27);
		contentPane.add(texto);
		texto.setColumns(10);
	}

	private void mostrarVentanaConfirmacion() {
		int res = JOptionPane.showConfirmDialog(this, "¿Desea salir de la sesión de chat?", "Confirmación",
				JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) {
			client.getChatsActivos().remove(getTitle());
			VentanaPrincipal.getEnviarATodosBut().setEnabled(true);
			dispose();
		}
	}

	public JTextArea getChat() {
		return chat;
	}

	public JTextField getTexto() {
		return texto;
	}
}
