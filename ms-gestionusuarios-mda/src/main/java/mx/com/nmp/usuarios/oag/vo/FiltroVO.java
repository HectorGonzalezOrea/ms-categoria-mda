package mx.com.nmp.usuarios.oag.vo;

public class FiltroVO {

	private String nombre;
	private String apellidos;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	@Override
	public String toString() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("FiltroVO {\n");
		 sb.append("nombre: ").append(toIndentedString(nombre)).append("\n");
		 sb.append("apellidos: ").append(toIndentedString(apellidos)).append("\n");
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
