package mx.com.nmp.escenariosdinamicos.elastic.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ElasticProperties {
	
	@Value("${spring.elasticsearch.old.urlbase}")
	private String hostOld;
	@Value("${spring.elasticsearch.old.port}")
	private String portOld;
	@Value("${spring.elasticsearch.old.username}")
	private String userOld;
	@Value("${spring.elasticsearch.old.password}")
	private String passwordOld;
	@Value("${spring.elasticsearch.old.indexgarantias}")
	private String indexGarantiasOld;
	@Value("${spring.elasticsearch.old.scheme}")
	private String schemeOld;
	
	@Value("${spring.elasticsearch.new.urlbase}")
	private String hostNew;
	@Value("${spring.elasticsearch.new.port}")
	private String portNew;
	@Value("${spring.elasticsearch.new.username}")
	private String userNew;
	@Value("${spring.elasticsearch.new.password}")
	private String passwordNew;
	@Value("${spring.elasticsearch.new.scheme}")
	private String schemeNew;
	@Value("${spring.elasticsearch.new.indexventas}")
	private String indexVentasNew;
	
	public String getHostOld() {
		return hostOld;
	}
	public void setHostOld(String hostOld) {
		this.hostOld = hostOld;
	}
	public String getPortOld() {
		return portOld;
	}
	public void setPortOld(String portOld) {
		this.portOld = portOld;
	}
	public String getUserOld() {
		return userOld;
	}
	public void setUserOld(String userOld) {
		this.userOld = userOld;
	}
	public String getPasswordOld() {
		return passwordOld;
	}
	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}
	public String getIndexGarantiasOld() {
		return indexGarantiasOld;
	}
	public void setIndexGarantiasOld(String indexGarantiasOld) {
		this.indexGarantiasOld = indexGarantiasOld;
	}
	public String getSchemeOld() {
		return schemeOld;
	}
	public void setSchemeOld(String schemeOld) {
		this.schemeOld = schemeOld;
	}
	public String getHostNew() {
		return hostNew;
	}
	public void setHostNew(String hostNew) {
		this.hostNew = hostNew;
	}
	public String getPortNew() {
		return portNew;
	}
	public void setPortNew(String portNew) {
		this.portNew = portNew;
	}
	public String getUserNew() {
		return userNew;
	}
	public void setUserNew(String userNew) {
		this.userNew = userNew;
	}
	public String getPasswordNew() {
		return passwordNew;
	}
	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}
	public String getSchemeNew() {
		return schemeNew;
	}
	public void setSchemeNew(String schemeNew) {
		this.schemeNew = schemeNew;
	}
	public String getIndexVentasNew() {
		return indexVentasNew;
	}
	public void setIndexVentasNew(String indexVentasNew) {
		this.indexVentasNew = indexVentasNew;
	}
	
}
