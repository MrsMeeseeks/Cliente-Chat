package intefaces;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.peer.LabelPeer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import cliente.Cliente;
import paqueteEnvios.Comando;
import paqueteEnvios.PaqueteMensaje;
import paqueteEnvios.PaqueteSala;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JTextArea;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private String user = null;
	private Cliente cliente;

	private static JList<String> listaUsuariosChatGeneral = new JList<String>();
	private static JList<String> listaSalas = new JList<String>();

	private static JTextArea chat;
	
	//Ancho máximo
    public static int MAX_WIDTH=60;
    //Alto máximo
    public static int MAX_HEIGHT=60;

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

		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel labelUsuario = new JLabel("Mi Usuario: ");
		labelUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelUsuario.setForeground(Color.BLACK);
		labelUsuario.setBounds(22, 11, 66, 16);
		contentPane.add(labelUsuario);
		
		JFileChooser seleccionArchivo = new JFileChooser();
		//Creo un filtro por extensión
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
		seleccionArchivo.setFileFilter(filtro);
		
		JButton botonPerfil = new JButton("Foto");
		botonPerfil.setBounds(22, 31, 66, 16);
		botonPerfil.setActionCommand("abre");
		contentPane.add(botonPerfil);
		
		JLabel labelPerfil = new JLabel();
		
		JPanel panelPerfil = new JPanel();
		panelPerfil.setBounds(96, 11, MAX_WIDTH, MAX_HEIGHT);
		panelPerfil.add(labelPerfil);
		contentPane.add(panelPerfil);
		
		botonPerfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if("abre".equals(e.getActionCommand())) {
					int valorFileChoser = seleccionArchivo.showOpenDialog(null);
					if(valorFileChoser == JFileChooser.APPROVE_OPTION) {
						File archivoElegido = seleccionArchivo.getSelectedFile();
						String pathArchElegido = archivoElegido.getPath();
						try {
							BufferedImage bimage = loadImage(pathArchElegido);
//							ImageIcon iconoPerfil = new ImageIcon(
//									devolverImagenRedimencionada(bimage));
							ImageIcon iconoPerfil = new ImageIcon(
									bimage.getScaledInstance(MAX_WIDTH, MAX_HEIGHT, Image.SCALE_DEFAULT));
							labelPerfil.setIcon(iconoPerfil);
						}catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "Error al arbir la imágen.");
						}
					}
				}
			}
		});

		JScrollPane panelListaUsuarios = new JScrollPane();
		panelListaUsuarios.setBounds(18, 116, 171, 198);
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
							}
						}
					}
				}
			}
		});


		JScrollPane panelListaSalas = new JScrollPane();
		panelListaSalas.setBounds(18, 349, 171, 77);
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
							PaqueteSala paqueteSala = new PaqueteSala(listaSalas.getSelectedValue(),cliente.getPaqueteUsuario().getUsername());
							cliente.setPaqueteSala(paqueteSala);
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

		JTextField texto = new JTextField();
		texto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviarMsjAServidor(texto.getText());
				texto.setText("");
				texto.grabFocus();
			}
		});

		texto.setEditable(true);
		texto.setForeground(Color.WHITE);
		texto.setBackground(Color.DARK_GRAY);
		texto.setCaretColor(Color.WHITE);
		texto.setBounds(209, 458, 302, 39);
		contentPane.add(texto);
		texto.setColumns(10);

		JButton enviarATodos = new JButton("Enviar a Todos");
		enviarATodos.setEnabled(true);
		enviarATodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsjAServidor(texto.getText());
				texto.setText("");
				texto.grabFocus();
			}

		});
		enviarATodos.setBounds(521, 458, 131, 39);
		contentPane.add(enviarATodos);




		JLabel labelNombreUsuario = new JLabel(user);
		labelNombreUsuario.setForeground(Color.BLACK);
		labelNombreUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelNombreUsuario.setBounds(96, 76, 103, 16);
		contentPane.add(labelNombreUsuario);

		JLabel labelUsuariosConectados = new JLabel("Usuarios Online");
		labelUsuariosConectados.setForeground(Color.BLACK);
		labelUsuariosConectados.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelUsuariosConectados.setBounds(22, 89, 111, 16);
		contentPane.add(labelUsuariosConectados);

		JLabel labelSalas = new JLabel("Salas Disponibles");
		labelSalas.setForeground(Color.BLACK);
		labelSalas.setFont(new Font("Tahoma", Font.PLAIN, 11));
		labelSalas.setBounds(22, 328, 111, 16);
		contentPane.add(labelSalas);

		JButton btnCrearSala = new JButton("Crear Sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuCreacionSala(cliente);
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
							PaqueteSala paqueteSala = new PaqueteSala(listaSalas.getSelectedValue(), cliente.getPaqueteUsuario().getUsername());
							cliente.setPaqueteSala(paqueteSala);
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
	
///////////////////////////////////////////////////////////////////////////////////////
	/*Este método es el de la magia recibe la ruta al archivo original y la ruta donde vamos a guardar la copia
    copyImage("C:\\Users\\IngenioDS\\Desktop\\test.png","C:\\Users\\IngenioDS\\Desktop\\Copia\\test2.png");*/
 
    public BufferedImage devolverImagenRedimencionada(BufferedImage bimage) {
        if(bimage.getHeight()>bimage.getWidth()){
            int heigt = (bimage.getHeight() * MAX_WIDTH) / bimage.getWidth();
            bimage = redimencionarImagen(bimage, MAX_WIDTH, heigt);
            int width = (bimage.getWidth() * MAX_HEIGHT) / bimage.getHeight();
            bimage = redimencionarImagen(bimage, width, MAX_HEIGHT);
        }else{
            int width = (bimage.getWidth() * MAX_HEIGHT) / bimage.getHeight();
            bimage = redimencionarImagen(bimage, width, MAX_HEIGHT);
            int heigt = (bimage.getHeight() * MAX_WIDTH) / bimage.getWidth();
            bimage = redimencionarImagen(bimage, MAX_WIDTH, heigt);
        }
        return bimage;
//        saveImage(bimage, copyPath);
    }
	
    /*
    Este método se utiliza para cargar la imagen de disco
    */
    public static BufferedImage loadImage(String pathName) {
        BufferedImage bimage = null;
        try {
            bimage = ImageIO.read(new File(pathName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimage;
    }
 
    /*
    Este método se utiliza para almacenar la imagen en disco
    */
//    public static void saveImage(BufferedImage bufferedImage, String pathName) {
//        try {
//            String format = (pathName.endsWith(".png")) ? "png" : "jpg";
//            File file =new File(pathName);
//            file.getParentFile().mkdirs();
//            ImageIO.write(bufferedImage, format, file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
	
	/*
	Este método se utiliza para redimensionar la imagen
	*/
	public static BufferedImage redimencionarImagen(BufferedImage bufferedImage, int newW, int newH) {
		int w = bufferedImage.getWidth();
		int h = bufferedImage.getHeight();
		
		BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
		Graphics2D g = bufim.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		
		return bufim;
	}
///////////////////////////////////////////////////////////////////////////////////////

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

	public static JList<String> getListConectados() {
		return listaUsuariosChatGeneral;
	}

	public static JList<String> getListSalas() {
		return listaSalas;
	}

	public static void setTextoChatGeneral (String msj) {
		chat.append(msj);
	}

	public static void eliminarConectados() {
		listaUsuariosChatGeneral.removeAll();
	}

	public static void cambiarModelo(DefaultListModel<String> modelo) {
		listaUsuariosChatGeneral.setModel(modelo);
	}

	public static void eliminarSalas() {
		listaSalas.removeAll();
	}

	public static void cambiarModeloSalas(DefaultListModel<String> modelo) {
		listaSalas.setModel(modelo);		
	}
	
	public void enviarMsjAServidor(String msj) {
		if(!msj.equals("")) {
			if(msj.startsWith("/")) {
				String[] words;
				words = msj.substring(1).split(" ", 2);
				if (words.length > 1 && words[1] != "") {
					words[1] = words[1].trim();
					
					if(cliente.getPaqueteUsuario().getListaDeConectados().contains(words[0]) && !words[0].equals(cliente.getPaqueteUsuario().getUsername())){
						cliente.setAccion(Comando.MP);
						PaqueteMensaje paqueteMsj = new PaqueteMensaje(cliente.getPaqueteUsuario().getUsername(),words[0],words[1],null);
						cliente.setPaqueteMensaje(paqueteMsj);

						if(cliente.getChatsActivos().containsKey(words[0])){
							String msjAgregar = cliente.getPaqueteUsuario().getUsername() + ": " + words[1] + "\n";
							cliente.getChatsActivos().get(words[0]).agregarMsj(msjAgregar);
						} else {
							Chat chatPropio = new Chat(cliente);

							chatPropio.setTitle(words[0]);

							cliente.getChatsActivos().put(words[0], chatPropio);
							String msjAgregar = cliente.getPaqueteUsuario().getUsername() + ": " + words[1] + "\n";
							chatPropio.agregarMsj(msjAgregar);
						}
						synchronized (cliente) {
							cliente.notify();
						}
					}

				} else if(words.length<=1 && !words[0].equals(cliente.getPaqueteUsuario().getUsername())){
					Chat chatPropio = new Chat(cliente);
					chatPropio.setTitle(words[0]);
					cliente.getChatsActivos().put(words[0], chatPropio);
				}
				
			} else {
				if (msj.startsWith("@") || msj.contains(" @")) {
					 chat.append(cliente.getPaqueteUsuario().getUsername() + ": " + msj + "\n");

					String mensaje;
					String[] mensajeArray;
					mensaje = msj;
					int pos = mensaje.indexOf("@") + 1;
					mensajeArray = mensaje.substring(pos).split(" ", 2);
					if (mensajeArray.length > 1 && mensajeArray[1] != null) {
						mensajeArray[1] = mensajeArray[1].trim();
					}


					if (!mensajeArray[0].equals(cliente.getPaqueteUsuario().getUsername())) {
						cliente.setAccion(Comando.MENCIONSALA);
						PaqueteMensaje paqueteMsj = new PaqueteMensaje(cliente.getPaqueteUsuario().getUsername(),
								mensajeArray[0], msj, "Ventana Principal");
						cliente.setPaqueteMensaje(paqueteMsj);
						synchronized (cliente) {
							cliente.notify();
						} 
					}
				} else {
					chat.append(cliente.getPaqueteUsuario().getUsername() + " : " + msj + "\n");
					cliente.setAccion(Comando.CHATALL);
					PaqueteMensaje paqueteMsj = new PaqueteMensaje(cliente.getPaqueteUsuario().getUsername(), null,
							msj, null);
					cliente.setPaqueteMensaje(paqueteMsj);

					synchronized (cliente) {
						cliente.notify();
					}
				}
			}
		}
		
	}
}
