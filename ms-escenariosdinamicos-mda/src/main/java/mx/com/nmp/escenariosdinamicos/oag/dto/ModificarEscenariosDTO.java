package mx.com.nmp.escenariosdinamicos.oag.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import mx.com.nmp.escenariosdinamicos.model.ModEscenariosReq;
@NotNull
public class ModificarEscenariosDTO {
@NotNull
 private List<ModEscenariosReq> escenarios;

public List<ModEscenariosReq> getEscenarios() {
	return escenarios;
}
public void setEscenarios(List<ModEscenariosReq> escenarios) {
	this.escenarios = escenarios;
}
}
