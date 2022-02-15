package com.example.demo.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.document.Roll;
import com.example.demo.model.RollModel;

@Repository
@Transactional
public interface RollsRepository extends MongoRepository<Roll, String> {

public List<Roll> findRollByUserId(int id_jugador);



}
