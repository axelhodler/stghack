package hodler.co.resources;

import hodler.co.model.Casualty;

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
import com.mongodb.Mongo;
import com.mongodb.MongoClient;


@Path("/witchhunts")
@Produces(MediaType.APPLICATION_JSON)
public class WitchHuntResource {

	private final DBCollection col;

	public WitchHuntResource() throws UnknownHostException {
		final Mongo mongo = new MongoClient("localhost");
		final DB killcount = mongo.getDB("killcount");
		col = killcount.getCollection("witchhunts");

	}

	@GET
	@Path("/")
	public Response getWitchHuntCasualties() {
		final DBCursor curs = col.find();
		final List<Casualty> casualties = new ArrayList<Casualty>();

		while (curs.hasNext()) {
			final DBObject dbo = curs.next();

			final Casualty cas = new Casualty();
			cas.setId(dbo.get("_id").toString());
			cas.setCasualties(((Number) dbo.get("casualties")).intValue());
			cas.setRegion(dbo.get("region").toString());

			casualties.add(cas);
		}

		return Response.ok().entity(casualties).build();
	}
}
