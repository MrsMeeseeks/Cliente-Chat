package comandosEscuchaServer;

import cliente.Cliente;
import intefaces.Chat;
import paqueteEnvios.PaqueteMensaje;

public class MP extends ComandoEscuchaServer {

	@Override
	public void ejecutar() {
		Cliente cliente = escuchaServer.getCliente();
		PaqueteMensaje paqueteMensaje = gson.fromJson(cadenaLeida, PaqueteMensaje.class);

		if (!(cliente.getChatsActivos().containsKey(paqueteMensaje.getUserEmisor()))) {
			Chat chat = new Chat(cliente);

			chat.setTitle(paqueteMensaje.getUserEmisor());
			chat.setVisible(true);

			cliente.getChatsActivos().put(paqueteMensaje.getUserEmisor(), chat);
		}
		cliente.getChatsActivos().get(paqueteMensaje.getUserEmisor()).getChat()
		.append(paqueteMensaje.getUserEmisor() + ": "
				+ paqueteMensaje.getMsj() + "\n");
		cliente.getChatsActivos().get(paqueteMensaje.getUserEmisor()).getTexto().grabFocus();		
	}

}
