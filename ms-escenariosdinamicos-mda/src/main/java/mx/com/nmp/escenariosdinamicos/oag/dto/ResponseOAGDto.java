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

}
