/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbatm;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ken
 */
@Stateless
public class AccountFacade extends AbstractFacade<Account> {
    @PersistenceContext(unitName = "EjbATM-ejbPU")
    private EntityManager em;
    
}
