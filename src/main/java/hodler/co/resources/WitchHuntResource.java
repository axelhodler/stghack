package hodler.co.resources;

import hodler.co.model.Infos;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


@Path("/witchhunts")
@Produces(MediaType.APPLICATION_JSON)
public class WitchHuntResource {

	private final DBCollection col;

	public WitchHuntResource(final DB db) throws UnknownHostException {
		col = db.getCollection("witchhunts");
	}

	@GET
	@Path("/")
	public Response getWitchHuntCasualties() {
		final DBCursor curs = col.find();
		final List<Infos> casualties = new ArrayList<Infos>();

		while (curs.hasNext()) {
			final DBObject dbo = curs.next();

			final Infos cas = new Infos();
			cas.setId(dbo.get("_id").toString());
			cas.setHighestCasualties(((Number) dbo.get("highestCasualties")).intValue());
			if (dbo.get("lowestCasualties") != null) {
				cas.setLowestCasualties(((Number) dbo.get("lowestCasualties")).intValue());
			}
			cas.setRegion(dbo.get("region").toString());

			casualties.add(cas);
		}

		return Response.ok().entity(casualties).build();
	}
}
