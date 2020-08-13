package mx.com.nmp.consolidados.utils;


import java.util.List;

import mx.com.nmp.consolidados.msestablecimientoprecios.vo.AjustePreciosRequestVO;
import mx.com.nmp.consolidados.oag.vo.PartidaResponseVO;

public class HtmlUtil {
      String cerrarTd="<td>";
	public StringBuilder armarTablaPartidasConArbitrariedad(List<PartidaResponseVO> procesadas) {

		StringBuilder sb = new StringBuilder();
       
		procesadas.stream().forEach(p -> {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append(p.getSku());
			sb.append(cerrarTd);
			sb.append("<td>");
			sb.append(p.getIdPartida());
			sb.append(cerrarTd);
			sb.append("<td>");
			sb.append(p.getCumpleArbitraje());
			sb.append(cerrarTd);
			sb.append("</tr>");
		});

		return sb;
	}

	public StringBuilder armarTablaPartidasFallidasAjuste(
			AjustePreciosRequestVO request) {

		StringBuilder sb = new StringBuilder();

		if (!request.isEmpty()) {
			request
			.stream()
			.forEach(r -> {
				sb.append("<tr>");
					sb.append("<td>");
						sb.append(r.getSku());
					sb.append("</td>");
					sb.append("<td>");
						sb.append(r.getFolioPartida());
					sb.append("</td>");
				sb.append("</tr>");
			});
		}
		return sb;
	}

}
