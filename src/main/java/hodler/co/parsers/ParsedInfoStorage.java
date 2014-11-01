package hodler.co.parsers;

import hodler.co.model.SerialKiller;
import hodler.co.utils.EnvVars;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


public class ParsedInfoStorage {

	private final DBCollection col;

	public ParsedInfoStorage(final String collectionName) throws UnknownHostException {
		final MongoClientURI mongoUri = new MongoClientURI(
				System.getenv(EnvVars.MONGO_URL));
		final MongoClient mongo = new MongoClient(mongoUri);

		final DB killcount = mongo.getDB(System.getenv(EnvVars.DB_NAME));
		col = killcount.getCollection(collectionName);
	}

	public void storeSerialKillers(final List<SerialKiller> killers) {
		for (final SerialKiller k : killers) {
			col.insert(new BasicDBObject("region", k.getInfos().getRegion())
					.append("lowestCasualties", k.getInfos().getLowestCasualties())
					.append("yearsActive", k.getYearsActive()).append("name", k.getName())
					.append("link", k.getWikipediaLink()));
		}
	}
}
