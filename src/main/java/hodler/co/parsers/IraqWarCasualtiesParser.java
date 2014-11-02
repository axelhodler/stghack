package hodler.co.parsers;

import hodler.co.model.IraqWarCasualtiesInfo;
import hodler.co.parsers.utils.ParsedInfoStorage;
import hodler.co.utils.Collections;

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

		// Us armed forces
		final Document doc2 = Jsoup.connect("http://icasualties.org/iraq/ByYear.aspx").get();
		final Element armedForcesCasTable = doc2.getElementById("ctl00_ContentPlaceHolder1_dgYears");
		final Elements rows = armedForcesCasTable.getElementsByTag("tr");

		for (final Element row : rows) {
			if (row == rows.first() || row == rows.get(1) || row == rows.get(2)
					|| row == rows.last() || row == rows.get(rows.size() - 2) || row == rows.get(rows.size() -3 )) {
				continue;
			}

			final IraqWarCasualtiesInfo info = new IraqWarCasualtiesInfo();

			info.setCasualties(Integer.parseInt(row.getElementsByTag("td").get(23).text()));
			info.setYear(Integer.parseInt(row.getElementsByTag("td").get(0).text()));
			info.setFaction("US Soldiers");

			civilianCasualties.add(info);
		}

		final List<IraqWarCasualtiesInfo> allCasualties = getInsurgencyCasualties(civilianCasualties);

		final ParsedInfoStorage piStorage = new ParsedInfoStorage(Collections.IRAQ_WAR_CASUALTIES);
		piStorage.storeIraqWarCasualties(allCasualties);
	}

	private static List<IraqWarCasualtiesInfo> getInsurgencyCasualties(final List<IraqWarCasualtiesInfo> insurgentCasualties) {
		// Insurgents
		final String INSURGENTS = "Insurgents";

		//2003
		final IraqWarCasualtiesInfo i03 = new IraqWarCasualtiesInfo();
		i03.setYear(2003);
		i03.setCasualties(603);
		i03.setFaction(INSURGENTS);
		insurgentCasualties.add(i03);

		final IraqWarCasualtiesInfo i04 = new IraqWarCasualtiesInfo();
		i04.setYear(2004);
		i04.setCasualties(6801);
		i04.setFaction(INSURGENTS);
		insurgentCasualties.add(i04);

		final IraqWarCasualtiesInfo i05 = new IraqWarCasualtiesInfo();
		i05.setYear(2005);
		i05.setCasualties(3247);
		i05.setFaction(INSURGENTS);
		insurgentCasualties.add(i05);

		final IraqWarCasualtiesInfo i06 = new IraqWarCasualtiesInfo();
		i06.setYear(2006);
		i06.setCasualties(3902);
		i06.setFaction(INSURGENTS);
		insurgentCasualties.add(i06);

		final IraqWarCasualtiesInfo i07 = new IraqWarCasualtiesInfo();
		i07.setYear(2007);
		i07.setCasualties(6747);
		i07.setFaction(INSURGENTS);
		insurgentCasualties.add(i07);

		final IraqWarCasualtiesInfo i08 = new IraqWarCasualtiesInfo();
		i08.setYear(2008);
		i08.setCasualties(2028);
		i08.setFaction(INSURGENTS);
		insurgentCasualties.add(i08);

		final IraqWarCasualtiesInfo i09 = new IraqWarCasualtiesInfo();
		i09.setYear(2009);
		i09.setCasualties(488);
		i09.setFaction(INSURGENTS);
		insurgentCasualties.add(i09);

		final IraqWarCasualtiesInfo i10 = new IraqWarCasualtiesInfo();
		i10.setYear(2010);
		i10.setCasualties(676);
		i10.setFaction(INSURGENTS);
		insurgentCasualties.add(i10);

		final IraqWarCasualtiesInfo i11 = new IraqWarCasualtiesInfo();
		i11.setYear(2011);
		i11.setCasualties(451);
		i11.setFaction(INSURGENTS);
		insurgentCasualties.add(i11);

		return insurgentCasualties;
	}
}
