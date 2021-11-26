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

import com.fatec.scp.model.Testemunha;
import com.fatec.scp.servico.TestemunhaServico;

@Controller
@RequestMapping(path = "/sig")
public class TestemunhaController {
	Logger logger = LogManager.getLogger(TestemunhaController.class);
	@Autowired
	TestemunhaServico servico;

	@GetMapping("/testemunhas")
	public ModelAndView retornaFormDeConsultaTodosTestemunhas() {
		ModelAndView modelAndView = new ModelAndView("consultarTestemunha");
		modelAndView.addObject("testemunhas", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/testemunha")
	public ModelAndView retornaFormDeCadastroDe(Testemunha testemunha) {
		ModelAndView mv = new ModelAndView("cadastrarTestemunha");
		mv.addObject("testemunha", testemunha);
		return mv;
	}

	@GetMapping("/testemunhas/{id}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarTestemunha(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("atualizarTestemunha");
		modelAndView.addObject("testemunha", servico.findById(id)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/testemunha/{id}")
	public ModelAndView excluirNoFormDeConsultaTestemunha(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarTestemunha");
		modelAndView.addObject("testemunhas", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/testemunhas")
	public ModelAndView save(@Validated Testemunha testemunha, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarTestemunha");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarTestemunha"); //aqui eu NÃO coloquei um "s" no Testemunha
		} else {
			modelAndView = servico.saveOrUpdate(testemunha);
		}
		return modelAndView;
	}

	@PostMapping("/testemunhas/{id}")
	public ModelAndView atualizaTestemunha(@PathVariable("id") Long id, @Validated Testemunha testemunha, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarTestemunha");
		if (result.hasErrors()) {
			testemunha.setId(id);
			return new ModelAndView("atualizarTestemunha");
		}
// programacao defensiva - deve-se verificar se o Testemunha existe antes de atualizar
		Testemunha umTestemunha = servico.findById(id);
		umTestemunha.setCpf(testemunha.getCpf());
		umTestemunha.setNome(testemunha.getNome());
		umTestemunha.setEmail(testemunha.getEmail());
		umTestemunha.setTel(testemunha.getTel());
		umTestemunha.setCep(testemunha.getCep());//aqui eu coloquei o CEP.
		modelAndView = servico.saveOrUpdate(umTestemunha);
		return modelAndView;
	}
}
