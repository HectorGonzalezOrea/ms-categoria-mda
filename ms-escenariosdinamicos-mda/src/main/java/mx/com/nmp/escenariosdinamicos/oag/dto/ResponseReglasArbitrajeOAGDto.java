package mx.com.nmp.escenariosdinamicos.oag.dto;

import java.util.List;

import mx.com.nmp.escenariosdinamicos.oag.vo.ReglasArbitrajeVO;

public class ResponseReglasArbitrajeOAGDto {
	
	List<ReglasArbitrajeVO> partida;

	public List<ReglasArbitrajeVO> getPartida() {
		return partida;
	}

	public void setPartida(List<ReglasArbitrajeVO> partida) {
		this.partida = partida;
	}
}
