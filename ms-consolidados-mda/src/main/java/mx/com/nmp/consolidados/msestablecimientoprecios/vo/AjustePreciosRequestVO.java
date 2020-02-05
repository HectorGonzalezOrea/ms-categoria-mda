package mx.com.nmp.consolidados.msestablecimientoprecios.vo;

import java.util.ArrayList;

public class AjustePreciosRequestVO extends ArrayList<AjustePrecio> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1772278823069630592L;

	@Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class AjustePreciosRequestVO {\n");
	    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
