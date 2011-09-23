package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Rating extends Model {
    public String match;
    public Date playedAt;
    
    @Lob
    public String story;
    
    @ManyToOne
    public User author;
    
    public Rating(User author, String title, String content) {
        this.author = author;
        this.match = title;
        this.story = content;
        this.playedAt = new Date();
    }
 
}
