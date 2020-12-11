package mx.com.nmp.gestionescenarios.model;

import java.util.List;

public class FechaAplicacionVO {
private HorasVO rangoHorario;
private List<String> fechas;

public HorasVO getRangoHorario() {
	return rangoHorario;
}
public List<String> getFechas() {
	return fechas;
}
public void setRangoHorario(HorasVO rangoHorario) {
	this.rangoHorario = rangoHorario;
}
public void setFechas(List<String> fechas) {
	this.fechas = fechas;
}

}
