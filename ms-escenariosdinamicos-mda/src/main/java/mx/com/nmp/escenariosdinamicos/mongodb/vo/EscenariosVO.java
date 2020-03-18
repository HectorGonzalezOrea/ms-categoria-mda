package mx.com.nmp.escenariosdinamicos.mongodb.vo;

import org.threeten.bp.LocalDate;

import mx.com.nmp.escenariosdinamicos.model.DiaDosEnum;
import mx.com.nmp.escenariosdinamicos.model.DiaTresEnum;
import mx.com.nmp.escenariosdinamicos.model.DiaUnoEnum;
import mx.com.nmp.escenariosdinamicos.model.IdReglaEnum;



public class EscenariosVO {
	
	private Integer idEscenario;
	private DiaUnoEnum diaUno;
	private DiaDosEnum diaDos;
	private DiaTresEnum diaTres;
	private IdReglaEnum idRegla;
	private LocalDate fechaCreacion;
	private LocalDate fechaActualizacion;
	
	public Integer getIdEscenario() {
		return idEscenario;
	}
	public void setIdEscenario(Integer idEscenario) {
		this.idEscenario = idEscenario;
	}
	public DiaUnoEnum getDiaUno() {
		return diaUno;
	}
	public void setDiaUno(DiaUnoEnum diaUno) {
		this.diaUno = diaUno;
	}
	public DiaDosEnum getDiaDos() {
		return diaDos;
	}
	public void setDiaDos(DiaDosEnum diaDos) {
		this.diaDos = diaDos;
	}
	public DiaTresEnum getDiaTres() {
		return diaTres;
	}
	public void setDiaTres(DiaTresEnum diaTres) {
		this.diaTres = diaTres;
	}
	public IdReglaEnum getIdRegla() {
		return idRegla;
	}
	public void setIdRegla(IdReglaEnum idRegla) {
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
