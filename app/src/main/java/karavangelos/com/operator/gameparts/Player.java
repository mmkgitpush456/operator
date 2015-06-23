package karavangelos.com.operator.gameparts;

/**
 * Created by karavangelos on 6/19/15.
 */
public class Player {

    private static final String TAG = "player";


    private int score;
    private int livesLeft;
    private int level;
    private float timeLeft;
    private boolean hasPowerUp;
    private boolean pausesGame;

    private static Player sPlayer;

    private Player(){

        setAttributesToDefault();

    }


    public static Player newInstance(){

        if(sPlayer == null){

            sPlayer = new Player();
        }

        return sPlayer;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isHasPowerUp() {
        return hasPowerUp;
    }

    public void setHasPowerUp(boolean hasPowerUp) {
        this.hasPowerUp = hasPowerUp;
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(float timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPausesGame() {
        return pausesGame;
    }

    public void setPausesGame(boolean pausesGame) {
        this.pausesGame = pausesGame;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public void setLivesLeft(int livesLeft) {
        this.livesLeft = livesLeft;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setAttributesToDefault(){

        setScore(0);
        setLevel(1);
        setLivesLeft(3);
        setHasPowerUp(false);
        setPausesGame(false);

    }

    public void incrementScore(int byHowMuch){

        score += byHowMuch;

    }

}
