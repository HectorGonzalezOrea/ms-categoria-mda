package mx.com.nmp.escenariosdinamicos.elastic.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ElasticProperties {
	@Value("${spring.elasticsearch.urlbase}")
	private String host;
	@Value("${spring.elasticsearch.port}")
	private String port;
	@Value("${spring.elasticsearch.username}")
	private String user;
	@Value("${spring.elasticsearch.password}")
	private String password;
	@Value("${spring.elasticsearch.index}")
	private String index;
	@Value("${spring.elasticsearch.scheme}")
	private String scheme;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

}
