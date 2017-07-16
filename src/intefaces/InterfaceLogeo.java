package intefaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;

public class InterfaceLogeo extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private String nombreUsuario;

	/**
	 * Create the frame.
	 */
	public InterfaceLogeo() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 348, 182);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnIniciar = new JButton("Iniciar Sesión");
		btnIniciar.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textField.getText().equals("")) {
					nombreUsuario = textField.getText();
				}
				dispose();
			}
		});

		btnIniciar.setBounds(185, 92, 116, 25);
		contentPane.add(btnIniciar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnCancelar.setBounds(36, 92, 108, 25);
		contentPane.add(btnCancelar);

		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textField.getText().equals("")) {
					nombreUsuario = textField.getText();
				}
				dispose();
			}
		});

		textField.setBounds(108, 51, 116, 22);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario");
		lblNombreDeUsuario.setForeground(Color.WHITE);
		lblNombreDeUsuario.setFont(new Font("Bodoni MT", Font.PLAIN, 14));
		lblNombreDeUsuario.setBounds(108, 24, 128, 16);
		contentPane.add(lblNombreDeUsuario);
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}
}
