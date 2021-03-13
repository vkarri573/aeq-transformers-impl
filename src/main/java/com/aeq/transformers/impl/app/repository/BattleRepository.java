package com.aeq.transformers.impl.app.repository;

import com.aeq.transformers.impl.app.model.Transformer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleRepository extends CrudRepository<Transformer, Long> {
}
