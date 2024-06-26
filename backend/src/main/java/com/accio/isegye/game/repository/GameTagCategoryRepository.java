package com.accio.isegye.game.repository;

import com.accio.isegye.game.entity.GameTagCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTagCategoryRepository extends JpaRepository<GameTagCategory, Integer> {

}
