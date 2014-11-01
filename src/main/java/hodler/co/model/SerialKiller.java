package hodler.co.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;


public class SerialKiller {

	@JsonUnwrapped
	private EntityInfos infos;
	private String name;
	private String yearsActive;

	public SerialKiller() {
	}

	public String getYearsActive() {
		return yearsActive;
	}

	public void setYearsActive(final String yearsActive) {
		this.yearsActive = yearsActive;
	}

	public EntityInfos getInfos() {
		return infos;
	}

	public void setInfos(final EntityInfos infos) {
		this.infos = infos;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}


}
