package mx.com.nmp.consolidados.oag.vo;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"partida"
})
public class ArbitrajePreciosPartidasRequestVO {

	@JsonProperty("partida")
	private List<PartidaVO> partida = null;

	@JsonProperty("partida")
	public List<PartidaVO> getPartida() {
		return partida;
	}

	@JsonProperty("partida")
	public void setPartida(List<PartidaVO> partida) {
		this.partida = partida;
	}

}
