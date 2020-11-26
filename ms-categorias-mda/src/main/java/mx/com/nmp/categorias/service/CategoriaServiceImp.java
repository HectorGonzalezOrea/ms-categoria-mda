package mx.com.nmp.categorias.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.com.nmp.categorias.entity.CategoriaEntity;
import mx.com.nmp.categorias.model.Categoria;
import mx.com.nmp.categorias.model.ResponseGetCategorias;
import mx.com.nmp.categorias.utils.Constantes;

@Service
public class CategoriaServiceImp{

	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImp.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
//	@Autowired
//	private CategoriaService userRepository;
	/*
	 * Crear categoria
	 */
	public Boolean configurarCategorias(ResponseGetCategorias categoriasbody) {
		Boolean insertado = false;
		//List<CategoriaEntity> aux = mongoTemplate.findAll(CategoriaEntity.class);
		Query query = new Query();
//		log.info("depurando datos [{}]"+aux.size());
		mongoTemplate.findAllAndRemove(query, CategoriaEntity.class);
//		if (!aux.isEmpty()) {
//			aux.stream().forEach(i -> mongoTemplate.remove(i));
//		}
//		userRepository.deleteAll(aux);
		log.info("Datos depurados...");
		if (categoriasbody != null) {
			List<Categoria> categoriasYOtros=new ArrayList<>();
			categoriasYOtros.addAll(categoriasbody.getCategorias());
			categoriasYOtros.addAll(categoriasbody.getOtros());
			log.info("insertando categorias");
			CategoriaEntity categ = new CategoriaEntity();
			categoriasYOtros.stream().forEach(i -> {
				Long id = sequenceGeneratorService.generateSequence(Constantes.CATEGORIA_SEQ);
				categ.setIdCategoria(id);
				categ.setDescripcion(i.getDescripcion());
				categ.setTipo(i.getTipo().toUpperCase());
				mongoTemplate.save(categ);
			});
			insertado=true;
		}
		return insertado;
	}
	
	/**
	 * consultar categorias
	 * */
	public List<CategoriaEntity> consultaCategorias(){
		log.info("consulta categorias");
		List<CategoriaEntity> getCategorias=null;
		Query query = new Query();
		Criteria aux = Criteria.where(Constantes.TIPO).is(Constantes.CATEGORIA);
		query.addCriteria(aux);
		getCategorias=mongoTemplate.find(query, CategoriaEntity.class);
		if(!getCategorias.isEmpty()){
			return getCategorias;
		}
		return getCategorias;
	}
	
	/**
	 * Consultar otros
	 * */
	public List<CategoriaEntity> consultaOtros(){
		log.info("consulta categorias de tipo otros");
		List<CategoriaEntity> getCategorias=null;
		Query query = new Query();
		Criteria aux = Criteria.where(Constantes.TIPO).is(Constantes.OTRO);
		query.addCriteria(aux);
		getCategorias=mongoTemplate.find(query, CategoriaEntity.class);
		if(!getCategorias.isEmpty()){
			return getCategorias;
		}
		return getCategorias;
	}
}
