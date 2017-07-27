package comandosEscuchaServer;

import java.awt.AWTException;
import java.net.MalformedURLException;

import cliente.Cliente;
import intefaces.Notificacion;
import paqueteEnvios.PaqueteMencion;

public class MencionSala extends ComandoEscuchaServer {

	@Override
	public void ejecutar() {
		PaqueteMencion paqMenc = new PaqueteMencion();
		paqMenc = (PaqueteMencion) gson.fromJson(cadenaLeida, PaqueteMencion.class);
		Cliente cliente = escuchaServer.getCliente();
		
		cliente.getPaqueteMencion().setMsj(paqMenc.getMsj());
		cliente.getPaqueteMencion().setUserEmisor(paqMenc.getUserEmisor());
		cliente.getPaqueteMencion().setUserReceptor(paqMenc.getUserReceptor());
		cliente.getPaqueteMencion().setNombreSala(paqMenc.getNombreSala());

		if((cliente.getSalasActivas().containsKey(cliente.getPaqueteMencion().getNombreSala()))){
			cliente.getSalasActivas().get(cliente.getPaqueteMencion().getNombreSala()).getChat()
			.append(cliente.getPaqueteMencion().getUserEmisor() + ": " + cliente.getPaqueteMencion().getMsj() + "\n");
			cliente.getSalasActivas().get(cliente.getPaqueteMencion().getNombreSala()).getTexto().grabFocus();
		}

		try {
			if ((cliente.getPaqueteUsuario().getUsername().equals(cliente.getPaqueteMencion().getUserReceptor()))) {
				Notificacion notificacion = new Notificacion(cliente.getPaqueteMencion().getNombreSala(),cliente.getPaqueteMencion().getUserEmisor());
				notificacion.displayTray();
			}
		} catch (MalformedURLException | AWTException e) {
			e.printStackTrace();
		} 
	}

}
