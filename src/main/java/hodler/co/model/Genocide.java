package hodler.co.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;


public class Genocide {

	@JsonUnwrapped
	private HistoricalRange timeRange;
	@JsonUnwrapped
	private EntityInfos infos;

	private String wikipediaLink;

	public Genocide() {
		
	}

	public HistoricalRange getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(final HistoricalRange timeRange) {
		this.timeRange = timeRange;
	}

	public EntityInfos getInfos() {
		return infos;
	}

	public void setInfos(final EntityInfos infos) {
		this.infos = infos;
	}

	public String getWikipediaLink() {
		return wikipediaLink;
	}

	public void setWikipediaLink(final String wikipediaLink) {
		this.wikipediaLink = wikipediaLink;
	}

}
