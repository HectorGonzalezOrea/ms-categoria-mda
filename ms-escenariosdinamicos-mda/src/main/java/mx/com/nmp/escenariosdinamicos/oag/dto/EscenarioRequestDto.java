package mx.com.nmp.escenariosdinamicos.oag.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosReq;
@NotNull
public class EscenarioRequestDto {
	@NotNull
	private List<CrearEscenariosReq> escenarios;
	
	public List<CrearEscenariosReq> getEscenarios() {
		return escenarios;
	}
	public void setEscenarios(List<CrearEscenariosReq> escenarios) {
		this.escenarios = escenarios;
	}
}
