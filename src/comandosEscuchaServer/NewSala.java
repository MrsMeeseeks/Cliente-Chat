package comandosEscuchaServer;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import cliente.Cliente;
import paqueteEnvios.Paquete;
import paqueteEnvios.PaqueteDeSalas;

public class NewSala extends ComandoEscuchaServer {

	@Override
	public void ejecutar() {
		Cliente cliente = escuchaServer.getCliente();
		PaqueteDeSalas paqueteDeSalas = gson.fromJson(cadenaLeida, PaqueteDeSalas.class);

		if( paqueteDeSalas.getMsj().equals(Paquete.msjExito)) {
			ArrayList<String> listadoSalas = paqueteDeSalas.getSalas();
			cliente.getPaqueteUsuario().setListaDeSalas(listadoSalas);
			escuchaServer.actualizarListaSalas();
		} else {
			JOptionPane.showMessageDialog(null, "Sala ya existente.");
		}		
	}

}
