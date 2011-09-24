import org.junit.Before;

import play.test.Fixtures;
import play.test.UnitTest;


public abstract class BasicModelsTest extends UnitTest {
	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}
	
}
