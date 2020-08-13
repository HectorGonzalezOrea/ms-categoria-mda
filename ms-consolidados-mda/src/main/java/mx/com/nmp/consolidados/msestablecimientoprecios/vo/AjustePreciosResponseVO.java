package mx.com.nmp.consolidados.msestablecimientoprecios.vo;

public class AjustePreciosResponseVO {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class AjustePreciosResponseVO {\n");
	    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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
