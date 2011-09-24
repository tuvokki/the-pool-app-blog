package controllers;

import play.*;
import play.cache.Cache;
import play.libs.Images;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        Post frontPost = Post.find("order by postedAt asc").first();
        List<Post> olderPosts = Post.find(
            "order by postedAt desc"
        ).from(1).fetch(10);
        render(frontPost, olderPosts);
    }
    
    public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#E4EAFD");
        Cache.set(id, code, "10mn");
        renderBinary(captcha);
    }
    
    @Before
    static void addDefaults() {
        renderArgs.put("tvkTitle", Play.configuration.getProperty("tvk.title"));
        renderArgs.put("tvkBaseline", Play.configuration.getProperty("tvk.baseline"));
        renderArgs.put("menuItem", Play.configuration.getProperty("menu.pool"));
    }
}