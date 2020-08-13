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
				sb.append(Constantes.ABRIR_TD_STYLE+vo.getFolioPartida()+Constantes.CERRAR_TD);
				sb.append(Constantes.ABRIR_TD_STYLE+vo.getSku()+"</td>");
				sb.append(Constantes.ABRIR_TD_STYLE+formato.format(vo.getPrecioActual())+Constantes.CERRAR_TD);
				sb.append(Constantes.ABRIR_TD_STYLE+formato.format(vo.getPrecioModificado())+Constantes.CERRAR_TD);
				sb.append(Constantes.ABRIR_TD_STYLE+formato.format(vo.getPrecioActual())+Constantes.CERRAR_TD);
				sb.append("</tr>");
			});
		}
		return sb;
	}
	
	

}
