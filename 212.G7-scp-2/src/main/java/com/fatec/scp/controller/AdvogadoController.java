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

import com.fatec.scp.model.Advogado;
import com.fatec.scp.servico.AdvogadoServico;

@Controller
@RequestMapping(path = "/sig")
public class AdvogadoController {
	Logger logger = LogManager.getLogger(AdvogadoController.class);
	@Autowired
	AdvogadoServico servico;

	@GetMapping("/advogados")
	public ModelAndView retornaFormDeConsultaTodosAdvogados() {
		ModelAndView modelAndView = new ModelAndView("consultarAdvogado");
		modelAndView.addObject("advogados", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/advogado")
	public ModelAndView retornaFormDeCadastroDe(Advogado advogado) {
		ModelAndView mv = new ModelAndView("cadastrarAdvogado");
		mv.addObject("advogado", advogado);
		return mv;
	}

	@GetMapping("/advogados/{id}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditaraAvogados(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("atualizarAdvogado");
		modelAndView.addObject("advogado", servico.findById(id)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/advogado/{id}")
	public ModelAndView excluirNoFormDeConsultaAdvogado(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarAdvogado");
		modelAndView.addObject("advogados", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/advogados")
	public ModelAndView save(@Validated Advogado advogado, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarAdvogado");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarAdvogado"); //aqui eu NÃO coloquei um "s" no advogado
		} else {
			modelAndView = servico.saveOrUpdate(advogado);
		}
		return modelAndView;
	}

	@PostMapping("/advogados/{id}")
	public ModelAndView atualizaAdvogado(@PathVariable("id") Long id, @Validated Advogado advogado, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarAdvogado");
		if (result.hasErrors()) {
			advogado.setId(id);
			return new ModelAndView("atualizarAdvogado");
		}
// programacao defensiva - deve-se verificar se o Advogado existe antes de atualizar
		Advogado umAdvogado = servico.findById(id);
		umAdvogado.setCpf(advogado.getCpf());
		umAdvogado.setNome(advogado.getNome());
		umAdvogado.setEmail(advogado.getEmail());
		umAdvogado.setTel(advogado.getTel());
		umAdvogado.setCep(advogado.getCep());//aqui eu coloquei o CEP.
		umAdvogado.setOAB(advogado.getOAB());//aqui eu coloquei o OAB.
		modelAndView = servico.saveOrUpdate(umAdvogado);
		return modelAndView;
	}
}