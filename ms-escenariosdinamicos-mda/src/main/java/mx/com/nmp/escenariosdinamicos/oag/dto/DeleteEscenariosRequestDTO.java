package mx.com.nmp.escenariosdinamicos.oag.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class DeleteEscenariosRequestDTO {
@NotNull
private List<Integer> idEscenarios;

public List<Integer> getIdEscenarios() {
	return idEscenarios;
}

public void setIdEscenarios(List<Integer> idEscenarios) {
	this.idEscenarios = idEscenarios;
}
}
