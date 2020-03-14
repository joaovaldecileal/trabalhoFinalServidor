/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.joaoValdeci.controller;

import br.edu.ifrs.restinga.ds.joaoValdeci.DAO.UsuarioDao;
import br.edu.ifrs.restinga.ds.joaoValdeci.erros.NaoEncontrado;
import br.edu.ifrs.restinga.ds.joaoValdeci.erros.RequisicaoInvalida;
import br.edu.ifrs.restinga.ds.joaoValdeci.modelo.Usuario;
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
public class Usuarios {
    @Autowired
    UsuarioDao usuarioDao;
    
    public void validaUsuarios(Usuario usuario){
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new RequisicaoInvalida("digite um nome");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new RequisicaoInvalida("digite uma senha");
        }
        Iterable <Usuario> usuarios = usuarioDao.findAll();
                boolean senhaIgual = false;
                boolean loginIgual = false;

        for (Usuario usuarioLogin: usuarios) {
            if (usuarioLogin.getLogin().equals(usuario.getLogin())) {
                loginIgual = true;
                break;
            }
        }
        if (loginIgual == true) {
            throw new RequisicaoInvalida("login ja existe");
        }
    }
    
    
    @RequestMapping(path = "/usuarios/", method = RequestMethod.GET)
    public Iterable<Usuario> listar() {
        return usuarioDao.findAll();
    }
    
    @RequestMapping(path = "/usuarios/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario inserir(@RequestBody Usuario usuario) {
        usuario.setId(0);
        validaUsuarios(usuario);
        return usuarioDao.save(usuario); 
    }
    
     @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.GET)
    public Usuario recuperar(@PathVariable int id) {
        Optional<Usuario> findById = usuarioDao.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }
    
    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Usuario usuarioNovo){
        Usuario usuarioAntigo = this.recuperar(id);
        usuarioAntigo.setLogin(usuarioNovo.getLogin());
        usuarioAntigo.setNome(usuarioNovo.getNome());
            usuarioDao.save(usuarioAntigo);
      
    }
    
    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id){
        if (usuarioDao.existsById(id)){
            usuarioDao.deleteById(id);
        }else {
            throw new NaoEncontrado("Não encontrado");
        }
    } 
}
