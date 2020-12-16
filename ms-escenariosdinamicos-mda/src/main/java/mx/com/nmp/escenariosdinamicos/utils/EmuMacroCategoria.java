package mx.com.nmp.escenariosdinamicos.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class EmuMacroCategoria {

	public  String calculaCateogiaCompleta(String parametro) {
		String dato=null;
		String paramAux = parametro.toUpperCase();
		List<String> lstCategoriasCompletas = Arrays.asList("JUEGO", "RELOJ", "COLLAR", "ARETE", "PULSERA", "ANILLO",
				"VIOLADOR", "AHOGADOR", "ARRACADAS", "BRAZALETE", "ARGOLLA", "BROQUELES", "PRENDEDORES", "ESCLAVA",
				"MEDALLA", "GARGANTILLA", "CRUZ", "CRUCIFIJO", "CRUCIFIJO", "PENDIENTE", "CADENA", "DIJE",
				"MANCUERNILLA", "ROSARIO", "ESCALOGRAMA", "MONEDAS", "LLAVERO", "FISTOL", "CHURUMBELA");
		for (String string : lstCategoriasCompletas) {
			dato=string;
			if (dato.contains(paramAux)) {
				return dato;
			}
		}
		dato="Sin_Categoria";
		return dato;
	}
	
	public String calculaCategoriaTop(String parametro){
		String dato=null;
		String paramAux = parametro.toUpperCase();
		List<String> lstCategoriasTop=Arrays.asList("ANILLO","COLLAR","PULSERA","ARRACADAS","ARETES","ARGOLLAS","ESCLAVA","PENDIENTE","GARGANTILLA");
		for (String string : lstCategoriasTop) {
			dato=string;
			if (dato.contains(paramAux)) {
				return dato;
			}
		}
		dato="Sin_Categoria";
		return dato;
	}
}
