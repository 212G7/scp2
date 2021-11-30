package com.fatec.scp.servico;

import org.springframework.web.servlet.ModelAndView;
import com.fatec.scp.model.Boleto;
import com.fatec.scp.model.Endereco;

public interface BoletoServico {
	public Iterable<Boleto> findAll();

	public Boleto findByCpf(String cpf);

	public void deleteById(Long id);

	public Boleto findById(Long id);

	public ModelAndView saveOrUpdate(Boleto boleto);

	public Endereco obtemEndereco(String cep);
}
