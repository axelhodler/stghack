package hodler.co.model;

public class IraqWarCasualtiesInfo {

	private String id;
	private int casualties;
	private int year;
	private String faction;

	public IraqWarCasualtiesInfo() {

	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public int getCasualties() {
		return casualties;
	}

	public void setCasualties(final int casualties) {
		this.casualties = casualties;
	}

	public int getYear() {
		return year;
	}

	public void setYear(final int year) {
		this.year = year;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(final String faction) {
		this.faction = faction;
	}


}
