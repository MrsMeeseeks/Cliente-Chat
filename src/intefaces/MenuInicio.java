package intefaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.*;
import paqueteEnvios.Comando;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLayeredPane;

public class MenuInicio extends JFrame {

	private JPanel contentPane;

	public MenuInicio(final Cliente cliente) {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// En caso de cerrar la ventana
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				synchronized(cliente){
					cliente.setAccion(Comando.DESCONECTAR);
					cliente.notify();
				}
				dispose();
			}
		});
		
		// Propiedades de la ventana
		setTitle("Inicio");
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 444, 271);
		contentPane.add(layeredPane);
		
		JButton btnRegistrar = new JButton("Registrarse");
		btnRegistrar.setBounds(121, 162, 191, 23);
		layeredPane.add(btnRegistrar, new Integer(1));
		btnRegistrar.setFocusable(false);
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuRegistro menuRegistro = new MenuRegistro(cliente);
				menuRegistro.setVisible(true);
				dispose();
			}
		});
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setBounds(121, 92, 191, 23);
		layeredPane.add(btnIniciarSesion, new Integer(1));
		btnIniciarSesion.setFocusable(false);
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MenuInicioSesion menuInicioSesion = new MenuInicioSesion(cliente);
				menuInicioSesion.setVisible(true);
				dispose();
			}
		});
	}
}


