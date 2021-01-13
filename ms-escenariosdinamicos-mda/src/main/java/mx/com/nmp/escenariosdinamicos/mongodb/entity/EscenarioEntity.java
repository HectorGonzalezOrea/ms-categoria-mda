package mx.com.nmp.escenariosdinamicos.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.threeten.bp.LocalDate;

import mx.com.nmp.escenariosdinamicos.model.DiaUnoEnum;
import mx.com.nmp.escenariosdinamicos.model.DiaDosEnum;
import mx.com.nmp.escenariosdinamicos.model.DiaTresEnum;
import mx.com.nmp.escenariosdinamicos.model.IdReglaEnum;



@Document(collection = "Escenarios")
public class EscenarioEntity {
	
	@Transient
    public static final String SEQUENCE_NAME = "escenario_sequence";
	
	@Id
	private Integer idEscenario;
	private String diaUno;
	private String diaDos;
	private String diaTres;
	private String idRegla;
	private LocalDate fechaCreacion;
	private LocalDate fechaActualizacion;
	
	
	public Integer getIdEscenario() {
		return idEscenario;
	}
	public void setIdEscenario(Integer idEscenario) {
		this.idEscenario = idEscenario;
	}
	public String getDiaUno() {
		return diaUno;
	}
	public void setDiaUno(String diaUno) {
		this.diaUno = diaUno;
	}
	public String getDiaDos() {
		return diaDos;
	}
	public void setDiaDos(String diaDos) {
		this.diaDos = diaDos;
	}
	public String getDiaTres() {
		return diaTres;
	}
	public void setDiaTres(String diaTres) {
		this.diaTres = diaTres;
	}
	public String getIdRegla() {
		return idRegla;
	}
	public void setIdRegla(String idRegla) {
		this.idRegla = idRegla;
	}
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public LocalDate getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(LocalDate fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
	
	

}
