package hodler.co.parsers;

import hodler.co.model.EntityInfos;
import hodler.co.parsers.utils.ParsedInfoStorage;
import hodler.co.utils.Collections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WW2DeathsParser {

	public static void main(final String[] args) throws IOException {
		final Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/World_War_II_casualties").get();

		final Elements els = doc.getElementsByClass("wikitable");

		// fourth table in the article is the terrorist stuff
		final Element ww2CasualtiesTable = els.get(0);

		final Elements casualtyRows = ww2CasualtiesTable.getElementsByTag("tr");

		final List<EntityInfos> casualtiesByCountry = new ArrayList<EntityInfos>();

		for (final Element casualtiesOfCountry : casualtyRows) {
			// skip first and last
			if ((casualtiesOfCountry == casualtyRows.first()) || (casualtiesOfCountry == casualtyRows.last())) {
				continue;
			}

			final Elements td = casualtiesOfCountry.getElementsByTag("td");
			String country = td.get(0).text();
			// first part has to always be removed
			country = country.substring(0, country.length() - 1);
			final String lastChar = (String) country.subSequence(country.length() - 1, country.length());


			String countryWithoutReferences = country;

			final String upperCaseLastChar = lastChar.toUpperCase();
			if (lastChar.equals(upperCaseLastChar)) {
				// first end again
				countryWithoutReferences = country.substring(0, country.length() - 1);
			}
			// Remove braces
			String countryWithoutBraces = countryWithoutReferences;

			final int indexOfBraces = countryWithoutReferences.lastIndexOf("(");
			if (indexOfBraces != -1) {
				countryWithoutBraces = countryWithoutReferences.substring(0, indexOfBraces);
			}
			final EntityInfos ei = new EntityInfos();

			// check if first letter is uppercase, if not cut out
			final String countryWithoutWhitespace = countryWithoutBraces.replace(String.valueOf((char) 160), " ").trim();
			ei.setRegion(countryWithoutWhitespace);

			final String withoutComma = td.get(1).text().replace(",", "");
			final int lastIndexOfBrace = withoutComma.lastIndexOf("[");
			String withoutReference = withoutComma;

			if (lastIndexOfBrace != -1) {
				withoutReference = withoutComma.substring(0, lastIndexOfBrace);
			}

			final String casualtiesWithoutCommas = td.get(5).text().replace(",", "");
			String casualties = casualtiesWithoutCommas;
			if (casualtiesWithoutCommas.equals("")) {
				casualties = "0";
			}

			final int lastIndexOfTo = casualties.lastIndexOf("to");
			String onlyLowestEstimate = casualties;
			if (lastIndexOfTo != -1) {
				onlyLowestEstimate = casualties.substring(0, lastIndexOfTo);
			}

			ei.setLowestCasualties(Integer.parseInt(onlyLowestEstimate.trim()));
			casualtiesByCountry.add(ei);
		}

		final ParsedInfoStorage pinfoStorage = new ParsedInfoStorage(Collections.WW2_CASUALTIES);
		pinfoStorage.storeWorldWarTwoCasualties(casualtiesByCountry);
	}
}
