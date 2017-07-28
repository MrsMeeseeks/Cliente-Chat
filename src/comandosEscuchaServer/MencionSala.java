package comandosEscuchaServer;

import java.awt.AWTException;
import java.net.MalformedURLException;

import cliente.Cliente;
import intefaces.Notificacion;
import paqueteEnvios.PaqueteMensaje;

public class MencionSala extends ComandoEscuchaServer {

	@Override
	public void ejecutar() {
		
		PaqueteMensaje paqueteMensaje = gson.fromJson(cadenaLeida, PaqueteMensaje.class);
		Cliente cliente = escuchaServer.getCliente();
		
		if((cliente.getSalasActivas().containsKey(paqueteMensaje.getNombreSala()))){
			String msjAgregar = paqueteMensaje.getUserEmisor() + ": " + paqueteMensaje.getMsj() + "\n";
			cliente.getSalasActivas().get(paqueteMensaje.getNombreSala()).agregarMsj(msjAgregar);
		}

		try {
			if ((cliente.getPaqueteUsuario().getUsername().equals(paqueteMensaje.getUserReceptor()))) {
				Notificacion notificacion = new Notificacion(paqueteMensaje.getNombreSala(),paqueteMensaje.getUserEmisor());
				notificacion.displayTray();
			}
		} catch (MalformedURLException | AWTException e) {
			e.printStackTrace();
		} 
	}

}
