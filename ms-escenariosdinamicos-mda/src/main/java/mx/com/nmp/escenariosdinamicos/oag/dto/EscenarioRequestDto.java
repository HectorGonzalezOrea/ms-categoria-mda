package mx.com.nmp.escenariosdinamicos.oag.dto;

import java.util.List;
import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosReq;

public class EscenarioRequestDto {
	List<CrearEscenariosReq> escenarios;
	
	public List<CrearEscenariosReq> getEscenarios() {
		return escenarios;
	}
	public void setEscenarios(List<CrearEscenariosReq> escenarios) {
		this.escenarios = escenarios;
	}
}
