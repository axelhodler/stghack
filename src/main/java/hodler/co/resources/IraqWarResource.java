package hodler.co.resources;

import hodler.co.model.IraqWarCasualtiesInfo;
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


@Path("/iraqwarcasualties")
@Produces(MediaType.APPLICATION_JSON)
public class IraqWarResource {

	private final DBCollection col;

	public IraqWarResource(final DB db) {
		col = db.getCollection(Collections.IRAQ_WAR_CASUALTIES);
	}

	@GET
	public Response getIraqWarCasualties() {
		final DBCursor curs = col.find();
		final List<IraqWarCasualtiesInfo> casualties = new ArrayList<IraqWarCasualtiesInfo>();

		while (curs.hasNext()) {
			final DBObject dbo = curs.next();

			final IraqWarCasualtiesInfo info = new IraqWarCasualtiesInfo();
			info.setId(dbo.get("_id").toString());
			info.setCasualties(((Number) dbo.get("casualties")).intValue());
			info.setYear(((Number) dbo.get("year")).intValue());
			info.setFaction(dbo.get("faction").toString());

			casualties.add(info);
		}

		return Response.ok().header("Access-Control-Allow-Origin", "*").entity(casualties).build();
	}
}
