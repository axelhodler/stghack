package hodler.co.parsers;

import hodler.co.model.IraqWarCasualtiesInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class IraqWarCasualtiesParser {

	public static void main(final String[] args) throws IOException {
		final Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/Casualties_of_the_Iraq_War").get();

		final Elements els = doc.getElementsByClass("wikitable");

		final Element iraqWarCivilianCasualties = els.get(5);

		final Elements casualtiesByYear = iraqWarCivilianCasualties.getElementsByTag("tr");

		// Civilian
		final List<IraqWarCasualtiesInfo> civilianCasualties = new ArrayList<IraqWarCasualtiesInfo>();
		for (final Element e : casualtiesByYear) {
			if (e == casualtiesByYear.first() || e == casualtiesByYear.get(1)) {
				continue;
			}

			final IraqWarCasualtiesInfo i = new IraqWarCasualtiesInfo();

			final Elements yearCasualties = e.getElementsByTag("td");
			i.setYear(Integer.parseInt(yearCasualties.get(0).text()));
			i.setFaction("Civilian");
			i.setCasualties(Integer.parseInt(yearCasualties.get(13).text().replace(",", "")));
			civilianCasualties.add(i);
		}

		// Use armed forces
		final Document doc2 = Jsoup.connect("http://icasualties.org/iraq/ByYear.aspx").get();
		final Element armedForcesCasTable = doc2.getElementById("ctl00_ContentPlaceHolder1_dgYears");
		final Elements rows = armedForcesCasTable.getElementsByTag("tr");

		for (final Element row : rows) {
			if (row == casualtiesByYear.first() || row == casualtiesByYear.get(1)
					|| row == casualtiesByYear.last()) {
				continue;
			}

			System.out.println(row.getElementsByTag("td").get(23).text());
		}
	}
}
