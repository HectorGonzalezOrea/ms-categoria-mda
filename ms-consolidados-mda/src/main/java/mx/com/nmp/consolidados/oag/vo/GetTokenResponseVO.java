package mx.com.nmp.consolidados.oag.vo;

public class GetTokenResponseVO {

	private Integer expires_in;
	private String token_type;
	private String refresh_token;
	private String access_token;
	
	public Integer getExpires_in() {
		return expires_in;
	}
	
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
	
	public String getToken_type() {
		return token_type;
	}
	
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	
	public String getRefresh_token() {
		return refresh_token;
	}
	
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	
	public String getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	@Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class GetTokenResponseVO {\n");
	    sb.append("    expires_in: ").append(toIndentedString(expires_in)).append("\n");
	    sb.append("    token_type: ").append(toIndentedString(token_type)).append("\n");
	    sb.append("    refresh_token: ").append(toIndentedString(refresh_token)).append("\n");
	    sb.append("    access_token: ").append(toIndentedString(access_token)).append("\n");
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
