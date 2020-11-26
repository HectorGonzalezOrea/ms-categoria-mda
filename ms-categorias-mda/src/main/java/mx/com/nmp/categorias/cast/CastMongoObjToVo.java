package mx.com.nmp.categorias.cast;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.nmp.categorias.entity.CategoriaEntity;
import mx.com.nmp.categorias.model.Categoria;

@Component
public class CastMongoObjToVo {

	public List<Categoria> convierteDatos(List<CategoriaEntity> entities) {
		List<Categoria> lstCategorias = new ArrayList<>();
		entities.stream().forEach(entidadDB -> {
			Categoria cat = new Categoria();
			cat.setIdCategoria(entidadDB.getIdCategoria());
			cat.setTipo(entidadDB.getTipo());
			cat.setDescripcion(entidadDB.getDescripcion());
			lstCategorias.add(cat);
		});
		return lstCategorias;
	}
}
