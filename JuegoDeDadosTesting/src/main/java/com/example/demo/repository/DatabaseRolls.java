package com.example.demo.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Roll;

@Repository
@Transactional
public interface DatabaseRolls extends JpaRepository<Roll, Integer> {

}
