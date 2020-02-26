package mx.com.nmp.gestionbolsas.mongodb.entity;

import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Bolsas")
public class BolsasEntity {

	@Transient
    public static final String SEQUENCE_NAME = "bolsas_sequence";
	
	@Id
	private Integer idBolsa;
	private String nombre;
	private Integer tipo;
	private String ramo;
	private String subramo;
	private String factor;
	private List<String> sucursales;
	private String autor;
	
	
	public Integer getidBolsa() {
		return idBolsa;
	}
	public void setidBolsa(Integer idBolsa) {
		this.idBolsa = idBolsa;
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
	public List<String> getSucursales() {
		return sucursales;
	}
	public void setSucursales(List<String> sucursales) {
		this.sucursales = sucursales;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
}
