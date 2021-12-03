package com.fatec.scp.servico;

import org.springframework.web.servlet.ModelAndView;
import com.fatec.scp.model.Processo;
//import com.fatec.scp.model.Endereco;

public interface ProcessoServico {
	public Iterable<Processo> findAll();

	public Processo findByCpf(String cpf);

	public void deleteById(Long id);

	public Processo findById(Long id);

	public ModelAndView saveOrUpdate(Processo processo);

//	public Endereco obtemEndereco(String cep);
}
