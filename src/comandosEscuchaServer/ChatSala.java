package comandosEscuchaServer;

import cliente.Cliente;
import paqueteEnvios.PaqueteMensaje;

public class ChatSala extends ComandoEscuchaServer {

	@Override
	public void ejecutar() {
		PaqueteMensaje paq = new PaqueteMensaje();
		paq = (PaqueteMensaje) gson.fromJson(cadenaLeida, PaqueteMensaje.class);
		Cliente cliente = escuchaServer.getCliente();
	
		if((cliente.getSalasActivas().containsKey(paq.getNombreSala()))){

			cliente.getSalasActivas().get(paq.getNombreSala()).getChat()
			.append(paq.getUserEmisor() + ": " + paq.getMsj() + "\n");
			cliente.getSalasActivas().get(paq.getNombreSala()).getTexto().grabFocus();

		}
	}
}
