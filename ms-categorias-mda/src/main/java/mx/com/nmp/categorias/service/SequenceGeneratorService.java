package mx.com.nmp.categorias.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import mx.com.nmp.categorias.entity.DatabaseSequenceEntity;
import mx.com.nmp.categorias.utils.Constantes;


@Repository
public class SequenceGeneratorService {
	
	@Autowired
	private MongoOperations mongoOperation;

	public long generateSequence(String seqName) {

		Query query = new Query(Criteria.where(Constantes.ID).is(seqName));

		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		DatabaseSequenceEntity counter = mongoOperation.findAndModify(query, new Update().inc("seq", 1), options,
				DatabaseSequenceEntity.class);

		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}

}
