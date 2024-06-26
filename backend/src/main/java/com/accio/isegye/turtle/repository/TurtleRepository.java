package com.accio.isegye.turtle.repository;

import com.accio.isegye.turtle.entity.Turtle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TurtleRepository extends JpaRepository<Turtle, Integer> {

    @Query("select t.id from Turtle t where t.isWorking=?1")
    List<Integer> findIdByIsWorking(int i);

    @Query("select t from Turtle t where t.store.id = ?1 and t.isWorking = ?2")
    List<Turtle> findByStoreIdAndIsWorking(int storeId, int isWorking);
}
