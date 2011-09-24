import models.Post;
import models.User;

import org.junit.Test;

import play.test.UnitTest;


public class TaggingTest extends BasicModelsTest{
	@Test
	public void testTags() {
	    // Create a new user and save it
	    User bob = new User("bob@gmail.com", "secret", "Bob").save();
	 
	    // Create a new post
	    Post bobPost = new Post(bob, "My first post", "Hello world").save();
	    Post anotherBobPost = new Post(bob, "Hop", "Hello world").save();
	    
	    // Well
	    assertEquals(0, Post.findTaggedWith("Red").size());
	    
	    // Tag it now
	    bobPost.tagItWith("Red").tagItWith("Blue").save();
	    anotherBobPost.tagItWith("Red").tagItWith("Green").save();
	    
	    // Check
	    assertEquals(2, Post.findTaggedWith("Red").size());        
	    assertEquals(1, Post.findTaggedWith("Blue").size());
	    assertEquals(1, Post.findTaggedWith("Green").size());
	    assertEquals(1, Post.findTaggedWith("Red", "Blue").size());   
	    assertEquals(1, Post.findTaggedWith("Red", "Green").size());   
	    assertEquals(0, Post.findTaggedWith("Red", "Green", "Blue").size());  
	    assertEquals(0, Post.findTaggedWith("Green", "Blue").size());
	}

}