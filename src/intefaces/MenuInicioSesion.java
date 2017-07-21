package intefaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.*;
import paqueteEnvios.Comando;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLayeredPane;

public class MenuInicioSesion extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	public MenuInicioSesion(final Cliente cliente) {


		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				synchronized(cliente){
					cliente.setAccion(Comando.DESCONECTAR);
					cliente.notify();
				}
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				System.exit(0);
			}
		});

		setTitle("Iniciar Sesion");
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.GRAY);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 444, 271);
		contentPane.add(layeredPane);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(111, 118, 68, 21);
		layeredPane.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setForeground(Color.BLACK);

		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(111, 66, 55, 23);
		layeredPane.add(lblNewLabel, new Integer(2));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));

		textField = new JTextField();
		textField.setBounds(198, 69, 118, 20);
		layeredPane.add(textField, new Integer(1));
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().equals("") && !passwordField.getText().equals("")){
					synchronized(cliente){
						cliente.setAccion(Comando.INICIOSESION);
						cliente.getPaqueteUsuario().setUsername(textField.getText());
						cliente.getPaqueteUsuario().setPassword(passwordField.getText());
						cliente.notify();
						dispose();
					}
				}
			}
		});
		passwordField.setBounds(198, 119, 118, 20);
		layeredPane.add(passwordField, new Integer(1));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JButton btnConectar = new JButton("Ingresar");
		btnConectar.setBounds(141, 182, 153, 23);
		layeredPane.add(btnConectar, new Integer(1));
		btnConectar.setFocusable(false);
		btnConectar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().equals("") && !passwordField.getText().equals("")){
					synchronized(cliente){
						cliente.setAccion(Comando.INICIOSESION);
						cliente.getPaqueteUsuario().setUsername(textField.getText());
						cliente.getPaqueteUsuario().setPassword(passwordField.getText());
						cliente.notify();
						dispose();
					}
				}
			}
		});
	}
}
