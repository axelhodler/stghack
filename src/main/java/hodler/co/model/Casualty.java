package hodler.co.model;

import javax.validation.constraints.NotNull;


public class Casualty {

	private String id;
	private String event;
	private String region;

	@NotNull
	private int casualties;

	public Casualty() {

	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(final String event) {
		this.event = event;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(final String region) {
		this.region = region;
	}

	public int getCasualties() {
		return casualties;
	}

	public void setCasualties(final int casualties) {
		this.casualties = casualties;
	}


}
