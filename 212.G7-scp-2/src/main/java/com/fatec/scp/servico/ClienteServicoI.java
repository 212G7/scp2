package com.fatec.scp.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scp.model.Cliente;
import com.fatec.scp.model.ClienteRepository;
import com.fatec.scp.model.Endereco;
import com.fatec.scp.model.EnderecoRepository;

@Service
public class ClienteServicoI implements ClienteServico {
	Logger logger = LogManager.getLogger(ClienteServicoI.class);
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Iterable<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente findByCpf(String cpf) {
		return clienteRepository.findByCpf(cpf);
	}

	public void deleteById(Long id) {
		clienteRepository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Cliente findById(Long id) {
		return clienteRepository.findById(id).get();
	}

	public ModelAndView saveOrUpdate(Cliente cliente) {
		ModelAndView modelAndView = new ModelAndView("consultarCliente");
		try {
			Endereco endereco = obtemEndereco(cliente.getCep());
			if (endereco != null) {
//cliente.setDataCadastro(new DateTime());
				endereco.setCpf(cliente.getCpf());
				enderecoRepository.save(endereco);
				cliente.setEndereco(endereco);
				clienteRepository.save(cliente);
				logger.info(">>>>>> 4. comando save executado ");
				modelAndView.addObject("clientes", clienteRepository.findAll());
			}
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarCliente");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - cliente já cadastrado.");
				logger.info(">>>>>> 5. cliente ja cadastrado ==> " + e.getMessage());
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
