package mx.com.nmp.escenariosdinamicos.clienteservicios.vo;

import org.springframework.web.multipart.MultipartFile;

public class RequestService {
	private String usuario;
	private MultipartFile adjunto;
	private String vigencia;
	private String nombreAjuste;
	private Boolean emergente;
	
	public String getUsuario() {
		return usuario;
	}
	public MultipartFile getAdjunto() {
		return adjunto;
	}
	public String getVigencia() {
		return vigencia;
	}
	public String getNombreAjuste() {
		return nombreAjuste;
	}
	public Boolean getEmergente() {
		return emergente;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public void setAdjunto(MultipartFile adjunto) {
		this.adjunto = adjunto;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	public void setNombreAjuste(String nombreAjuste) {
		this.nombreAjuste = nombreAjuste;
	}
	public void setEmergente(Boolean emergente) {
		this.emergente = emergente;
	}
}
