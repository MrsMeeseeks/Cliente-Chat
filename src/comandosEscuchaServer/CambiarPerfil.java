package comandosEscuchaServer;

import cliente.Cliente;
import paqueteEnvios.PaqueteUsuario;

public class CambiarPerfil extends ComandoEscuchaServer{
	@Override
	public void ejecutar() {
		Cliente cliente = escuchaServer.getCliente();
		PaqueteUsuario paqueteUser = gson.fromJson(cadenaLeida, PaqueteUsuario.class);

		cliente.getPaqueteUsuario().setFotoPerfil(paqueteUser.getFotoPerfil());
		escuchaServer.actualizarFotoPerfil();
	}
}
