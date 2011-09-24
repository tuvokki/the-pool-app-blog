import java.util.List;

import models.Post;
import models.User;

import org.junit.Test;

import play.test.UnitTest;


public class PostTest extends BasicModelsTest {
	@Test
	public void createPost() {
		// Create a new user and save it
		User bob = new User("bob@gmail.com", "secret", "Bob").save();

		// Create a new post
		new Post(bob, "My first post", "Hello world").save();

		// Test that the post has been created
		assertEquals(1, Post.count());

		// Retrieve all posts created by Bob
		List<Post> bobPosts = Post.find("byAuthor", bob).fetch();

		// Tests
		assertEquals(1, bobPosts.size());
		Post firstPost = bobPosts.get(0);
		assertNotNull(firstPost);
		assertEquals(bob, firstPost.author);
		assertEquals("My first post", firstPost.title);
		assertEquals("Hello world", firstPost.content);
		assertNotNull(firstPost.postedAt);
	}


}
