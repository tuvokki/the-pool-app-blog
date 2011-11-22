package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.Required;
import play.db.jpa.Model;
import util.EloRatingSystem;
import util.IGameOver;

@Entity
public class Game extends Model {
	@OneToOne
	@Required
	public Player playerOne;
	public int playerOneEloBefore;
	public int playerOneEloAfter;
	@OneToOne
	@Required
	public Player playerTwo;
	public int playerTwoEloBefore;
	public int playerTwoEloAfter;
	@Required
	public Date playedAt;
	public String title = "game";
	@OneToOne
	public Player winner;

	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	public List<Comment> comments;
	
	public Game(Player playerOne, Player playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.playerOneEloBefore = playerOne.eloRating;
		this.playerTwoEloBefore = playerTwo.eloRating;
		this.playedAt = new Date();
	}

	public Game(Player playerOne, Player playerTwo, Player winner) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.winGame(winner);
	}
	
	public Game(Player playerOne, Player playerTwo, Date playedAt) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.playedAt = playedAt;
	}
	
	public Game(Date date) {
		this.playedAt = date;
	}

	public Game addComment(String author, String content) {
		Comment newComment = new Comment(this, author, content).save();
		this.comments.add(newComment);
		this.save();
		return this;
	}
	
	public Game winGame(Player winner) {
//		variable = booleanExpr ? value1 : value2;
		EloRatingSystem elo = EloRatingSystem.getInstance(null);
		/*
		 *		EloRatingSystem elo = new EloRatingSystem();
		 * 		int userRating = 1600;
		 * 		int opponentRating = 1650; 
		 * 		int newUserRating = elo.getNewRating(userRating, opponentRating, WIN);
		 * 		int newOpponentRating = elo.getNewRating(opponentRating, userRating, LOSS);
		 */
		this.winner = winner;
		if (winner.equals(playerOne)) {
			playerOne.eloRating = elo.getNewRating(playerOne.eloRating, playerTwo.eloRating, IGameOver.WIN);
			playerOne.validateAndSave();
			playerTwo.eloRating = elo.getNewRating(playerTwo.eloRating, playerOne.eloRating, IGameOver.LOSE);
			playerTwo.validateAndSave();
		} else {
			playerTwo.eloRating = elo.getNewRating(playerTwo.eloRating, playerOne.eloRating, IGameOver.WIN);
			playerTwo.validateAndSave();
			playerOne.eloRating = elo.getNewRating(playerOne.eloRating, playerTwo.eloRating, IGameOver.LOSE);
			playerOne.validateAndSave();
		}
		this.playerOneEloAfter = playerOne.eloRating;
		this.playerTwoEloAfter = playerTwo.eloRating;
		this.save();
		return this;
	}

	public boolean isWon() {
		if (winner == null)
			return false;
		else
			return true;
	}
	public Game previous() {
		return Game.find("playedAt < ? order by playedAt desc", playedAt)
				.first();
	}

	public Game next() {
		return Game.find("playedAt > ? order by playedAt asc", playedAt)
				.first();
	}
	
	public String toString() {
		if (winner == null) return "At " + playedAt + ", " + playerOne + " played " + playerTwo + " and nobody won.";
		return "At " + playedAt + ", " + playerOne + " played " + playerTwo + " and " + winner + " won.";
	}
}
