package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Player extends Model{
    @Required
    public String name;
    
    public int eloRating;

    public Player(String name) {
        this.name = name;
        this.eloRating = 1600;
    }
	
	public static Player connect(String name) {
	    return find("byName", name).first();
	}
	
	public String toString() {
		return name;
	}
}
