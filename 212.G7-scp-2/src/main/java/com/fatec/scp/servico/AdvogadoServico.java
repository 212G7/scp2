package com.fatec.scp.servico;

import org.springframework.web.servlet.ModelAndView;
import com.fatec.scp.model.Advogado;
import com.fatec.scp.model.Endereco;

public interface AdvogadoServico {
	public Iterable<Advogado> findAll();

	public Advogado findByCpf(String cpf);

	public void deleteById(Long id);

	public Advogado findById(Long id);

	public ModelAndView saveOrUpdate(Advogado advogado);

	public Endereco obtemEndereco(String cep);
}
