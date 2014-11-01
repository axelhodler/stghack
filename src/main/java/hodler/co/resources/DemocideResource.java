package hodler.co.resources;

import hodler.co.model.EntityInfos;

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


@Path("/genocides")
@Produces(MediaType.APPLICATION_JSON)
public class DemocideResource {

	private final DBCollection col;

	public DemocideResource(final DB db) {
		col = db.getCollection("genocides");
	}

	@GET
	@Path("/")
	public Response getDemocides() {
		final DBCursor curs = col.find();
		final List<EntityInfos> casualties = new ArrayList<EntityInfos>();

		while (curs.hasNext()) {
			final DBObject dbo = curs.next();

			final EntityInfos cas = new EntityInfos();
			cas.setId(dbo.get("_id").toString());
			cas.setHighestCasualties(((Number) dbo.get("highestCasualties")).intValue());
			if (dbo.get("lowestCasualties") != null) {
				cas.setLowestCasualties(((Number) dbo.get("lowestCasualties")).intValue());
			}
			cas.setRegion(dbo.get("region").toString());

			casualties.add(cas);
		}

		return Response.ok().header("Access-Control-Allow-Origin", "*").entity(casualties).build();
	}
}
