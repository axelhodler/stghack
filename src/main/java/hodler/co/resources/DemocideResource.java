package hodler.co.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/democides")
@Produces(MediaType.APPLICATION_JSON)
public class DemocideResource {

	@GET
	@Path("/")
	public Response getDemocides() {
		return null;
	}
}
