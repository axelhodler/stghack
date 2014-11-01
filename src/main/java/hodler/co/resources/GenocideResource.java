package hodler.co.resources;

import hodler.co.model.EntityInfos;
import hodler.co.model.Genocide;
import hodler.co.model.HistoricalRange;
import hodler.co.utils.Collections;

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
public class GenocideResource {

	private final DBCollection col;

	public GenocideResource(final DB db) {
		col = db.getCollection(Collections.GENOCIDES);
	}

	@GET
	@Path("/")
	public Response getDemocides() {
		final DBCursor curs = col.find();
		final List<Genocide> casualties = new ArrayList<Genocide>();

		while (curs.hasNext()) {
			final DBObject dbo = curs.next();

			final Genocide gc = new Genocide();
			final EntityInfos cas = new EntityInfos();
			cas.setId(dbo.get("_id").toString());
			cas.setHighestCasualties(((Number) dbo.get("highestCasualties")).intValue());
			if (dbo.get("lowestCasualties") != null) {
				cas.setLowestCasualties(((Number) dbo.get("lowestCasualties")).intValue());
			}
			cas.setRegion(dbo.get("region").toString());
			cas.setEvent(dbo.get("event").toString());

			gc.setInfos(cas);
			gc.setTimeRange(new HistoricalRange(
					Integer.parseInt(dbo.get("from").toString()),
					Integer.parseInt(dbo.get("to").toString())));
			gc.setWikipediaLink(dbo.get("link").toString());

			casualties.add(gc);
		}

		return Response.ok().header("Access-Control-Allow-Origin", "*").entity(casualties).build();
	}
}
