package com.fatec.scp.servico;

import org.springframework.web.servlet.ModelAndView;
import com.fatec.scp.model.Audiencia;
import com.fatec.scp.model.Endereco;

public interface AudienciaServico {
	public Iterable<Audiencia> findAll();

	public Audiencia findByCpf(String cpf);

	public void deleteById(Long id);

	public Audiencia findById(Long id);

	public ModelAndView saveOrUpdate(Audiencia audiencia);

	public Endereco obtemEndereco(String cep);
}
