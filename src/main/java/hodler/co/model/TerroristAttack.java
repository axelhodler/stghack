package hodler.co.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;


public class TerroristAttack {

	@JsonUnwrapped
	private EntityInfos entityInfos;
	private int year;
	private String ideology;
	private String wikipediaLink;

	public TerroristAttack() {
	}

	public EntityInfos getEntityInfos() {
		return entityInfos;
	}

	public void setEntityInfos(final EntityInfos entityInfos) {
		this.entityInfos = entityInfos;
	}

	public int getYear() {
		return year;
	}

	public void setYear(final int year) {
		this.year = year;
	}

	public String getIdeology() {
		return ideology;
	}

	public void setIdeology(final String ideology) {
		this.ideology = ideology;
	}

	public String getWikipediaLink() {
		return wikipediaLink;
	}

	public void setWikipediaLink(final String wikipediaLink) {
		this.wikipediaLink = wikipediaLink;
	}

}
