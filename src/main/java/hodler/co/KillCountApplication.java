package hodler.co;

import hodler.co.resources.WitchHuntResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class KillCountApplication extends Application<KillCountConfiguration> {

	public static void main(final String[] args) throws Exception {
		new KillCountApplication().run(args);
	}

	@Override
	public void initialize(final Bootstrap<KillCountConfiguration> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(final KillCountConfiguration arg0, final Environment env) throws Exception {
		final WitchHuntResource res = new WitchHuntResource();

		env.jersey().register(res);
	}

}
