package karavangelos.com.operator.gameparts;

import android.os.Handler;

/**
 * Created by karavangelos on 6/19/15.
 */
public class Player {

    private static final String TAG = "player";                                                     //logging tag


    private int score;                                                                              //player score
    private int livesLeft;                                                                          //number of lives a player has left
    private int level;                                                                              //player's current level
    private float timeLeft;                                                                         //time left on the current level until it is cleared
    private int operatorCounter;
    private boolean hasPowerUp;                                                                     //flag that tells whether the player is elligible for a power up
    private boolean pausesGame;                                                                     //flag that tells whether the game is currently paused
    private boolean hitWrongSlider;                                                                 //tells whether the player has hit a mis-matching slider object.
    private boolean levelRebooted;                                                                  //states whether a level has been properly prepared for launch.

    private int minimumSliderSpeed;
    private int maximumSliderSpeed;
    private int minumumSliderLaunchTime;
    private int maximumSliderLaunchTime;

    private Handler timeLeftHandler;
    private Runnable timeLeftRunnable;


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

    public int getOperatorCounter() {
        return operatorCounter;
    }

    public void setOperatorCounter(int operatorCounter) {
        this.operatorCounter = operatorCounter;
    }

    public int getMaximumSliderLaunchTime() {
        return maximumSliderLaunchTime;
    }

    public void setMaximumSliderLaunchTime(int maximumSliderLaunchTime) {
        this.maximumSliderLaunchTime = maximumSliderLaunchTime;
    }

    public int getMaximumSliderSpeed() {
        return maximumSliderSpeed;
    }

    public void setMaximumSliderSpeed(int maximumSliderSpeed) {
        this.maximumSliderSpeed = maximumSliderSpeed;
    }

    public int getMinimumSliderSpeed() {
        return minimumSliderSpeed;
    }

    public void setMinimumSliderSpeed(int minimumSliderSpeed) {
        this.minimumSliderSpeed = minimumSliderSpeed;
    }

    public int getMinumumSliderLaunchTime() {
        return minumumSliderLaunchTime;
    }

    public void setMinumumSliderLaunchTime(int minumumSliderLaunchTime) {
        this.minumumSliderLaunchTime = minumumSliderLaunchTime;
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
        timeLeftHandler = new Handler();
        operatorCounter = 0;
        setMinimumSliderSpeed(2);
        setMaximumSliderSpeed(5);

    }

    //increment the player score by a certain value
    //whenever the correct action occurs.
    public void incrementScore(int byHowMuch){

        score += byHowMuch;

    }

    //subtract from the lives left when player loses a life
    public void subtractOneLife(){

        livesLeft--;
    }

    //Add one life to the livesLeft variable when player
    //is eligible for an extra life.
    public void AddOneLife(){

        livesLeft++;
    }

    //calculates the amount of time in seconds that will be added to the
    //default starting time amount of the game.
    //Used in the setTimeInLevel() method below.
    private float levelTime(){

        float levelTime = 5 * level;
        return levelTime;
    }

    public void setTimeInLevel(){

        timeLeft = 21 + levelTime();

    }


    public void levelUp(){

        setLevel(level + 1);

    }



    public void runTimeLeft(){

        timeLeftRunnable = new Runnable() {
            @Override
            public void run() {

                timeLeftHandler.postDelayed(this, 1000);

                timeLeft -= 1;
                operatorCounter++;

            }
        };

        timeLeftRunnable.run();
    }

    public void killTheTimerProcess(){

        timeLeftHandler.removeCallbacks(timeLeftRunnable);
        timeLeftRunnable = null;

    }


    //Most involved method in the player class.
    //Takes the current vlue of the timeLeft float variable
    //and converts it into an integer value.
    //The value is then passed through multiple
    //filters to place it into a readable countdown time
    //format and then is returned as a string value.
    //Used within the canvasView.
    public String timeLeftFormatted(){

        int theTime = (int) timeLeft;
        String timeAsString = "";

        if(theTime < 60) {

            if(theTime < 10) {

                timeAsString = "00:0"+theTime;

            } else {

                timeAsString = "00:" + theTime;

            }
        }

        if(theTime > 60){

            int minutesLeft = theTime / 60;
            int secondsLeft = theTime % 60;
            String formatMinutes = "";
            String formatSeconds = "";


            if(minutesLeft < 10){

                formatMinutes = "0" + minutesLeft;


            } else {

                formatMinutes = String.valueOf(minutesLeft);

            }




            if(secondsLeft < 10) {

                formatSeconds = "0" + secondsLeft;

            } else {

                formatSeconds = String.valueOf(secondsLeft);

            }

            timeAsString = formatMinutes + ":" + formatSeconds;

        }

        return timeAsString;
        //return String.valueOf(theTime);
    }

    public void incrementSpeedOrSliderLaunch(){

        int remainder = (level % 2);

        if(remainder == 1){

            setMinimumSliderSpeed(minimumSliderSpeed + 1);

        } else {

            setMaximumSliderSpeed(maximumSliderSpeed + 1);

        }

    }





}
