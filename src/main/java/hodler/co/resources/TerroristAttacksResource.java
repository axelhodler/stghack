package hodler.co.resources;

import hodler.co.model.EntityInfos;
import hodler.co.model.TerroristAttack;
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


@Path("/terroristattacks")
@Produces(MediaType.APPLICATION_JSON)
public class TerroristAttacksResource {

	private final DBCollection col;

	public TerroristAttacksResource(final DB db) {
		col = db.getCollection(Collections.TERRORIST_ATTACKS);
	}

	@GET
	public Response getTerroristAttacks() {
		final DBCursor curs = col.find();
		final List<TerroristAttack> terroristAttacks = new ArrayList<TerroristAttack>();

		while (curs.hasNext()) {
			final DBObject dbo = curs.next();

			final TerroristAttack ta = new TerroristAttack();

			final EntityInfos entityInfos = new EntityInfos();
			entityInfos.setId(dbo.get("_id").toString());
			entityInfos.setLowestCasualties(((Number) dbo.get("lowestCasualties")).intValue());
			entityInfos.setRegion(dbo.get("region").toString());
			entityInfos.setEvent(dbo.get("event").toString());
			ta.setEntityInfos(entityInfos);
			ta.setIdeology(dbo.get("ideology").toString());
			ta.setYear(((Number) dbo.get("year")).intValue());
			ta.setWikipediaLink(dbo.get("link").toString());

			terroristAttacks.add(ta);
		}

		return Response.ok().header("Access-Control-Allow-Origin", "*").entity(terroristAttacks).build();
	}
}
