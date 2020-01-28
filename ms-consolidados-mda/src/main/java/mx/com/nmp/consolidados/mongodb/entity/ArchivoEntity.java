package mx.com.nmp.consolidados.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Document(collection = "Consolidados")
public class ArchivoEntity {
	
	@Transient
    public static final String SEQUENCE_NAME = "usuario_sequence";
	
	@Id
	private ObjectId _id;
	private Long idArchivo;
	private File adjunto;
	private Date vigencia;
	private String nombreAjuste;
	private Boolean emergente;
	private Date fechaAplicacion;
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Long getIdArchivo() {
		return idArchivo;
	}
	public void setIdArchivo(Long idArchivo) {
		this.idArchivo = idArchivo;
	}
	public File getAdjunto() {
		return adjunto;
	}
	public void setAdjunto(File adjunto) {
		this.adjunto = adjunto;
	}
	public Date getVigencia() {
		return vigencia;
	}
	public void setVigencia(Date vigencia) {
		this.vigencia = vigencia;
	}
	public String getNombreAjuste() {
		return nombreAjuste;
	}
	public void setNombreAjuste(String nombreAjuste) {
		this.nombreAjuste = nombreAjuste;
	}
	public Boolean getEmergente() {
		return emergente;
	}
	public void setEmergente(Boolean emergente) {
		this.emergente = emergente;
	}
	public Date getFechaAplicacion() {
		return fechaAplicacion;
	}
	public void setFechaAplicacion(Date fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	

}
