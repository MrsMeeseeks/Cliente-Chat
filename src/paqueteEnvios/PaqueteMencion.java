package paqueteEnvios;

public class PaqueteMencion extends Paquete{
	
	private String userEmisor;
	private String userReceptor;
	private String nombreSala;
	private String msj;
	

	public String getUserEmisor() {
		return userEmisor;
	}
	
	public void setUserEmisor(String userEmisor) {
		this.userEmisor = userEmisor;
	}
	
	public String getUserReceptor() {
		return userReceptor;
	}
	
	public void setUserReceptor(String userReceptor) {
		this.userReceptor = userReceptor;
	}
	
	public String getNombreSala() {
		return nombreSala;
	}
	
	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}
	
	public String getMsj() {
		return msj;
	}
	
	public void setMsj(String mensaje) {
		this.msj = mensaje;
	}
	
	public Object clone() {
		Object obj = null;
		obj = super.clone();
		return obj;
	}
}
