import play.*;
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() {
		// Check if the database is empty
		if (Blogger.count() == 0) {
			Logger.info("Loading initial blog data ...");
			Fixtures.loadModels("initial-blog-data.yml");
		}
		if (Game.count() == 0) {
			Logger.info("Loading initial game data ...");
			Fixtures.loadModels("initial-game-data.yml");
		}
	}

}