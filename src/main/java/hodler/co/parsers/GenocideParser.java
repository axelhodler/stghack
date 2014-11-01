package hodler.co.parsers;

import hodler.co.model.EntityInfos;
import hodler.co.model.Genocide;
import hodler.co.model.HistoricalRange;
import hodler.co.parsers.utils.ParsedInfoStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class GenocideParser {

	public static void main(final String[] args) throws IOException {
		final Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/List_of_genocides_by_death_toll").get();

		// only table on the page
		final Elements els = doc.getElementsByClass("wikitable");
		final Element genocideTable = els.get(0);

		final Elements genocides = genocideTable.getElementsByTag("tr");

		final List<Genocide> genocideList = new ArrayList<Genocide>();

		for (final Element genocide : genocides) {
			if (genocide == genocides.first()) {
				continue;
			}

			final EntityInfos ei = new EntityInfos();

			final Elements genocideInfos = genocide.getElementsByTag("td");
			int lowestCasualties = 0;
			if (genocideInfos.get(0).hasText()) {
				lowestCasualties = Integer.parseInt(genocideInfos.get(0).textNodes().get(0).text().replace(",", ""));
			}
			ei.setLowestCasualties(lowestCasualties);
			ei.setHighestCasualties(Integer.parseInt(genocideInfos.get(1).textNodes().get(0).text().replace(",", "")
					.trim()));

			ei.setEvent(genocideInfos.get(3).text());

			
			ei.setRegion(genocideInfos.get(4).text().replace(" ", ""));

			final int from = Integer.parseInt(genocideInfos.get(5).text());
			int to;

			if (notAYearFormat(genocideInfos)) {
				to = dealWithOngoing();
			} else {
				to = Integer.parseInt(genocideInfos.get(6).text());
			}

			final Genocide gc = new Genocide();
			gc.setInfos(ei);
			gc.setTimeRange(new HistoricalRange(from, to));
			gc.setWikipediaLink("http://en.wikipedia.org" +genocideInfos.get(3).getElementsByTag("a").attr("href"));

			genocideList.add(gc);
		}

		final ParsedInfoStorage ps = new ParsedInfoStorage("genocides");
		ps.storeGenocides(genocideList);
	}

	private static int dealWithOngoing() {
		return 2014;
	}

	private static boolean notAYearFormat(final Elements genocideInfos) {
		return genocideInfos.get(6).text().length() > 4;
	}
}
