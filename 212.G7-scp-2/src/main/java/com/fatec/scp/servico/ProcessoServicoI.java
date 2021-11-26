package com.fatec.scp.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scp.model.Processo;
import com.fatec.scp.model.ProcessoRepository;
import com.fatec.scp.model.Endereco;
import com.fatec.scp.model.EnderecoRepository;

@Service
public class ProcessoServicoI implements ProcessoServico {
	Logger logger = LogManager.getLogger(ProcessoServicoI.class);
	@Autowired
	private ProcessoRepository processoRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Iterable<Processo> findAll() {
		return processoRepository.findAll();
	}

	public Processo findByCpf(String cpf) {
		return processoRepository.findByCpf(cpf);
	}

	public void deleteById(Long id) {
		processoRepository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Processo findById(Long id) {
		return processoRepository.findById(id).get();
	}

	public ModelAndView saveOrUpdate(Processo processo) {
		ModelAndView modelAndView = new ModelAndView("consultarProcesso");
		try {
			Endereco endereco = obtemEndereco(processo.getCep());
			if (endereco != null) {
//Processo.setDataCadastro(new DateTime());
				endereco.setCpf(processo.getCpf());
				enderecoRepository.save(endereco);
				processo.setEndereco(endereco);
				processoRepository.save(processo);
				logger.info(">>>>>> 4. comando save executado ");
				modelAndView.addObject("processos", processoRepository.findAll());
			}
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarCliente");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - processo já cadastrado.");
				logger.info(">>>>>> 5. processo ja cadastrado ==> " + e.getMessage());
			} else {
				modelAndView.addObject("message", "Erro não esperado - contate o administrador");
				logger.error(">>>>>> 5. erro nao esperado ==> " + e.getMessage());
			}
		}
		return modelAndView;
	}

	public Endereco obtemEndereco(String cep) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{cep}/json/";
		Endereco endereco = template.getForObject(url, Endereco.class, cep);
		logger.info(">>>>>> 3. obtem endereco ==> " + endereco.toString());
		return endereco;
	}
}
