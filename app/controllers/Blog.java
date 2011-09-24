package controllers;

import java.util.List;

import models.Comment;
import models.Post;
import play.Play;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

public class Blog extends Controller {
	public static void list() {
		Post frontPost = Post.find("order by postedAt asc").first();
		List<Post> olderPosts = Post.find("order by postedAt desc").fetch(10);
		olderPosts.remove(frontPost);
		render(olderPosts);
	}

	public static void show(Long id) {
		Post post = Post.findById(id);
		render(post);
	}

	public static void postComment(Long postId, @Required String author, @Required String content) {
	    Post post = Post.findById(postId);
	    if (validation.hasErrors()) {
	        render("Blog/show.html", post);
	    }
	    post.addComment(author, content);
	    flash.success("Thanks for posting your message, %s", author);
	    show(postId);
	}
	
	@Before
	static void addDefaults() {
		renderArgs.put("tvkTitle", Play.configuration.getProperty("tvk.title"));
		renderArgs.put("tvkBaseline",
				Play.configuration.getProperty("tvk.baseline"));
        renderArgs.put("menuItem", Play.configuration.getProperty("menu.blog"));
	}
}
