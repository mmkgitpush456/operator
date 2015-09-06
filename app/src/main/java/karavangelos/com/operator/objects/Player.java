package karavangelos.com.operator.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import karavangelos.com.operator.R;

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
    private boolean paused;                                                                         //tells whether or not the current game in activity has been paused or not.

    private int minimumSliderSpeed;                                                                 //absolute minimum speed that a slider can inherit during a course of the game.
    private int maximumSliderSpeed;                                                                 //absolute maximum speed that a slider can inherit during a course of the game.
    private int minimumQuadrantTimeOut;                                                             //absolute minimum amount of time before slider releases on a given quadrant
    private int maximumQuadrantTimeOut;                                                             //absolute maximum amount of time before slider releases on a given quadrant

    private Handler timeLeftHandler;                                                                //handler that handles the level countdown.
    private Runnable timeLeftRunnable;                                                              //runnable that handles the level countdown.

    private Context context;
    private SharedPreferences preferences;
    private SoundPool sp;
    private int sound;

    private static Player sPlayer;                                                                  //static player instance.  Ensures only one player object will be created throughout the app

    //constructor.  When a player object is created,
    //the default attributes will be set so a new game is
    //ready to begin.
    private Player(Context context){

        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        setAttributesToDefault();

    }


    //Singleton instance of the player object
    public static Player newInstance(Context context){

        if(sPlayer == null){

            sPlayer = new Player(context);
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

    public int getMaximumQuadrantTimeOut() {
        return maximumQuadrantTimeOut;
    }

    public void setMaximumQuadrantTimeOut(int maximumQuadrantTimeOut) {
        this.maximumQuadrantTimeOut = maximumQuadrantTimeOut;
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

    public int getMinimumQuadrantTimeOut() {
        return minimumQuadrantTimeOut;
    }

    public void setMinimumQuadrantTimeOut(int minimumQuadrantTimeOut) {
        this.minimumQuadrantTimeOut = minimumQuadrantTimeOut;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
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
        setMinimumQuadrantTimeOut(4);
        setMaximumQuadrantTimeOut(6);
        setPaused(false);
        setTimeInLevel();
      //  timeLeftRunnable = null;

      //  Log.d(TAG, "setting attributes to default");

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

    //set the total time of the current level.  The levelTime method is
    //used to calculate the amount of time within the current level.
    public void setTimeInLevel(){

//        timeLeft = 0 + levelTime();
        timeLeft = 21 + levelTime();

    }


    //level up should the player clear the current level.
    //used within the runLevelClearedSequenceWhenTimeLeftAtZero
    //method on the canvas view.
    public void levelUp(){

        setLevel(level + 1);

    }



    //handler and runnable method that runs the countdown of the timer at the top
    //left of the screen.  Used within the startGameOrLevel of the canvas view as well
    //as the pauseOrResumeTheTimer below.
    public void runTimeLeft(){

        timeLeftRunnable = new Runnable() {
            @Override
            public void run() {

                if(!isPaused()){

                    timeLeftHandler.postDelayed(this, 1000);

                    timeLeft -= 1;
                    operatorCounter++;

                  //  Log.d(TAG, "If you see this, the player timer is working correctly");
                }
            }
        };

        timeLeftRunnable.run();
    }


    //should the local paused flag be flipped to true, the timeLeftHandler is detached from its calls
    //to the timeLeftRunnable.  Otherwise, the runTimeLeft method is re-instituted and the timer
    //continues to count down.  Used within the pauseTheGame on the canvas view.
    public void pauseOrResumeTheTimer(){

        if(isPaused()){

            timeLeftHandler.removeCallbacks(timeLeftRunnable);

        } else {

            runTimeLeft();

        }

    }


    //completely kill off the timeLeftHandler's association to the
    //timeLeftRunnable and nullify the timeLeftRunnable.
    //Used within the runLifeLostSequenceWhenMismatchedHit
    //as well as the runLevelClearedSequenceWhenTimeLeftAtZero method
    //on the canvas view.
    public void killTheTimerProcess(){

        timeLeftHandler.removeCallbacks(timeLeftRunnable);
        timeLeftRunnable = null;

    }

    public void nullifyHandler(){

        timeLeftHandler = null;
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


    //Adjusts a value used for calling the speed of the sliders as well as the
    //delay time used to calculate their activations.  A local value called increment is used
    //which obtains a modulous remained from the level the player has reached.  Should the increment
    //value match one of the values shown below, either the minimum slider speed, maximum slider speed,
    //minimum quadrant timeour, or maximum timeout will be updated to periodically increase the game's difficulty.
    //Used within the runLevelClearedSequenceWhenTimeLeftAtZero method on the canvas view.
    public void incrementMinimumOrMaximumSliderSpeed(){

        int increment = (level % 7);
        if(increment == 3){

            if(minimumSliderSpeed != 12) {

                setMinimumSliderSpeed(minimumSliderSpeed + 1);

            }

        } else if(increment == 4) {


            if(maximumSliderSpeed != 16) {

                setMaximumSliderSpeed(maximumSliderSpeed + 1);
            }
        }


        if(increment == 5){

            if (minimumQuadrantTimeOut != 2) {
                setMinimumQuadrantTimeOut(minimumQuadrantTimeOut - 1);
            }



        } else if(increment == 6){

            if(maximumQuadrantTimeOut != 3) {

                setMaximumQuadrantTimeOut(maximumQuadrantTimeOut -1);

            }
        }
    }

    //returns today's date in MM/DD/YY format.
    //this method is used to show when a score was
    //entered into the high scores database table.
    public String getTodaysDate(){

        Date date = new Date();
        String DATE_FORMAT = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String todaysDate = sdf.format(date);

        return todaysDate;
    }


    //refreshes all player variables to their default state
    //prior to the launch of a new game.  This method is used
    //in the event that a player leaves a game that is not completed
    //so that the next time they start a game, it is brand new.
    public void setBaseDefaultsOnLevelStart(){
        setLevelRebooted(true);
        setTimeInLevel();
        setPaused(false);
        runTimeLeft();
        setOperatorCounter(0);
    }

    //checks to see if the current score is elligible for adding
    //an extra life to the player's lives count.  If the score is
    //divisible by 100, then another life is added.
    private boolean scoreIsElligibleForExtraLife(){

        boolean isElligible = false;

        if( (score % 100) == 0 ){

            isElligible = true;
        }

        return isElligible;
    }

    //adds an extra life to the player's lives count when elligible.
    //used within the runTheCollisionLogic method of the slider class
    //since every slider reacts with the operator.
    public void addLifeIfElligible(){

        if(scoreIsElligibleForExtraLife()){

            livesLeft++;
        }

    }

///////////////////////////////
//saves a new preference based on the user input.  Utilized on the settings fragments

    public void saveInPreferences(String attribute, String value){

        SharedPreferences.Editor spe = preferences.edit();
        spe.putString(attribute, value);
        spe.commit();
        //  Log.d(TAG, "saved " + attribute + " with value " + value);
    }


    //retrieves a previously saved user value to the requested view
    public String retrieveSavedPreference(String attribute){

        String value = preferences.getString(attribute, "0");
        return value;
    }


    //public method used for playing a certain sound across the game.
    //It uses the soundpool instance which has been deprecated as of Android
    //API 20.  However, since the game supports API 15, soundpool is ok to use in this case.
    public void playTheSound(int soundFile){

        boolean soundIsOn = soundIsOn();

        if(soundIsOn) {

            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            sound = sp.load(context, soundFile, 2);
            sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId,
                                           int status) {
                    soundPool.play(sound, 20, 20, 1, 0, 1f);
                }
            });
        }
    }


    private boolean soundIsOn(){

        boolean soundIsOn = true;

        String soundStatus = retrieveSavedPreference(context.getString(R.string.sound_status) ) ;


        if(soundStatus.equals(context.getString(R.string.sound_off) ) ){

            soundIsOn = false;
        }

        return soundIsOn;
    }

///////////////////////////////





}
