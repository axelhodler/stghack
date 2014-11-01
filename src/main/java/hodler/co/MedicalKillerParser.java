package hodler.co;

import hodler.co.model.EntityInfos;
import hodler.co.model.SerialKiller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


public class MedicalKillerParser {
	public static void main(final String[] args) throws IOException {
		final MongoClientURI mongoUri = new MongoClientURI(
				System.getenv("MONGO_URL"));
		final MongoClient mongo = new MongoClient(mongoUri);

		final DB killcount = mongo.getDB(System.getenv("DB_NAME"));
		final DBCollection col = killcount.getCollection("serialkillers");

		final Document doc =
				Jsoup.connect("http://en.wikipedia.org/wiki/List_of_serial_killers_by_number_of_victims").get();

		final Elements els = doc.getElementsByClass("wikitable");

		final Element killerTable = els.get(2);

		final Elements serialKillers = killerTable.getElementsByTag("tr");

		final List<SerialKiller> serialKillerList = new ArrayList<SerialKiller>();

		for (final Element killer : serialKillers) {
			if (killer == serialKillers.first()) {
				continue;
			}

			final Elements killerInfo = killer.getElementsByTag("td");
			final SerialKiller k = new SerialKiller();
			final EntityInfos i = new EntityInfos();
			final String name;

			name = parseName(killerInfo);

			k.setName(name);
			i.setRegion(killerInfo.get(1).text());
			// deal with numbers using a plus at the end :)
			final int lowestCasualties;

			if (killerInfo.get(3).text().endsWith("+")) {
				final String info = killerInfo.get(3).text();
				lowestCasualties = Integer.parseInt(info.replace("+", ""));
			} else if (killerInfo.get(3).text().contains("–")){
				final String[] possibleCasualties = killerInfo.get(3).text().split("–");
				lowestCasualties = Integer.parseInt(possibleCasualties[1]);
			} else {
				lowestCasualties = Integer.parseInt(killerInfo.get(3).text());
			}
			i.setLowestCasualties(lowestCasualties);

			k.setYearsActive(killerInfo.get(2).text());
			k.setInfos(i);
			
			serialKillerList.add(k);
		}

		for (final SerialKiller k : serialKillerList) {
			col.insert(new BasicDBObject("region", k.getInfos().getRegion())
					.append("lowestCasualties", k.getInfos().getLowestCasualties())
					.append("yearsActive", k.getYearsActive()).append("name", k.getName()));
		}
	}

	private static String parseName(final Elements killerInfo) {
		return killerInfo.get(0).text();
	}
}
