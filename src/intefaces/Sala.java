package intefaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class Sala extends JFrame {

	private JPanel contentPane;
	private JTextField texto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sala frame = new Sala();
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
	public Sala() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.GRAY);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 170, 171);
		contentPane.add(scrollPane);
		
		JList listaUsuariosSala = new JList();
		listaUsuariosSala.setForeground(Color.WHITE);
		listaUsuariosSala.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(listaUsuariosSala);
		
		texto = new JTextField();
		texto.setBounds(194, 209, 320, 41);
		texto.setForeground(Color.WHITE);
		texto.setBackground(Color.DARK_GRAY);
		texto.setCaretColor(Color.WHITE);
		contentPane.add(texto);
		texto.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(518, 209, 97, 41);
		contentPane.add(btnEnviar);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(194, 11, 421, 194);
		contentPane.add(scrollPane_1);
		
		JTextPane chat = new JTextPane();
		chat.setForeground(Color.WHITE);
		chat.setBackground(Color.DARK_GRAY);
		scrollPane_1.setViewportView(chat);
		
		JButton btnDesconectarse = new JButton("Salir de la Sala");
		btnDesconectarse.setBounds(10, 209, 170, 41);
		contentPane.add(btnDesconectarse);
		
		JLabel lblUsuario = new JLabel("Usuario ");
		lblUsuario.setBounds(10, 11, 46, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblNombreUsuario = new JLabel("");
		lblNombreUsuario.setBounds(57, 11, 123, 14);
		contentPane.add(lblNombreUsuario);
	}
}
