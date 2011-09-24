import models.User;

import org.junit.Test;

import play.test.UnitTest;


public class UserTest extends BasicModelsTest {

	@Test
	public void createAndRetrieveUser() {
		// Create a new user and save it
		new User("bob@gmail.com", "secret", "Bob").save();

		// Retrieve the user with e-mail address bob@gmail.com
		User bob = User.find("byEmail", "bob@gmail.com").first();

		// Test
		assertNotNull(bob);
		assertEquals("Bob", bob.fullname);
	}

	@Test
	public void createAndAlterAndRetrieveUser() {
		// Create a new user and save it
		new User("alic@gmail.com", "secret", "Alice").save();

		// Retrieve the user with e-mail address alice@gmail.com
		User alice = User.find("byFullname", "Alice").first();

		// Test
		assertNotNull(alice);
		alice.email("alice@gmail.com");
		alice.save();
		alice = User.find("byEmail", "alice@gmail.com").first();
		assertEquals("Alice", alice.fullname);
	}

	@Test
	public void tryConnectAsUser() {
	    // Create a new user and save it
	    new User("bob@gmail.com", "secret", "Bob").save();
	    
	    // Test 
	    assertNotNull(User.connect("bob@gmail.com", "secret"));
	    assertNull(User.connect("bob@gmail.com", "badpassword"));
	    assertNull(User.connect("tom@gmail.com", "secret"));
	}

}
