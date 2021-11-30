package com.fatec.scp.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scp.model.Audiencia;
import com.fatec.scp.model.AudienciaRepository;
import com.fatec.scp.model.Endereco;
import com.fatec.scp.model.EnderecoRepository;

@Service
public class AudienciaServicoI implements AudienciaServico {
	Logger logger = LogManager.getLogger(AudienciaServicoI.class);
	@Autowired
	private AudienciaRepository audienciaRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Iterable<Audiencia> findAll() {
		return audienciaRepository.findAll();
	}

	public Audiencia findByCpf(String cpf) {
		return audienciaRepository.findByCpf(cpf);
	}

	public void deleteById(Long id) {
		audienciaRepository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Audiencia findById(Long id) {
		return audienciaRepository.findById(id).get();
	}

	public ModelAndView saveOrUpdate(Audiencia audiencia) {
		ModelAndView modelAndView = new ModelAndView("consultarAudiencia");
		try {
			Endereco endereco = obtemEndereco(audiencia.getCep());
			if (endereco != null) {
//audiencia.setDataCadastro(new DateTime());
				endereco.setCpf(audiencia.getCpf());
				enderecoRepository.save(endereco);
				audiencia.setEndereco(endereco);
				audienciaRepository.save(audiencia);
				logger.info(">>>>>> 4. comando save executado ");
				modelAndView.addObject("audiencias", audienciaRepository.findAll());
			}
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarCliente");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - audiencia já cadastrado.");
				logger.info(">>>>>> 5. audiencia ja cadastrado ==> " + e.getMessage());
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
