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
public class Testemunha {
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
	private String cep;
	@NotNull
	private Long processo;
	@ManyToOne
	private Endereco endereco;
	private String dataCadastro;
	private String dataUltimaTransacao;

	public Testemunha() {
	}

	public Testemunha (String CPF, String nome, String cep, Long processo, Endereco endereco) {
		super();
		this.cpf = CPF;
		this.nome = nome;
//		this.email = email;
		this.processo = processo;
		this.cep = cep;
		this.endereco = endereco;
		DateTime dataAtual = new DateTime();
		setDataCadastro(dataAtual);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	//public String getEmail() {
		//return email;
	//}

	//public void setEmail(String email) {
		//this.email = email;
	//}

	public Long getProcesso() {
		return processo;
	}

	public void setProcesso(Long processo) {
		this.processo = processo;
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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
