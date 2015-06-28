package karavangelos.com.operator.gameparts;

/**
 * Created by karavangelos on 6/19/15.
 */
public class Player {

    private static final String TAG = "player";                                                     //logging tag


    private int score;                                                                              //player score
    private int livesLeft;                                                                          //number of lives a player has left
    private int level;                                                                              //player's current level
    private float timeLeft;                                                                         //time left on the current level until it is cleared
    private boolean hasPowerUp;                                                                     //flag that tells whether the player is elligible for a power up
    private boolean pausesGame;                                                                     //flag that tells whether the game is currently paused
    private boolean hitWrongSlider;                                                                 //tells whether the player has hit a mis-matching slider object.
    private boolean levelRebooted;                                                                  //states whether a level has been properly prepared for launch.

    private static Player sPlayer;                                                                  //static player instance.  Ensures only one player object will be created throughout the app

    //constructor.  When a player object is created,
    //the default attributes will be set so a new game is
    //ready to begin.
    private Player(){

        setAttributesToDefault();

    }


    //Singleton instance of the player object
    public static Player newInstance(){

        if(sPlayer == null){

            sPlayer = new Player();
        }

        return sPlayer;
    }


    //getters and setters
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

    public boolean isHitWrongSlider() {
        return hitWrongSlider;
    }

    public void setHitWrongSlider(boolean hitWrongSlider) {
        this.hitWrongSlider = hitWrongSlider;
    }

    public boolean isLevelRebooted() {
        return levelRebooted;
    }

    public void setLevelRebooted(boolean levelRebooted) {
        this.levelRebooted = levelRebooted;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    //set the defaults before a new game.
    //score to 0
    //level starts at 1
    //number of lives starts at 3
    //no power ups to start
    //game is not paused at start
    //wrong slider has not been hit before game starts
    public void setAttributesToDefault(){

        setScore(0);
        setLevel(1);
        setLivesLeft(3);
        setHasPowerUp(false);
        setPausesGame(false);
        setHitWrongSlider(false);
        setLevelRebooted(false);

    }

    //increment the player score by a certain value
    //whenever the correct action occurs.
    public void incrementScore(int byHowMuch){

        score += byHowMuch;

    }


}
