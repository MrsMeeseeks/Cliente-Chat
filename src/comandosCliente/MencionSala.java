package comandosCliente;


import java.io.IOException;

import comandosEscuchaServer.ComandoCliente;
import paqueteEnvios.Comando;
import paqueteEnvios.PaqueteMencion;

public class MencionSala extends ComandoCliente {

	@Override
	public void ejecutar() {
		PaqueteMencion paqueteMencion = cliente.getPaqueteMencion();
		paqueteMencion.setComando(Comando.MENCIONSALA);
		try {
			cliente.getSalida().writeObject(gson.toJson(paqueteMencion));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
