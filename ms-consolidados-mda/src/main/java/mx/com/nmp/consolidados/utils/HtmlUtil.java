package mx.com.nmp.consolidados.utils;

import java.util.ArrayList;
import java.util.List;

import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasResponseVO;
import mx.com.nmp.consolidados.oag.vo.PartidaResponseVO;

public class HtmlUtil {

	
	public StringBuilder armarTablaPartidasConArbitrariedad(List<ArbitrajePreciosPartidasResponseVO> listaVerificarRegistrosResp) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<tr>");
		sb.append("<td>");
		
		sb.append("SKU");
		
		sb.append("</td>");
		sb.append("<td>");
		
		sb.append("Partida");
		
		sb.append("</td>");
		sb.append("<td>");
		
		sb.append("Arbitrariedad");
		
		sb.append("</td>");
		sb.append("</tr>");
		
		List<PartidaResponseVO> partidas = new ArrayList<>();
		
		if(!listaVerificarRegistrosResp.isEmpty()) {
			listaVerificarRegistrosResp
			.stream()
			.forEach( lv -> {
				if(!lv.getPartida().isEmpty()) {
					lv.getPartida()
					.stream()
					.forEach(p -> {
						if(Boolean.TRUE.equals(p.getCumpleArbitraje())) {
							partidas.add(p);
							
							sb.append("<tr>");
							sb.append("<td>");
							sb.append(p.getSku());
							
							sb.append("</td>");
							sb.append("<td>");
							sb.append(p.getIdPartida());
							
							sb.append("</td>");
							sb.append("<td>");
							sb.append(p.getCumpleArbitraje());
							
							sb.append("</td>");
							sb.append("</tr>");
							
						}
					});
				}
			});
		}
		
		return sb;
	}
	
}
