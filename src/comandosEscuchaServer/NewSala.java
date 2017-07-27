package comandosEscuchaServer;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import cliente.Cliente;
import paqueteEnvios.Paquete;
import paqueteEnvios.PaqueteDeUsuariosYSalas;

public class NewSala extends ComandoEscuchaServer {

	@Override
	public void ejecutar() {
		Cliente cliente = escuchaServer.getCliente();
		PaqueteDeUsuariosYSalas paqueteDUS = gson.fromJson(cadenaLeida, PaqueteDeUsuariosYSalas.class);

		if( paqueteDUS.getMsj().equals(Paquete.msjExito)) {
			ArrayList<String> listadoSalas = paqueteDUS.getSalas();
			cliente.getPaqueteUsuario().setListaDeSalas(listadoSalas);
			escuchaServer.actualizarListaSalas();
		} else {
			JOptionPane.showMessageDialog(null, "Sala ya existente.");
		}		
	}

}
