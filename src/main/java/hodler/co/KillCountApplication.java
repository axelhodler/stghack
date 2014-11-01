package hodler.co;

import hodler.co.resources.GenocideResource;
import hodler.co.resources.SerialKillerResource;
import hodler.co.resources.TerroristAttacksResource;
import hodler.co.resources.WitchHuntResource;
import hodler.co.utils.EnvVars;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


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
		final MongoClientURI mongoUri = new MongoClientURI(
				System.getenv(EnvVars.MONGO_URL));
		final MongoClient mongo = new MongoClient(mongoUri);

		final DB killcount = mongo.getDB(System.getenv(EnvVars.DB_NAME));

		final WitchHuntResource witchHuntRes = new WitchHuntResource(killcount);
		final GenocideResource demoCideRes = new GenocideResource(killcount);
		final SerialKillerResource serialKillerRes = new SerialKillerResource(killcount);
		final TerroristAttacksResource taRes = new TerroristAttacksResource(killcount);

		env.jersey().register(witchHuntRes);
		env.jersey().register(demoCideRes);
		env.jersey().register(serialKillerRes);
		env.jersey().register(taRes);
	}

}
