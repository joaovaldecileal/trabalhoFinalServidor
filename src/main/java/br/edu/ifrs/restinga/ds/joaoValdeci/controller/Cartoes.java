/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.joaoValdeci.controller;

import br.edu.ifrs.restinga.ds.joaoValdeci.DAO.CartaoDao;
import br.edu.ifrs.restinga.ds.joaoValdeci.erros.NaoEncontrado;
import br.edu.ifrs.restinga.ds.joaoValdeci.erros.RequisicaoInvalida;
import br.edu.ifrs.restinga.ds.joaoValdeci.modelo.CartaoCredito;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author yk.hjm
 */
@RestController
@RequestMapping(path = "/api")
public class Cartoes {
    @Autowired
    CartaoDao cartaoDao;
    
    
    public void verificaCartao(CartaoCredito cartao){
        if (cartao.getNome() == null || cartao.getNome().isEmpty()) {
            throw new RequisicaoInvalida("digite um nome");
        }
        if (cartao.getLimiteCartao() <= 500) {
            throw new RequisicaoInvalida("digite um limite valido");
        }
        if (cartao.getFinanceira() == null) {
            throw new RequisicaoInvalida("digite uma financeira");
        }
        if (cartao.getPessoa() == null) {
            throw new RequisicaoInvalida("digite uma pessoa para o cartão"); 
        }
        if (cartao.getUsuario() == null) {
            throw new RequisicaoInvalida("digite um usuario");   
        }
        if (cartao.getValidade().before(new Date(System.currentTimeMillis()))) {
            throw new RequisicaoInvalida("data invalida");
        }
    }
    
     public Date Validade() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,3);
        return calendar.getTime();
    }
    
    @RequestMapping(path = "/cartoes/", method = RequestMethod.GET)
    public Iterable<CartaoCredito> listar() {
        return cartaoDao.findAll();
    }
    
    @RequestMapping(path = "/cartoes/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CartaoCredito inserir(@RequestBody CartaoCredito cartao) {
        cartao.setId(0);
        cartao.setValidade(Validade());
        verificaCartao(cartao);
        return cartaoDao.save(cartao); 
    }
    
    @RequestMapping(path = "/cartoes/{id}", method = RequestMethod.GET)
    public CartaoCredito recuperar(@PathVariable int id) {
        Optional<CartaoCredito> findById = cartaoDao.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }
    
    @RequestMapping(path = "/cartoes/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody CartaoCredito cartaoNovo){
       
             CartaoCredito cartaoAntigo = this.recuperar(id);
             cartaoAntigo.setNome(cartaoNovo.getNome());
             cartaoAntigo.setLimiteCartao(cartaoNovo.getLimiteCartao());
             cartaoAntigo.setFinanceira(cartaoNovo.getFinanceira());
             cartaoAntigo.setPessoas(cartaoNovo.getPessoa());
       
            cartaoDao.save(cartaoAntigo);
      
    }
    
    @RequestMapping(path = "/cartoes/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id){
        if (cartaoDao.existsById(id)){
           cartaoDao.deleteById(id);
        }else {
            throw new NaoEncontrado("Não encontrado");
        }
    }
    
//    //acessa pessoas no cartao
//    
//    @RequestMapping(path = "/cartoes/{idCartao}/pessoas/", method = RequestMethod.GET)
//    public Iterable<Pessoa> listarPessoas(@PathVariable int idCartao){
//        return this.recuperar(idCartao).getPessoas();
//    }
//    
//    //insere pessoas  no cartao
//    
//    @RequestMapping(path = "/cartoes/{idCartao}/pessoas/",method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void inserirPessoa(@PathVariable int idCartao,@RequestBody Pessoa pessoa){
//        CartaoCredito cartao = this.recuperar(idCartao);
//        cartao.getPessoas().add(pessoa);
//        cartaoDao.save(cartao); 
//    }
//    
//    // apaga pessoas no cartão
//    
//    @RequestMapping(path= "/cartoes/{idCartao}/pessoas/{id}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.OK)
//    public void apagarPessoas(@PathVariable int idCartao,@PathVariable int id){
//        Pessoa pessoaAchada=null;
//        CartaoCredito cartao = this.recuperar(idCartao);
//        List<Pessoa> pessoas = cartao.getPessoas();
//        for (Pessoa pessoaLista : pessoas){
//            if(id==pessoaLista.getId())
//                pessoaAchada=pessoaLista;
//        }
//        if(pessoaAchada!=null){
//            cartao.getPessoas().remove(pessoaAchada);
//            cartaoDao.save(cartao);
//        }else 
//            throw new NaoEncontrado("Não encontrado");
//    }
//    
//    //acessa financeiras no cartão
//    
//    @RequestMapping(path = "/cartoes/{idCartao}/financeiras/", method = RequestMethod.GET)
//    public Iterable<Financeira> listarFinanceiras(@PathVariable int idCartao){
//        return this.recuperar(idCartao).getFinanceiras();
//    }
//      
//    //insere financeiras no cartão
//    
//    @RequestMapping(path = "/cartoes/{idCartao}/financeiras/",method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void inserirFinanceiras(@PathVariable int idCartao,@RequestBody Financeira financeira){
//        CartaoCredito cartao = this.recuperar(idCartao);
//        cartao.getFinanceiras().add(financeira);
//        cartaoDao.save(cartao);
//        
//    }
//    
//    //apaga financeiras no cartão
//    
//    @RequestMapping(path= "/cartoes/{idCartao}/financeiras/{id}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.OK)
//    public void apagarFinanceira(@PathVariable int idCartao,@PathVariable int id){
//        Financeira financeiraAchado=null;
//        CartaoCredito cartao = this.recuperar(idCartao);
//        List<Financeira> cartoes = cartao.getFinanceiras();
//        for (Financeira financeiraLista : cartoes){
//            if(id==financeiraLista.getId())
//                financeiraAchado=financeiraLista;
//        }
//        if(financeiraAchado!=null){
//            cartao.getFinanceiras().remove(financeiraAchado);
//            cartaoDao.save(cartao);
//        }else 
//            throw new NaoEncontrado("Não encontrado");
//    }
}
