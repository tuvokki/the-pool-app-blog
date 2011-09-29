package models;

import javax.persistence.Entity;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Blogger extends Model {
    @Email
    @Required
    public String email;
    
    @Required
    public String password;
    
    public String fullname;
    public boolean isAdmin;
    
    public Blogger(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

	public void email(String email) {
		this.email = email;
	}
	
	public static Blogger connect(String email, String password) {
	    return find("byEmailAndPassword", email, password).first();
	}
	
	public String toString() {
		return fullname;
	}
}
