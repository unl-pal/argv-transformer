package co.pablobastidasv.resource;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by pbastidas on 1/04/15.
 */
@Path("admin")
@Stateless
@Produces("application/json")
@DeclareRoles({"ROL_PRESTADOR"})
@DenyAll
public class AdminResource {

	@Resource
	SessionContext ctx;

}
