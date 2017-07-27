package comandosEscuchaServer;

import cliente.Cliente;
import intefaces.Chat;
import paqueteEnvios.PaqueteMensaje;

public class MP extends ComandoEscuchaServer {

	@Override
	public void ejecutar() {
		Cliente cliente = escuchaServer.getCliente();
		cliente.setPaqueteMensaje((PaqueteMensaje) gson.fromJson(cadenaLeida, PaqueteMensaje.class));

		if (!(cliente.getChatsActivos().containsKey(cliente.getPaqueteMensaje().getUserEmisor()))) {
			Chat chat = new Chat(cliente);

			chat.setTitle(cliente.getPaqueteMensaje().getUserEmisor());
			chat.setVisible(true);

			cliente.getChatsActivos().put(cliente.getPaqueteMensaje().getUserEmisor(), chat);
		}
		cliente.getChatsActivos().get(cliente.getPaqueteMensaje().getUserEmisor()).getChat()
		.append(cliente.getPaqueteMensaje().getUserEmisor() + ": "
				+ cliente.getPaqueteMensaje().getMsj() + "\n");
		cliente.getChatsActivos().get(cliente.getPaqueteMensaje().getUserEmisor()).getTexto().grabFocus();		
	}

}
