package hodler.co.model;

import javax.validation.constraints.NotNull;


public class EntityInfos {

	private String id;
	private String event;
	private String region;

	private int lowestCasualties;
	@NotNull
	private int highestCasualties;

	public EntityInfos() {

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

	public int getLowestCasualties() {
		return lowestCasualties;
	}

	public void setLowestCasualties(final int lowestEstimateCasualties) {
		lowestCasualties = lowestEstimateCasualties;
	}

	public int getHighestCasualties() {
		return highestCasualties;
	}

	public void setHighestCasualties(final int highestEstiamteCasualties) {
		highestCasualties = highestEstiamteCasualties;
	}

}
