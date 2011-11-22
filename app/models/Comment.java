package models;

import java.util.*;
import javax.persistence.*;
 
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.*;
 
@Entity
public class Comment extends Model {
	@Required
    public String author;
	@Required
    public Date postedAt;
     
    @MaxSize(10000)
    @Lob
	@Required
    public String content;
    
    @ManyToOne
    public Post post;

    @ManyToOne
    public Game game;

    public Comment(Post post, String author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }

	public Comment(Game game, String author, String content) {
        this.game = game;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
	}
 
}

