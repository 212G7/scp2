package com.fatec.scp.servico;

import org.springframework.web.servlet.ModelAndView;
import com.fatec.scp.model.Testemunha;
import com.fatec.scp.model.Endereco;

public interface TestemunhaServico {
	public Iterable<Testemunha> findAll();

	public Testemunha findByCpf(String cpf);

	public void deleteById(Long id);

	public Testemunha findById(Long id);

	public ModelAndView saveOrUpdate(Testemunha testemunha);

	public Endereco obtemEndereco(String cep);
}
