package com.fatec.scp.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienciaRepository extends CrudRepository<Audiencia, Long> {
	public Audiencia findByCpf(@Param("cpf") String cpf);
}
