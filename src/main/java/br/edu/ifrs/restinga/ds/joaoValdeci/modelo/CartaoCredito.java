/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.joaoValdeci.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author yk.hjm
 */
@Entity
public class CartaoCredito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "MM/yyyy",locale = "UTC-03")
    private Date validade;
    private float limiteCartao;
//    @JsonIgnore
    @ManyToOne
    private Financeira financeira;
//    @JsonIgnore
    @ManyToOne
    private Pessoa pessoa;
//    @JsonIgnore
    @ManyToOne
    private Usuario usuario;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date Validade) {
        this.validade = Validade;
    }
    
    public float getLimiteCartao() {
        return limiteCartao;
    }
    public void setLimiteCartao(float limite_cartao) {
        this.limiteCartao = limite_cartao;
    }

    public Financeira getFinanceira() {
        return financeira;
    }

    public void setFinanceira(Financeira financeira) {
        this.financeira = financeira;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoas(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    } 
}
