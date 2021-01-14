package mx.com.nmp.escenariosdinamicos.oag.dto;

import java.util.List;

import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;



public class RequestReglaEscenarioDinamicoDto {
	
	private List<PartidaVO> partida;

	public List<PartidaVO> getPartida() {
		return partida;
	}

	public void setPartida(List<PartidaVO> partida) {
		this.partida = partida;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class RequestReglaEscenarioDinamicoDto {\n");
		sb.append("    partida: ").append(toIndentedString(partida)).append("\n");
		sb.append("}");
		
		return sb.toString();
	}
	
	private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
	
}
