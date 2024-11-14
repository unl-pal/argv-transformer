/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbatm;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Bank as Stateless EJB
 * Set the name of the EJB for JNDI lookup as a 'ejbatm.BankRemote'
 * @author Guillermo Antonio Toro Bayona
 */
@Stateless
public class Bank implements BankRemote {

    /**
     * EJB declaration and instantiated by reflexion
     */
    @EJB
    private AccountFacade accountFacade;
}
