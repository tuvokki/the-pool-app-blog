package controllers;

import java.util.List;

import models.Comment;
import models.Post;
import play.Play;
import play.cache.Cache;
import play.data.validation.Required;
import play.libs.Codec;
import play.mvc.Before;
import play.mvc.Controller;

public class Blog extends Controller {
	public static void list() {
		Post frontPost = Post.find("order by postedAt asc").first();
		List<Post> olderPosts = Post.find("order by postedAt asc").fetch(10);
		olderPosts.remove(frontPost);
		render(olderPosts);
	}

	public static void show(Long id) {
		Post post = Post.findById(id);
		String randomID = Codec.UUID();
		render(post, randomID);
	}

	public static void postComment(Long postId,
			@Required(message = "Author is required") String author,
			@Required(message = "A message is required") String content,
			@Required(message = "Please type the code") String code,
			String randomID) {
		Post post = Post.findById(postId);
		if (code != null && !code.equalsIgnoreCase("")) {
			validation.equals(code, Cache.get(randomID)).message(
					"Invalid code. Please type it again");
		}

		if (validation.hasErrors()) {
			render("Blog/show.html", post, randomID);
		}
		post.addComment(author, content);
		flash.success("Thanks for posting your message, %s", author);
		Cache.delete(randomID);
		show(postId);
	}

	/*
	 * public static void postComment(Long postId, @Required String author,
	 * 
	 * @Required String content) { Post post = Post.findById(postId); if
	 * (validation.hasErrors()) { render("Blog/show.html", post); }
	 * post.addComment(author, content);
	 * flash.success("Thanks for posting your message, %s", author);
	 * show(postId); }
	 */

	@Before
	static void addDefaults() {
		renderArgs.put("tvkTitle", Play.configuration.getProperty("tvk.title"));
		renderArgs.put("tvkBaseline",
				Play.configuration.getProperty("tvk.baseline"));
		renderArgs.put("menuItem", Play.configuration.getProperty("menu.blog"));
	}

	public static void listTagged(String tag) {
		List<Post> posts = Post.findTaggedWith(tag);
		render(tag, posts);
	}
}
