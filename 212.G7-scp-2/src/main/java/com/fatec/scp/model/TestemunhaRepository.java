package com.fatec.scp.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestemunhaRepository extends CrudRepository<Testemunha, Long> {
	public Testemunha findByCpf(@Param("cpf") String cpf);
}
