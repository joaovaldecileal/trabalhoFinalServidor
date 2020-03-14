/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.joaoValdeci.DAO;

import br.edu.ifrs.restinga.ds.joaoValdeci.modelo.CartaoCredito;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yk.hjm
 */
@Repository
public interface CartaoDao extends CrudRepository<CartaoCredito, Integer> {
    
}
