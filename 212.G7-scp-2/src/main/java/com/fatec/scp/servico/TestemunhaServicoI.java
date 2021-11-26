package com.fatec.scp.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scp.model.Testemunha;
import com.fatec.scp.model.TestemunhaRepository;
import com.fatec.scp.model.Endereco;
import com.fatec.scp.model.EnderecoRepository;

@Service
public class TestemunhaServicoI implements TestemunhaServico {
	Logger logger = LogManager.getLogger(TestemunhaServicoI.class);
	@Autowired
	private TestemunhaRepository testemunhaRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Iterable<Testemunha> findAll() {
		return testemunhaRepository.findAll();
	}

	public Testemunha findByCpf(String cpf) {
		return testemunhaRepository.findByCpf(cpf);
	}

	public void deleteById(Long id) {
		testemunhaRepository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Testemunha findById(Long id) {
		return testemunhaRepository.findById(id).get();
	}

	public ModelAndView saveOrUpdate(Testemunha testemunha) {
		ModelAndView modelAndView = new ModelAndView("consultarTestemunha");
		try {
			Endereco endereco = obtemEndereco(testemunha.getCep());
			if (endereco != null) {
//testemunha.setDataCadastro(new DateTime());
				endereco.setCpf(testemunha.getCpf());
				enderecoRepository.save(endereco);
				testemunha.setEndereco(endereco);
				testemunhaRepository.save(testemunha);
				logger.info(">>>>>> 4. comando save executado ");
				modelAndView.addObject("testemunhas", testemunhaRepository.findAll());
			}
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarTestemunha");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - testemunha já cadastrado.");
				logger.info(">>>>>> 5. testemunha ja cadastrado ==> " + e.getMessage());
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
