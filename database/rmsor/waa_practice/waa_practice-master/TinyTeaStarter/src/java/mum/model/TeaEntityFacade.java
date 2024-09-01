/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mum.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 984317
 */
@Stateless
public class TeaEntityFacade extends AbstractFacade<TeaEntity> {
    @PersistenceContext(unitName = "TinyTeaStarterPU")
    private EntityManager em;
    
}
