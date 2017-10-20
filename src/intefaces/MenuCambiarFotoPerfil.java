package intefaces;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import cliente.Cliente;
import paqueteEnvios.Comando;
import paqueteEnvios.PaqueteUsuario;

public class MenuCambiarFotoPerfil extends JFrame{

	private static final long serialVersionUID = 1L;
	private JLabel labelImagen;
	private JPanel panelPerfil;
//	private JPanel contentPane;
    private JFileChooser fileChooser;
    private File archivoElegido;
   
    //Ancho máximo
    public static int MAX_WIDTH=260;
    //Alto máximo
    public static int MAX_HEIGHT=260;
    
    //Construtor de la clase
    public MenuCambiarFotoPerfil(Cliente cliente) {
    	
    	setTitle("Foto de perfil");
    	setResizable(false);
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	//Bloque try-catch para atrapar los errores
        try {
                //Look and Feel para la aplicacion dependiendo del sistema operativo
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
                e.printStackTrace();
        }
       
        JFileChooser seleccionArchivo = new JFileChooser();
		//Creo un filtro por extensión
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
		seleccionArchivo.setFileFilter(filtro);
        
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		labelImagen = new JLabel();
		
		panelPerfil = new JPanel();
		panelPerfil.setBounds(60, 68, MAX_WIDTH, MAX_HEIGHT);
		panelPerfil.add(labelImagen);
		contentPane.add(panelPerfil);
        
        JButton btnAbrir = new JButton("Abrir");
        btnAbrir.setActionCommand("abre");
        btnAbrir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		if("abre".equals(ev.getActionCommand())) {
					int valorFileChoser = seleccionArchivo.showOpenDialog(null);
					if(valorFileChoser == JFileChooser.APPROVE_OPTION) {
						archivoElegido = seleccionArchivo.getSelectedFile();
						String pathArchElegido = archivoElegido.getPath();
						
						//muestra la foto elegida
						BufferedImage bimage = null;
						try {
							bimage = ImageIO.read(new File(pathArchElegido));
						} catch (IOException e) {
							e.printStackTrace();
						}
						ImageIcon iconoPerfil = new ImageIcon(
								bimage.getScaledInstance(MAX_WIDTH, MAX_HEIGHT,
										Image.SCALE_DEFAULT));
						labelImagen.setIcon(iconoPerfil);
						panelPerfil.add(labelImagen);
					}
				}
        	}
        });
        btnAbrir.setBounds(33, 28, 89, 23);
        contentPane.add(btnAbrir);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
					synchronized (cliente) {
						cliente.getPaqueteUsuario();
						//envía la foto elegida y la guarda en disco
						byte[] foto = PaqueteUsuario.deArchivoABytes(archivoElegido);
						cliente.getPaqueteUsuario().setFotoPerfil(foto);
						cliente.setAccion(Comando.CAMBIARPERFIL);
						
						String nuevoArchivo = "Imagenes/" 
								+ cliente.getPaqueteUsuario().getUsername() + ".png";
						PaqueteUsuario.deBytesAFile(foto, nuevoArchivo);
						
						cliente.notify();
					} 
        		}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error al enviar imagen al servidor.");
				}
        		dispose();
        	}
        });
        btnGuardar.setBounds(150, 28, 89, 23);
        contentPane.add(btnGuardar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		dispose();
        	}
        });
        btnCancelar.setBounds(262, 28, 89, 23);
        contentPane.add(btnCancelar);
        
      //Estableciendo visibilidad, tamaño y cierrre de la aplicacion
        setVisible(true);
        setBounds(500,200, 400, 400);
//        add(contentPane);
       
        //Creando FileChooser
        fileChooser = new JFileChooser();
        //Añadiendole un filtro
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
        fileChooser.setFileFilter(filter);
        
    }
    

    
//    //Metodo de accion para el boton
//    public void actionPerformed(ActionEvent e)
//    {
//            if("abre".equals( e.getActionCommand() ) )
//            {                      
//                    //Valor que tomara el fileChooser
//                    int regresaValor = fileChooser.showOpenDialog(null);   
//                    //Accion del fileChooser
//                    if(regresaValor == JFileChooser.APPROVE_OPTION)
//                    {
//                            //Crear propiedades para ser utilizadas por fileChooser
//                            File archivoElegido = fileChooser.getSelectedFile();
//                            //Obteniendo la direccion del archivo
//                            String direccion = archivoElegido.getPath();
//                            //Bloque try-catch para errores
//                            try
//                            {
//                                    //Obtiene la direccion del archivo y lo instancia en icon
//                                    ImageIcon icon = new ImageIcon( direccion );
//                                    //Setea el labelImagen con el archivo obtenido
//                                    labelImagen.setIcon(icon);
//                                    panelPerfil.add(labelImagen);
//                            }
//                            catch(Exception es)
//                            {
//                                    JOptionPane.showMessageDialog(null, "Upss!! error abriendo la imagen "+ es);
//                            }
//                    }
//            }
//    }
}
