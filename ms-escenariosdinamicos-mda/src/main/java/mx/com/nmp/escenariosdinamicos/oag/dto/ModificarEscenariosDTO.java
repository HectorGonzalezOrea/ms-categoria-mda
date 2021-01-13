package mx.com.nmp.escenariosdinamicos.oag.dto;

import java.util.List;

import mx.com.nmp.escenariosdinamicos.model.ModEscenariosReq;

public class ModificarEscenariosDTO {
 private List<ModEscenariosReq> escenarios;

public List<ModEscenariosReq> getEscenarios() {
	return escenarios;
}
public void setEscenarios(List<ModEscenariosReq> escenarios) {
	this.escenarios = escenarios;
}
}
