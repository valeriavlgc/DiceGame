package com.example.demo.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.document.Sequence;

@Service
public class SequenceService {

//Para qué sirve?	
@Autowired	
MongoOperations mongoOperations;

public int setNextSequence(String sequence) {

Query q = new Query(Criteria.where("id").is(sequence));
// incrementa la secuencia en 1
Update u = new Update().inc("value", 1);
//modificación en el documento
Sequence counter = mongoOperations
                   .findAndModify(q, u,FindAndModifyOptions.options()
		                                                   .returnNew(true)
		                                                   .upsert(true), Sequence.class);

    return !Objects.isNull(counter) ? counter.getValue() : 1;
	
	
}

}
