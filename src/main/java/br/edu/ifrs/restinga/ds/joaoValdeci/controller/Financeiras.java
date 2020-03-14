/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.joaoValdeci.controller;

import br.edu.ifrs.restinga.ds.joaoValdeci.DAO.FinanceiraDao;
import br.edu.ifrs.restinga.ds.joaoValdeci.erros.NaoEncontrado;
import br.edu.ifrs.restinga.ds.joaoValdeci.erros.RequisicaoInvalida;
import br.edu.ifrs.restinga.ds.joaoValdeci.modelo.Financeira;
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
public class Financeiras {
    @Autowired
    FinanceiraDao financeiraDao;
    
    public void verificaFinanceira(Financeira financeira){
        if (financeira.getNome() == null || financeira.getNome().isEmpty()) {
            throw new RequisicaoInvalida("digite um nome");
        }
        if (financeira.getEndereco() == null || financeira.getEndereco().isEmpty()) {
            throw new RequisicaoInvalida("digite um endereço");
        }
        if (financeira.getCnpj() == null || financeira.getCnpj().isEmpty()) {
            throw new RequisicaoInvalida("digite um cnpj");
        }
        if (financeira.getTelefone() == null || financeira.getTelefone().isEmpty()) {
            throw new RequisicaoInvalida("digite um telefone");
        }
    }
    
    @RequestMapping(path = "/financeiras/", method = RequestMethod.GET)
    public Iterable<Financeira> listar() {
        return financeiraDao.findAll();
    }
 
    @RequestMapping(path = "/financeiras/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Financeira inserir(@RequestBody Financeira financeira) {
        financeira.setId(0);
        verificaFinanceira(financeira);
        return financeiraDao.save(financeira); 
    }
    
    @RequestMapping(path = "/financeiras/{id}", method = RequestMethod.GET)
    public Financeira recuperar(@PathVariable int id) {
        Optional<Financeira> findById = financeiraDao.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }
    
    @RequestMapping(path = "/financeiras/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Financeira financeira){
        if (financeiraDao.existsById(id)){
            financeira.setId(id);
            verificaFinanceira(financeira);
            financeiraDao.save(financeira);
        }else{
            throw new NaoEncontrado("Não encontrado");
        }
    }
    
    @RequestMapping(path = "/financeiras/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id){
        if (financeiraDao.existsById(id)){
            financeiraDao.deleteById(id);
        }else {
            throw new NaoEncontrado("Não encontrado");
        }
    }
}
