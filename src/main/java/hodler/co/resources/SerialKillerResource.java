package hodler.co.resources;

import hodler.co.model.EntityInfos;
import hodler.co.model.SerialKiller;

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

@Path("/serialkillers")
@Produces(MediaType.APPLICATION_JSON)
public class SerialKillerResource {

	private final DBCollection col;

	public SerialKillerResource(final DB db) {
		col = db.getCollection("serialkillers");
	}

	@GET
	@Path("/")
	public Response getWitchHuntCasualties() {
		final DBCursor curs = col.find();
		final List<SerialKiller> serialKillers = new ArrayList<SerialKiller>();

		while (curs.hasNext()) {
			final DBObject dbo = curs.next();

			final SerialKiller sk = new SerialKiller();
			
			final EntityInfos entityInfos = new EntityInfos();
			entityInfos.setId(dbo.get("_id").toString());
			entityInfos.setLowestCasualties(((Number) dbo.get("lowestCasualties")).intValue());
			entityInfos.setRegion(dbo.get("region").toString());
			sk.setInfos(entityInfos);
			sk.setName(dbo.get("name").toString());
			sk.setYearsActive(dbo.get("yearsActive").toString());
			sk.setWikipediaLink(dbo.get("link").toString());

			serialKillers.add(sk);
		}

		return Response.ok().header("Access-Control-Allow-Origin", "*").entity(serialKillers).build();
	}
}
