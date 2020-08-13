package mx.com.nmp.valormonte.elastic.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ElasticProperties {
	
	@Value("${spring.elasticsearch.url}")
	private String host;
	@Value("${spring.elasticsearch.port}")
	private String port;
	@Value("${spring.elasticsearch.username}")
	private String user;
	@Value("${spring.elasticsearch.password}")
	private String password;
	@Value("${spring.elasticsearch.indexgarantias}")
	private String indexGarantia;
	@Value("${spring.elasticsearch.scheme}")
	private String scheme;
	
	public String getHost() {
		return host;
	}
	public String getPort() {
		return port;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
	public String getIndexGarantia() {
		return indexGarantia;
	}
	public String getScheme() {
		return scheme;
	}
	


}
