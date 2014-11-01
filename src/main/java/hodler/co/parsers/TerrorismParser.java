package hodler.co.parsers;

import hodler.co.model.EntityInfos;
import hodler.co.model.TerroristAttack;
import hodler.co.parsers.utils.ParsedInfoStorage;
import hodler.co.utils.Collections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TerrorismParser {

	public static void main(final String[] args) throws IOException {
		final Document doc =
				Jsoup.connect(
						"http://en.wikipedia.org/wiki/List_of_battles_and_other_violent_events_by_death_toll#Terrorist_attacks")
						.get();

		final Elements els = doc.getElementsByClass("wikitable");

		// fourth table in the article is the terrorist stuff
		final Element terroristAttacksTable = els.get(4);

		final Elements terroristAttacks = terroristAttacksTable.getElementsByTag("tr");

		final List<TerroristAttack> terroristAttacksList = new ArrayList<TerroristAttack>();

		for (final Element attack : terroristAttacks) {
			if (attack == terroristAttacks.first()) {
				continue;
			}

			final Elements td = attack.getElementsByTag("td");
			final TerroristAttack ta = new TerroristAttack();

			final EntityInfos ei = new EntityInfos();
			ei.setLowestCasualties(
					Integer.parseInt(td.get(0).text().replace(",", "")
							.replace("+", "")));
			ei.setEvent(td.get(1).text());
			ta.setWikipediaLink("http://en.wikipedia.org" + td.get(1).getElementsByTag("a").attr("href"));
			ta.setIdeology(td.get(2).text());
			ei.setRegion(td.get(3).text());
			ta.setYear(Integer.parseInt(td.get(5).text()));

			ta.setEntityInfos(ei);

			terroristAttacksList.add(ta);
		}

		final ParsedInfoStorage ps = new ParsedInfoStorage(Collections.TERRORIST_ATTACKS);
		ps.storeTerroristAttacks(terroristAttacksList);
	}

}
