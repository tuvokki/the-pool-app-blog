import play.*;
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() {
		// Check if the database is empty
		if (Blogger.count() == 0) {
			Fixtures.loadModels("initial-blog-data.yml");
			Fixtures.loadModels("initial-game-data.yml");
		}
	}

}