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


public class SerialKillerParser {

	public static void main(final String[] args) throws IOException {
		final MongoClientURI mongoUri = new MongoClientURI(
				System.getenv("MONGO_URL"));
		final MongoClient mongo = new MongoClient(mongoUri);

		final DB killcount = mongo.getDB(System.getenv("DB_NAME"));
		final DBCollection col = killcount.getCollection("serialkillers");

		final Document doc =
				Jsoup.connect("http://en.wikipedia.org/wiki/List_of_serial_killers_by_number_of_victims").get();

		final Elements els = doc.getElementsByClass("wikitable");

		final Element killerTable = els.get(0);
		final Elements serialKillers = killerTable.getElementsByTag("tr");

		final List<SerialKiller> serialKillerList = new ArrayList<SerialKiller>();

		for (final Element killer : serialKillers) {
			if (killer == serialKillers.first()) {
				continue;
			}

			final Elements killerInfo = killer.getElementsByTag("td");
			final SerialKiller k = new SerialKiller();
			final EntityInfos i = new EntityInfos();
			k.setName(killerInfo.get(0).child(0).text());
			i.setRegion(killerInfo.get(1).text());
			i.setLowestCasualties(Integer.parseInt(killerInfo.get(3).text()));
			k.setYearsActive(killerInfo.get(2).text());
			k.setInfos(i);

			String wikiLink = "";
			if (killerInfo.get(0).getElementsByTag("a").size() > 0) {
				wikiLink = "http://en.wikipedia.org" + getWikiLink(killerInfo);
			} else {
				//
			}
			k.setWikipediaLink(wikiLink);

			serialKillerList.add(k);
		}

		for (final SerialKiller k : serialKillerList) {
			col.insert(new BasicDBObject("region", k.getInfos().getRegion())
					.append("lowestCasualties", k.getInfos().getLowestCasualties())
					.append("yearsActive", k.getYearsActive()).append("name", k.getName())
					.append("link", k.getWikipediaLink()));
		}
	}

	private static String getWikiLink(final Elements killerInfo) {
		return killerInfo.get(0).getElementsByTag("a").get(0).attr("href");
	}
}
