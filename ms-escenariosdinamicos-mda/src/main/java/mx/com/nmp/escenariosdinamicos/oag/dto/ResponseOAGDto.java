package mx.com.nmp.escenariosdinamicos.oag.dto;

import java.util.List;

import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaResponseVO;



public class ResponseOAGDto {
	
	private List<PartidaResponseVO> partida;

	public List<PartidaResponseVO> getPartida() {
		return partida;
	}

	public void setPartida(List<PartidaResponseVO> partida) {
		this.partida = partida;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ResponseOAGDto {\n");
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
