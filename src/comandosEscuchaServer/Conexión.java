package comandosEscuchaServer;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import paqueteEnvios.PaqueteDeUsuariosYSalas;

public class Conexión extends ComandoEscuchaServer {

	@Override
	public void ejecutar() {
		ArrayList<String> usuariosConectados = new ArrayList<String>();
		ArrayList<String> fotosConectados = new ArrayList<String>();
		
		PaqueteDeUsuariosYSalas paqueteUS = gson.fromJson(cadenaLeida, PaqueteDeUsuariosYSalas.class);
		usuariosConectados = paqueteUS.getUsuarios();
		fotosConectados = paqueteUS.getFotos();
		
		usuariosConectados.remove(escuchaServer.getCliente().getPaqueteUsuario());
		escuchaServer.getCliente().getPaqueteUsuario().setListaDeConectados(usuariosConectados);
		
		escuchaServer.getCliente().getPaqueteUsuario().setListaFotosConectados(fotosConectados);
		
		try {
			escuchaServer.actualizarLista();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
