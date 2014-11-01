package hodler.co.parsers.utils;

import hodler.co.model.Genocide;
import hodler.co.model.SerialKiller;
import hodler.co.model.TerroristAttack;
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

	public void storeGenocides(final List<Genocide> genocides) {
		for (final Genocide gc : genocides) {
			col.insert(new BasicDBObject("region", gc.getInfos().getRegion())
					.append("lowestCasualties", gc.getInfos().getLowestCasualties())
					.append("highestCasualties", gc.getInfos().getHighestCasualties())
					.append("from", gc.getTimeRange().getFrom())
					.append("to", gc.getTimeRange().getTo())
					.append("event", gc.getInfos().getEvent())
					.append("link", gc.getWikipediaLink()));
		}
	}

	public void storeTerroristAttacks(final List<TerroristAttack> attacks) {
		for (final TerroristAttack attack : attacks) {
			col.insert(new BasicDBObject("region", attack.getEntityInfos().getRegion())
					.append("lowestCasualties", attack.getEntityInfos().getLowestCasualties())
					.append("year", attack.getYear())
					.append("ideology", attack.getIdeology())
					.append("event", attack.getEntityInfos().getEvent())
					.append("link", attack.getWikipediaLink()));
		}
	}
}
