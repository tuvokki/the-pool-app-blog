package controllers;

import models.*;

public class Security extends controllers.Secure.Security {
	static boolean authenticate(String username, String password) {
		return Blogger.connect(username, password) != null;
	}

	static boolean check(String profile) {
	    if("admin".equals(profile)) {
	        return Blogger.find("byEmail", connected()).<Blogger>first().isAdmin;
	    }
	    return false;
	}
	
	static void onDisconnected() {
		Blog.list();
	}

	static void onAuthenticated() {
		Admin.index();
	}
}
