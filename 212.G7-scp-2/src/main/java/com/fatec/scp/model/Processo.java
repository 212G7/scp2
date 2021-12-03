package com.fatec.scp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Entity
public class Processo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@CPF(message = "CPF inválido")
	@Column(unique = true) // nao funciona com @Valid tem que tratar na camada de persistencia
	private String cpf;
	@NotNull
	@Size(min = 1, max = 50, message = "Nome deve ser preenchido")
	private String nome;
	//@Email(message = "E-mail inválido")
	//private String email;
	@NotNull
	private String advogado;
	@NotNull
	private String testemunha;
//	@NotNull
//	private String cep;
	@NotNull
	private String descricao;
	@NotNull
	private String data;
	@NotNull
	private String conclusao;
	@ManyToOne
	private Endereco endereco;
	private String dataCadastro;
	private String dataUltimaTransacao;

	public Processo() {
	}

	public Processo(String CPF, String nome, String advogado, String descricao, String testemunha, String data, String conclusao, Endereco endereco) {
		super();
		this.cpf = CPF;
		this.nome = nome;
//		this.cep = cep;
		this.advogado = advogado;
		this.descricao = descricao;
		this.testemunha = testemunha;
		this.data = data;
		this.conclusao = conclusao;
		this.endereco = endereco;
		DateTime dataAtual = new DateTime();
		setDataCadastro(dataAtual);
	}

	public Long getId() {
		return id;
	}

	public static void setId(Long id) {
		return;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
//	public String getCep() {
//		return cep;
//	}

//	public void setCep(String cep) {
//		this.cep = cep;
//	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAdvogado() {
		return advogado;
	}

	public void setAdvogado(String advogado) {
		this.advogado = advogado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getTestemunha() {
		return testemunha;
	}

	public void setTestemunha(String testemunha) {
		this.testemunha = testemunha;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getConclusao() {
		return conclusao;
	}

	public void setConclusao(String conclusao) {
		this.conclusao = conclusao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(DateTime dataAtual) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY/MM/dd");
		this.dataCadastro = dataAtual.toString(fmt);
		setDataUltimaTransacao(dataAtual);
	}

	public String getDataUltimaTransacao() {
		return dataUltimaTransacao;
	}

	public void setDataUltimaTransacao(DateTime data) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY/MM/dd");
		this.dataUltimaTransacao = data.toString(fmt);
	}
// omitidos hashcode e equals
// omitido toString
}