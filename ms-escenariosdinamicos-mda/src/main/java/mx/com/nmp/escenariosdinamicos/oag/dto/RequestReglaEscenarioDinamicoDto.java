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

}
