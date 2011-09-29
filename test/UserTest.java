import models.Blogger;

import org.junit.Test;

import play.test.UnitTest;


public class UserTest extends BasicModelsTest {

	@Test
	public void createAndRetrieveUser() {
		// Create a new user and save it
		new Blogger("bob@gmail.com", "secret", "Bob").save();

		// Retrieve the user with e-mail address bob@gmail.com
		Blogger bob = Blogger.find("byEmail", "bob@gmail.com").first();

		// Test
		assertNotNull(bob);
		assertEquals("Bob", bob.fullname);
	}

	@Test
	public void createAndAlterAndRetrieveUser() {
		// Create a new user and save it
		new Blogger("alic@gmail.com", "secret", "Alice").save();

		// Retrieve the user with e-mail address alice@gmail.com
		Blogger alice = Blogger.find("byFullname", "Alice").first();

		// Test
		assertNotNull(alice);
		alice.email("alice@gmail.com");
		alice.save();
		alice = Blogger.find("byEmail", "alice@gmail.com").first();
		assertEquals("Alice", alice.fullname);
	}

	@Test
	public void tryConnectAsUser() {
	    // Create a new user and save it
	    new Blogger("bob@gmail.com", "secret", "Bob").save();
	    
	    // Test 
	    assertNotNull(Blogger.connect("bob@gmail.com", "secret"));
	    assertNull(Blogger.connect("bob@gmail.com", "badpassword"));
	    assertNull(Blogger.connect("tom@gmail.com", "secret"));
	}

}
