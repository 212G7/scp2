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

import com.fatec.scp.model.Processo;
import com.fatec.scp.servico.ProcessoServico;

@Controller
@RequestMapping(path = "/sig")
public class ProcessoController {
	Logger logger = LogManager.getLogger(ProcessoController.class);
	@Autowired
	ProcessoServico servico;

	@GetMapping("/processos")
	public ModelAndView retornaFormDeConsultaTodosProcessos() {
		ModelAndView modelAndView = new ModelAndView("consultarProcesso");
		modelAndView.addObject("processos", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/processo")
	public ModelAndView retornaFormDeCadastroDe(Processo processo) {
		ModelAndView mv = new ModelAndView("cadastrarProcesso");
		mv.addObject("processo", processo);
		return mv;
	}

	@GetMapping("/processos/{id}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarProcesso(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("atualizarProcesso");
		modelAndView.addObject("processo", servico.findById(id)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/processo/{id}")
	public ModelAndView excluirNoFormDeConsultaCliente(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarProcesso");
		modelAndView.addObject("processos", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/processos")
	public ModelAndView save(@Validated Processo processo, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarProcesso");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarProcesso"); //aqui eu NÃO coloquei um "s" no Processo
		} else {
			modelAndView = servico.saveOrUpdate(processo);
		}
		return modelAndView;
	}

	@PostMapping("/processos/{id}")
	public ModelAndView atualizaCliente(@PathVariable("id") Long id, @Validated Processo processo, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarProcesso");
		if (result.hasErrors()) {
			Processo.setId(id);
			return new ModelAndView("atualizarProcesso");
		}
// programacao defensiva - deve-se verificar se o Processo existe antes de atualizar
		Processo umProcesso = servico.findById(id);
		umProcesso.setCpf(processo.getCpf());
		umProcesso.setNome(processo.getNome());
		umProcesso.setEmail(processo.getEmail());
		umProcesso.setTel(processo.getTel());
		umProcesso.setCep(processo.getCep());//aqui eu coloquei o CEP.
		modelAndView = servico.saveOrUpdate(umProcesso);
		return modelAndView;
	}
}
