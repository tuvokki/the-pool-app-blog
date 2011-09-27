package util;

/**
 * Interface which holds constants for a game over.
 *
 * @author Bob Marks
 * @version Alpha 0.2.3
 */
public interface IGameOver {
        
        /** Player wins a game. */
        public static final String [] RESULTS = {"W", "L", "D", "UR", "AD"};
        
        /** Player wins a game. */
        public static final int WIN = 1;

        /** Player losses a game. */
        public static final int LOSE = 2;

        /** Player draws with another player. */
        public static final int DRAW = 3;       
        
        /** A user resigns. */
        public static final int USER_RESIGNS = 4;
        
        /** All users agree on a draw. */
        public static final int AGREED_DRAW = 5;        
}
