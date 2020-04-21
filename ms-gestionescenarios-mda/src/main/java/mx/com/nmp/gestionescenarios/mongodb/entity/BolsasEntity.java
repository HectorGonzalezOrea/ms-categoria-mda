package mx.com.nmp.gestionescenarios.mongodb.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Bolsas")
public class BolsasEntity {

	@Id
	private Integer _id;
	private String nombre;
	private Integer tipo;
	private String ramo;
	private String subramo;
	private String factor;
	private List<Integer> sucursales;
	private String autor;
	private Date fechaCreacion;
	private Date fechaModificacion;
	
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getRamo() {
		return ramo;
	}
	public void setRamo(String ramo) {
		this.ramo = ramo;
	}
	public String getSubramo() {
		return subramo;
	}
	public void setSubramo(String subramo) {
		this.subramo = subramo;
	}
	public String getFactor() {
		return factor;
	}
	public void setFactor(String factor) {
		this.factor = factor;
	}
	public List<Integer> getSucursales() {
		return sucursales;
	}
	public void setSucursales(List<Integer> sucursales) {
		this.sucursales = sucursales;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
}
