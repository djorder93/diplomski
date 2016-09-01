/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen.beans;

import domen.Stavkaintervencije;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author radovanovic
 */
@Stateless
public class StavkaintervencijeFacade extends AbstractFacade<Stavkaintervencije> {

    @PersistenceContext(unitName = "diplomskiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StavkaintervencijeFacade() {
        super(Stavkaintervencije.class);
    }
    
}
