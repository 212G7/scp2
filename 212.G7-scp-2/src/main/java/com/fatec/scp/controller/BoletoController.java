package com.fatec.scp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.scp.model.Boleto;
import com.fatec.scp.servico.BoletoServico;

@Controller
@RequestMapping(path = "/sig")
public class BoletoController {
	Logger logger = LogManager.getLogger(BoletoController.class);
	@Autowired
	BoletoServico servico;

	@GetMapping("/boletos")
	public ModelAndView retornaFormDeConsultaTodosBoletos() {
		ModelAndView modelAndView = new ModelAndView("consultarBoleto");
		modelAndView.addObject("boletos", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/boleto")
	public ModelAndView retornaFormDeCadastroDe(Boleto boleto) {
		ModelAndView mv = new ModelAndView("cadastrarBoleto");
		mv.addObject("boleto", boleto);
		return mv;
	}

	@GetMapping("/boletos/{id}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarCliente(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("atualizarBoleto");
		modelAndView.addObject("boleto", servico.findById(id)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/boleto/{id}")
	public ModelAndView excluirNoFormDeConsultaBoleto(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarBoleto");
		modelAndView.addObject("boletos", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/boletos")
	public ModelAndView save(@Validated Boleto boleto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarBoleto");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarBoleto"); //aqui eu NÃO coloquei um "s" no boleto
		} else {
			modelAndView = servico.saveOrUpdate(boleto);
		}
		return modelAndView;
	}

	@PostMapping("/boletos/{id}")
	public ModelAndView atualizaBoleto(@PathVariable("id") Long id, @Validated Boleto boleto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarBoleto");
		if (result.hasErrors()) {
			boleto.setId(id);
			return new ModelAndView("atualizarBoleto");
		}
// programacao defensiva - deve-se verificar se o boleto existe antes de atualizar
		Boleto umBoleto = servico.findById(id);
		umBoleto.setCpf(boleto.getCpf());
		umBoleto.setNome(boleto.getNome());
//		umBoleto.setEmail(boleto.getEmail());
		umBoleto.setValor(boleto.getValor());
		umBoleto.setProcesso(boleto.getProcesso());
//		umBoleto.setCep(boleto.getCep());//aqui eu coloquei o CEP.
		modelAndView = servico.saveOrUpdate(umBoleto);
		return modelAndView;
	}
}
