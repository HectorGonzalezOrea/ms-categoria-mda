package mx.com.nmp.valormonte.elastic.controller;

import org.springframework.beans.factory.annotation.Value;

public abstract class ElasticBaseController {

	@Value("${spring.elasticsearch.urlbase}")
	protected String urlbase;

	@Value("${spring.elasticsearch.context}")
	protected String service;

	@Value("${spring.elasticsearch.username}")
	protected String username;

	@Value("${spring.elasticsearch.password}")
	protected String password;
	
}
