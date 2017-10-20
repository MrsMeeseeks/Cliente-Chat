package comandosEscuchaServer;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import paqueteEnvios.PaqueteDeUsuariosYSalas;

public class Conexión extends ComandoEscuchaServer {

	@Override
	public void ejecutar() {
		ArrayList<String> usuariosConectados = new ArrayList<String>();
//		ArrayList<byte[]> fotosConectados = new ArrayList<byte[]>();
//		ArrayList<PaqueteUsuario> paqUsersConectados = new ArrayList<PaqueteUsuario>();
		
		usuariosConectados = (ArrayList<String>) gson.fromJson(cadenaLeida, PaqueteDeUsuariosYSalas.class)
				.getUsuarios();
//		fotosConectados = (ArrayList<byte[]>) gson.fromJson(cadenaLeida, PaqueteDeUsuariosYSalas.class)
//				.getFotos();
//		paqUsersConectados = (ArrayList<PaqueteUsuario>) gson.fromJson(cadenaLeida,
//				PaqueteDeUsuariosYSalas.class).getPaqUsuarios();
		
		
//		if(paqUsersConectados != null && !paqUsersConectados.isEmpty()) {
//			for (PaqueteUsuario paqueteUsuario : paqUsersConectados) {
//				usuariosConectados.add(paqueteUsuario.getUsername());
//			}
			usuariosConectados.remove(escuchaServer.getCliente().getPaqueteUsuario().getUsername());
			escuchaServer.getCliente().getPaqueteUsuario().setListaDeConectados(usuariosConectados);
//		}
		
//		fotosConectados.remove(escuchaServer.getCliente().getPaqueteUsuario().getFotoPerfil());
//		escuchaServer.getCliente().getPaqueteUsuario().setListaFotosConectados(fotosConectados);
		
//		paqUsersConectados.remove(escuchaServer.getCliente().getPaqueteUsuario());
//		escuchaServer.getCliente().getPaqueteUsuario().setListaPaqUsuariosConectados(paqUsersConectados);
//		
		try {
			escuchaServer.actualizarLista();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
