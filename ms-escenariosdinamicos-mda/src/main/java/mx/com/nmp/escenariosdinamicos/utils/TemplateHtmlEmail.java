package mx.com.nmp.escenariosdinamicos.utils;

import java.text.DecimalFormat;
import java.util.List;

import mx.com.nmp.escenariosdinamicos.oag.vo.PreciosVO;

public class TemplateHtmlEmail {
	
	
	/**
	 * @param List PreciosVO
	 * @return el body de una tabla html
	 * */
	public StringBuilder tablePrecios(List<PreciosVO> lstPrecios) {
		StringBuilder sb= new StringBuilder();
		DecimalFormat formato= new DecimalFormat("#,###.00");
		if(!lstPrecios.isEmpty()) {
			lstPrecios.stream().forEach(vo->{
				sb.append("<tr>");
				sb.append("<td style=\"border: 1px solid black\">"+vo.getFolioPartida()+"</td>");
				sb.append("<td style=\"border: 1px solid black\">"+vo.getSku()+"</td>");
				sb.append("<td style=\"border: 1px solid black\">"+formato.format(vo.getPrecioActual())+"</td>");
				sb.append("<td style=\"border: 1px solid black\">"+formato.format(vo.getPrecioModificado())+"</td>");
				sb.append("<td style=\"border: 1px solid black\">"+formato.format(vo.getPrecioActual())+"</td>");
				sb.append("</tr>");
			});
		}
		return sb;
	}
	
	

}
