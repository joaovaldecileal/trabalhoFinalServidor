/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.joaoValdeci.controller;

import br.edu.ifrs.restinga.ds.joaoValdeci.DAO.PessoaDao;
import br.edu.ifrs.restinga.ds.joaoValdeci.erros.NaoEncontrado;
import br.edu.ifrs.restinga.ds.joaoValdeci.erros.RequisicaoInvalida;
import br.edu.ifrs.restinga.ds.joaoValdeci.modelo.Pessoa;
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
public class Pessoas {
    @Autowired
    PessoaDao pessoaDao;
    
    public void verificaPessoa(Pessoa pessoa){
        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()){
            throw new RequisicaoInvalida("digite um nome");
        }
        if (pessoa.getCpf() == null || pessoa.getCpf().isEmpty()){
            throw new RequisicaoInvalida("digite um numero de cpf");
        }
        if (pessoa.getEndereco() == null || pessoa.getEndereco().isEmpty()){
            throw new RequisicaoInvalida("digite um endereço");
        }
        if (pessoa.getTelefone() == null || pessoa.getTelefone().isEmpty()) {
            throw new RequisicaoInvalida("digite um telefone");
        }
    }
    
    @RequestMapping(path = "/pessoas/", method = RequestMethod.GET)
    public Iterable<Pessoa> listar() {
        return pessoaDao.findAll();
    }
    
    @RequestMapping(path = "/pessoas/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa inserir(@RequestBody Pessoa pessoa) {
        pessoa.setId(0);
        verificaPessoa(pessoa);
        return pessoaDao.save(pessoa); 
    }
    
    @RequestMapping(path = "/pessoas/{id}", method = RequestMethod.GET)
    public Pessoa recuperar(@PathVariable int id) {
        Optional<Pessoa> findById = pessoaDao.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }
    
    @RequestMapping(path = "/pessoas/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Pessoa pessoa){
        if (pessoaDao.existsById(id)){
            pessoa.setId(id);
            verificaPessoa(pessoa);
            pessoaDao.save(pessoa);
        }else{
            throw new NaoEncontrado("Não encontrado");
        }
    }
    
    @RequestMapping(path = "/pessoas/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id){
        if (pessoaDao.existsById(id)){
            pessoaDao.deleteById(id);
        }else {
            throw new NaoEncontrado("Não encontrado");
        }
    } 
}
