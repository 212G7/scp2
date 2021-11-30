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

import com.fatec.scp.model.Audiencia;
import com.fatec.scp.servico.AudienciaServico;

@Controller
@RequestMapping(path = "/sig")
public class AudienciaController {
	Logger logger = LogManager.getLogger(AudienciaController.class);
	@Autowired
	AudienciaServico servico;

	@GetMapping("/audiencias")
	public ModelAndView retornaFormDeConsultaTodosClientes() {
		ModelAndView modelAndView = new ModelAndView("consultarAudiencia");
		modelAndView.addObject("audiencias", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/audiencia")
	public ModelAndView retornaFormDeCadastroDe(Audiencia audiencia) {
		ModelAndView mv = new ModelAndView("cadastrarAudiencia");
		mv.addObject("audiencia", audiencia);
		return mv;
	}

	@GetMapping("/audiencias/{id}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarAudiencia(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("atualizarAudiencia");
		modelAndView.addObject("audiencia", servico.findById(id)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/audiencia/{id}")
	public ModelAndView excluirNoFormDeConsultaAudiencia(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarAudiencia");
		modelAndView.addObject("audiencias", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/audiencias")
	public ModelAndView save(@Validated Audiencia audiencia, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarAudiencia");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarAudiencia"); //aqui eu NÃO coloquei um "s" no audiencia
		} else {
			modelAndView = servico.saveOrUpdate(audiencia);
		}
		return modelAndView;
	}

	@PostMapping("/audiencias/{id}")
	public ModelAndView atualizaCliente(@PathVariable("id") Long id, @Validated Audiencia audiencia, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarAudiencia");
		if (result.hasErrors()) {
			audiencia.setId(id);
			return new ModelAndView("atualizarAudiencia");
		}
// programacao defensiva - deve-se verificar se o Cliente existe antes de atualizar
		Audiencia umAudiencia = servico.findById(id);
		umAudiencia.setCpf(audiencia.getCpf());
		umAudiencia.setNome(audiencia.getNome());
		umAudiencia.setEmail(audiencia.getEmail());
		umAudiencia.setTel(audiencia.getTel());
		umAudiencia.setCep(audiencia.getCep());//aqui eu coloquei o CEP.
		modelAndView = servico.saveOrUpdate(umAudiencia);
		return modelAndView;
	}
}
