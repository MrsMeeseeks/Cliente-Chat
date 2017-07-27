package comandosCliente;

import java.io.IOException;

import comandosEscuchaServer.ComandoCliente;
import paqueteEnvios.Comando;
import paqueteEnvios.PaqueteMensajeSala;

public class ChatSala extends ComandoCliente {

	@Override
	public void ejecutar() {
		PaqueteMensajeSala paqueteMensajeSala = cliente.getPaqueteMensajeSala();
		paqueteMensajeSala.setComando(Comando.CHATSALA);
		try {
			cliente.getSalida().writeObject(gson.toJson(paqueteMensajeSala));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
