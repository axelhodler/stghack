package hodler.co;

import io.dropwizard.Configuration;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;


public class KillCountConfiguration extends Configuration {

	@NotEmpty
	private final String defaultName = "Stranger";

	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}
}
