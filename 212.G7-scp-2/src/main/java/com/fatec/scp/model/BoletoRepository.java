package com.fatec.scp.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoRepository extends CrudRepository<Boleto, Long> {
	public Boleto findByCpf(@Param("cpf") String cpf);
}
