package comandosEscuchaServer;

import cliente.Cliente;
import intefaces.VentanaPrincipal;
import paqueteEnvios.PaqueteMensaje;

public class ChatAll extends ComandoCliente {

	@Override
	public void ejecutar() {
		Cliente cliente = escuchaServer.getCliente();
		cliente.setPaqueteMensaje((PaqueteMensaje) gson.fromJson(cadenaLeida, PaqueteMensaje.class));
		VentanaPrincipal.setTextoChatGeneral(cliente.getPaqueteMensaje().getUserEmisor(),cliente.getPaqueteMensaje().getMsj());
	}

}
