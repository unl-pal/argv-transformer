/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbatm;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Ken
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;
    
}
