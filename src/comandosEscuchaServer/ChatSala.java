package comandosEscuchaServer;

import cliente.Cliente;
import paqueteEnvios.PaqueteMensajeSala;

public class ChatSala extends ComandoEscuchaServer {

	@Override
	public void ejecutar() {
		PaqueteMensajeSala paq = new PaqueteMensajeSala();
		paq = (PaqueteMensajeSala) gson.fromJson(cadenaLeida, PaqueteMensajeSala.class);
		Cliente cliente = escuchaServer.getCliente();
		cliente.getPaqueteMensajeSala().setMsj(paq.getMsj());
		cliente.getPaqueteMensajeSala().setNombreSala(paq.getNombreSala());
		cliente.getPaqueteMensajeSala().setUserEmisor(paq.getUserEmisor());


		if((cliente.getSalasActivas().containsKey(cliente.getPaqueteMensajeSala().getNombreSala()))){

			cliente.getSalasActivas().get(cliente.getPaqueteMensajeSala().getNombreSala()).getChat()
			.append(cliente.getPaqueteMensajeSala().getUserEmisor() + ": " + cliente.getPaqueteMensajeSala().getMsj() + "\n");
			cliente.getSalasActivas().get(cliente.getPaqueteMensajeSala().getNombreSala()).getTexto().grabFocus();

		}
	}
}
