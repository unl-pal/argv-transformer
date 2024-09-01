package ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.UserInfo;

/**
 *
 * @author xtrememe
 */
@Stateless
public class UserFacade extends AbstractFacade<UserInfo> {

    @PersistenceContext(unitName = "eticketingPU")
    private EntityManager em;

}
