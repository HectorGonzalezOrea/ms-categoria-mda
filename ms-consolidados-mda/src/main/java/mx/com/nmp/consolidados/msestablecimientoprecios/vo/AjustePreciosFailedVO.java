package mx.com.nmp.consolidados.msestablecimientoprecios.vo;

public class AjustePreciosFailedVO {

	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class AjustePreciosFailedVO {\n");
	    sb.append("    code: ").append(toIndentedString(code)).append("\n");
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
