package com.fatec.scp.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scp.model.Boleto;
import com.fatec.scp.model.BoletoRepository;
import com.fatec.scp.model.Endereco;
import com.fatec.scp.model.EnderecoRepository;

@Service
public class BoletoServicoI implements BoletoServico {
	Logger logger = LogManager.getLogger(BoletoServicoI.class);
	@Autowired
	private BoletoRepository boletoRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Iterable<Boleto> findAll() {
		return boletoRepository.findAll();
	}

	public Boleto findByCpf(String cpf) {
		return boletoRepository.findByCpf(cpf);
	}

	public void deleteById(Long id) {
		boletoRepository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Boleto findById(Long id) {
		return boletoRepository.findById(id).get();
	}

	public ModelAndView saveOrUpdate(Boleto boleto) {
		ModelAndView modelAndView = new ModelAndView("consultarBoleto");
		try {
			Endereco endereco = obtemEndereco(boleto.getCep());
			if (endereco != null) {
//Boleto.setDataCadastro(new DateTime());
				endereco.setCpf(boleto.getCpf());
				enderecoRepository.save(endereco);
				boleto.setEndereco(endereco);
				boletoRepository.save(boleto);
				logger.info(">>>>>> 4. comando save executado ");
				modelAndView.addObject("Boletos", boletoRepository.findAll());
			}
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarBoleto");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - boleto já cadastrado.");
				logger.info(">>>>>> 5. boleto ja cadastrado ==> " + e.getMessage());
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
