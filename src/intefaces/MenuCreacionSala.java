package intefaces;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import paqueteEnvios.Comando;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class MenuCreacionSala extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldSala;
	private String name;
	private Cliente cliente;

	public MenuCreacionSala(Cliente cli) {
		this.cliente = cli;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		JButton btnCrearSala = new JButton("Crear Sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textFieldSala.getText().equals("")){
					name = textFieldSala.getText();
					//Sala sala = new Sala(name);
					cliente.getPaqueteSala().setName(name);
					cliente.setAccion(Comando.NEWSALA);
					synchronized (cliente) {
						cliente.notify();
					}
					dispose();
				}
			}
		});
		btnCrearSala.setBounds(132, 129, 154, 46);
		contentPane.add(btnCrearSala);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(132, 186, 154, 46);
		contentPane.add(btnSalir);
		
		JLabel lblNombreSala = new JLabel("Nombre de la Sala");
		//lblNombreSala.setHorizontalAlignment();
		lblNombreSala.setBounds(132, 22, 154, 37);
		contentPane.add(lblNombreSala);
		
		textFieldSala = new JTextField();
		textFieldSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textFieldSala.getText().equals("")){
					name = textFieldSala.getText();
					cliente.getPaqueteSala().setName(name);
					cliente.getPaqueteUsuario().setComando(Comando.NEWSALA);
					cliente.notify();
					dispose();
				}
			}
		});
		textFieldSala.setBounds(132, 70, 154, 30);
		contentPane.add(textFieldSala);
		textFieldSala.setColumns(10);
	}
}
