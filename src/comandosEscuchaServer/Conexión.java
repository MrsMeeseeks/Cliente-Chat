package comandosEscuchaServer;

import java.util.ArrayList;

import paqueteEnvios.PaqueteDeUsuariosYSalas;

public class Conexión extends ComandoCliente {
	
	@Override
	public void ejecutar() {
		ArrayList<String> usuariosAntiguos = new ArrayList<String>();
		ArrayList<String> diferenciaContactos = new ArrayList<String>();
		ArrayList<String> usuariosConectados = new ArrayList<String>();
		usuariosConectados = (ArrayList<String>) gson.fromJson(cadenaLeida, PaqueteDeUsuariosYSalas.class)
				.getUsuarios();
				for (String usuario : usuariosConectados) {
					if (!usuariosAntiguos.contains(usuario)) {
						usuariosAntiguos.add(usuario);
					}
				}
				diferenciaContactos = new ArrayList<String>(usuariosAntiguos);
				diferenciaContactos.removeAll(usuariosConectados);
				if (!diferenciaContactos.isEmpty()) {
					for (String usuario : diferenciaContactos) {
						if (escuchaServer.getCliente().getChatsActivos().containsKey(usuario)) {
							escuchaServer.getCliente().getChatsActivos().get(usuario).getChat()
							.append("El usuario: " + usuario + " se ha desconectado\n");
						}
						usuariosAntiguos.remove(usuario);
					}
				}
				escuchaServer.getCliente().getPaqueteUsuario().setListaDeConectados(usuariosConectados);
				escuchaServer.actualizarLista();
	}

}
