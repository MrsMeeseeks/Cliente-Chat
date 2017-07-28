package comandosEscuchaServer;

import java.awt.AWTException;
import java.net.MalformedURLException;

import cliente.Cliente;
import intefaces.Notificacion;
import paqueteEnvios.Paquete;
import paqueteEnvios.PaqueteMensaje;

public class MencionSala extends ComandoEscuchaServer {

	private String msjAgregar;
	@Override
	public void ejecutar() {

		PaqueteMensaje paqueteMensaje = gson.fromJson(cadenaLeida, PaqueteMensaje.class);
		Cliente cliente = escuchaServer.getCliente();

		if (paqueteMensaje.getMsj().equals(Paquete.msjExito)) {
			if ((cliente.getSalasActivas().containsKey(paqueteMensaje.getNombreSala()))) {
				msjAgregar = paqueteMensaje.getUserEmisor() + ": " + paqueteMensaje.getMsjChat() + "\n";
			}
			try {
				if ((cliente.getPaqueteUsuario().getUsername().equals(paqueteMensaje.getUserReceptor()))) {
					Notificacion notificacion = new Notificacion(paqueteMensaje.getNombreSala(),
							paqueteMensaje.getUserEmisor());
					notificacion.displayTray();
				}
			} catch (MalformedURLException | AWTException e) {
				e.printStackTrace();
			} 
		} else {
			msjAgregar = paqueteMensaje.getMsjChat();
		}
		cliente.getSalasActivas().get(paqueteMensaje.getNombreSala()).agregarMsj(msjAgregar);
	}

}
