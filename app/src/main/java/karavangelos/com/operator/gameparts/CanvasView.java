package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/14/15.
 */
public class CanvasView extends View implements View.OnClickListener{

    private static final String TAG = "CanvasView";
    private Context context;

    private int verticalGridBreaks;                                                                 //helps determine distance between vertical grid lines
    private int horizontalGridBreaks;                                                               //helps determine distance between horizontal grid lines

    private PlayerBars playerBars;                                                                  //player bars object which interacts with the canvas.
    private ArrayList<Quadrant> quadrants;                                                          //container of quadrants

    private TextView scoreTextView;
    private TextView livesTextView;
    private TextView timerTextView;
    private TextView levelTextView;
    private Button powerUpButton;
    private Button pauseButton;
    private Button gameButton;

    private Player player;

    private boolean mismatchedHit;
    private boolean defaultsAreSet;




    //constructor.  Assigns member context variable from inherited parent
    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        verticalGridBreaks = 7;
        horizontalGridBreaks = 11;

        playerBars = new PlayerBars(c, attrs);
        playerBars.setBarPaint();

        setUpTheQuadrants(c, attrs);

        player = Player.newInstance();

        mismatchedHit = false;
        defaultsAreSet = true;


    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    // override onDraw.  Where ALL the magic happens
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(context.getResources().getColor(R.color.white));
        runGamingSequenceIfLevelActive(canvas, playerBars);
        runLifeLostSequenceWhenMismatchedHit(canvas, playerBars);
        displayLifeLostMessageWhenMismatchedHit(canvas);
        runLevelClearedSequenceWhenTimeLeftAtZero(canvas, playerBars);


    }


    //On touch events.  Player operations all fall within this block of programming.
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int pointerCount = event.getPointerCount();
        int thePointer = event.getPointerId(pointerCount - 1);

        int x = 0;
        int y = 0;


        if(thePointer == 0) {

            y = (int) event.getY();
            x = (int) event.getX();
        }

        if( thePointer == 0) {

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:


                        checkIfTheBarsGotTouched(x, y);

                    break;
                case MotionEvent.ACTION_MOVE:


                        moveVerticalOrHorizontalBars(x, y);

                    break;
                case MotionEvent.ACTION_UP:

                    alignPlayerBarsAndDisableMovements();
                    break;
            }

            return true;

        }

        alignPlayerBarsAndDisableMovements();
      //  Log.d(TAG, "dropped down on touch, pointer count is " + pointerCount);
        return false;
    }

    @Override
    public void onClick(View v) {

        if(v == powerUpButton){

            Log.d(TAG, "pushed the power up button");

        }

        if(v == pauseButton){

            Log.d(TAG, "pushed the pause button");

        }

        if(v == gameButton){

            startGameOrLevel();

        }


    }


    //end override methods.
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //getters and setters
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setContext(Context context) {
        this.context = context;
    }

    public TextView getScoreTextView() {
        return scoreTextView;
    }

    public void setScoreTextView(TextView scoreTextView) {
        this.scoreTextView = scoreTextView;
    }

    public TextView getLivesTextView() {
        return livesTextView;
    }

    public void setLivesTextView(TextView livesTextView) {
        this.livesTextView = livesTextView;
    }

    public Button getPowerUpButton() {
        return powerUpButton;
    }

    public void setPowerUpButton(Button powerUpButton) {
        this.powerUpButton = powerUpButton;

        this.powerUpButton.setOnClickListener(this);
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(Button pauseButton) {
        this.pauseButton = pauseButton;

        this.pauseButton.setOnClickListener(this);
    }

    public Button getGameButton() {
        return gameButton;
    }

    public void setGameButton(Button gameButton) {
        this.gameButton = gameButton;

        this.gameButton.setOnClickListener(this);
    }

    public TextView getTimerTextView() {
        return timerTextView;
    }

    public void setTimerTextView(TextView timerTextView) {
        this.timerTextView = timerTextView;
    }

    public TextView getLevelTextView() {
        return levelTextView;
    }

    public void setLevelTextView(TextView levelTextView) {
        this.levelTextView = levelTextView;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    //drawing specific methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void setUpTheQuadrants(Context c, AttributeSet attrs){

        quadrants = new ArrayList<Quadrant>();

        for(int i = 1; i < 5; i ++){

            Quadrant quadrant = new Quadrant(c, attrs, i);
            quadrants.add(quadrant);

        }
    }

    private void activateTheQuadrantsOnLevelStart(){

        for(int i = 0; i < quadrants.size(); i++){

            quadrants.get(i).runProcessForCallingSliders();
        }

    }



    //The following 2 methods draw the vertical and horizontal lines on the canvas by measuring the canvas size and making 5 drawing
    //iterations across the canvas.  Distance of drawing the lines is determined by grabbing the canvas
    //size and dividing it by 6.  Set up of the method allows for it to scale correctly regardless of
    //the size of the device using the application.
    private void drawVerticalLines(Canvas canvas){

       Paint linePaint = getLinePaint(canvas);
        int canvasWidth = canvas.getWidth();
        int iterator = getIterator(canvasWidth, verticalGridBreaks);
        int verticalLineMarker = iterator;

     //   sliderRight = iterator;

        for(int i = 0; i < verticalGridBreaks; i++){

            if(i < (verticalGridBreaks - 1)) {

                canvas.drawLine(verticalLineMarker, 0, verticalLineMarker, canvas.getHeight(), linePaint);
                verticalLineMarker += iterator;
            }
        }
    }


    private void drawHorizontalLines(Canvas canvas){

        Paint linePaint = getLinePaint(canvas);
        int canvasHeight = canvas.getHeight();
        int iterator = getIterator(canvasHeight, horizontalGridBreaks);
        int horizontalLineMarker = iterator;

      //  sliderBottom = iterator;

        for(int i = 0; i < horizontalGridBreaks; i++){

            if(i < (horizontalGridBreaks - 1) ) {

                canvas.drawLine(0, horizontalLineMarker, canvas.getWidth(), horizontalLineMarker, linePaint);
                horizontalLineMarker += iterator;

            }
        }
    }


    //gets a full integer number to create both the width of the horizontal and vertical bars, and
    //also measures the space between rows/columns on the canvas.
    private int getIterator(int widthOrHeight, int gridBreaks){

        int iterator = 0;
        int lWidthOrHeight = widthOrHeight;

        while ( (lWidthOrHeight % gridBreaks) != 0 ){

            lWidthOrHeight --;

        }

        iterator = (lWidthOrHeight / gridBreaks);

        return iterator;
    }


    //determines the width of the grid lines based
    //on the width of the canvas on the screen.
    //once again, this is a scaled method.
    private int getGridStrokeWidth(Canvas canvas){

        int width = canvas.getWidth();
        int strokewidth = (int) (width * .01);

        return strokewidth;
    }


    //sets up a paint object to help paint the grid
    //lines on the canvas.
    private Paint getLinePaint(Canvas canvas){

        Paint linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.black));
        linePaint.setStrokeWidth(getGridStrokeWidth(canvas));

        return linePaint;
    }

    private void drawTheLines(Canvas canvas){

        drawVerticalLines(canvas);
        drawHorizontalLines(canvas);

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    //onTouch assistance methods.
    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    //checks whether the horizontal and/or vertical bars have been touched down.
    //also checks whether the player has attempted to touch the cross-path between the 2.
    //if that's the case, then the touch movements are completely ignored
    private void checkIfTheBarsGotTouched(int x, int y){

      //  if(!playerBars.touchedTheExactCenter(x, y) ) {

            playerBars.touchedTheVerticalBar(x);
            playerBars.touchedTheHorizontalBar(y);
     //   }
    }


    //moves the vertical and horizontal bars if they have been touched down by the player
    private void moveVerticalOrHorizontalBars(int x, int y){

        playerBars.moveTheVerticalBar(x);
        playerBars.moveTheHorizontalBar(y, horizontalGridBreaks);


    }


    //sets the on-touch recognition on both player bars to false and puts aligns them to their respective
    //positions based on their current location on the canvas.  See commentary on the Player Bars class
    //for more detail on how these methods work.
    private void alignPlayerBarsAndDisableMovements(){

        playerBars.setTouchedTheVerticalBar(false);
        playerBars.setTouchedTheHorizontalBar(false);

        playerBars.moveVerticalBarToRowActionUp();
        playerBars.moveHorizontalBarToRowActionUp(horizontalGridBreaks);

    }

    //runs the 4 quadrants that control the sliders that move across the screen.
    //Should the operator hit one of the slider objects, then the mismatched hit
    //flag is raised on the canvas View level in order to start the life lost and
    //restart level sequence.
    private void runTheQuadrants(Canvas canvas, PlayerBars playerBars){

        for(int i = 0; i < quadrants.size(); i++){

            quadrants.get(i).performSliderActivities(canvas, playerBars);


            if(quadrants.get(i).isMismatchedHit()){

                mismatchedHit = true;

            }
        }
    }




    //main method that runs the game.  The activity from the player bars, the quadrants that control
    //the sliders, and the scoring, lives left, and power up status are maintained through this method.
    //This is what is used within the main ondraw method.
    private void runTheGame(Canvas canvas, PlayerBars playerBars){

        playerBars.setUserBarStartingCoordinates(canvas, horizontalGridBreaks, verticalGridBreaks);
        playerBars.drawTheBars(canvas);
        runTheQuadrants(canvas, playerBars);
        drawTheLines(canvas);
        invalidate();
        setTheScore();
        setTheLives();
        setPowerUp();
        setTimeLeft();
        setLevel();

        player.setOperatorCounter(playerBars.checkToRebootOperatorColor(player.getOperatorCounter() ) );
    }




    private boolean checkForOneTouch(int pointerCount){

        if(pointerCount == 1){

            return true;
        }

        return false;

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //stops the quadrant handler and runnables in the event that the application is no longer on
    //the front of the device stack.
    public void freezeTheGame(){

        for(int i = 0; i < quadrants.size(); i++){

            quadrants.get(i).stopTheHandlerAndRunnable();
        }
    }

    //maintains the player's score on the visual level on the screen.
    private void setTheScore(){

        scoreTextView.setText("" + player.getScore());
    }

    //maintains the player's lives remaining on the visual level on the screen.
    private void setTheLives(){

        livesTextView.setText("" + player.getLivesLeft());
    }

    private void setPowerUp(){

        powerUpButton.setText(context.getString(R.string.none));
    }


    private void setTimeLeft(){

        timerTextView.setText("Time Left: " +  player.timeLeftFormatted());

    }

    private void setLevel(){

        levelTextView.setText("Level: " + player.getLevel());

    }


    //action taken when the player starts a new level, life or game.  All default flag values
    //are flipped to initiate the gaming sequence and the start button is de-activated to prevent
    //multiple games.  The player values are reset to their defaults if the player pushes to start
    // the game after they have lost all their lives.
    private void startGameOrLevel(){

        //Log.d(TAG, "pushed the game button");
        if(!player.isLevelRebooted() ){


            activateTheQuadrantsOnLevelStart();
            gameButton.setClickable(false);
            gameButton.setTextColor(getResources().getColor(R.color.light_gray));
            defaultsAreSet = false;
            playerBars.setBlackRectBackToZero();
            mismatchedHit = false;

            player.setLevelRebooted(true);
            player.setTimeInLevel();
            player.runTimeLeft();
            player.setOperatorCounter(0);

            if(player.getLivesLeft() < 0){

                player.setScore(0);
                player.setLivesLeft(3);

            }
        }

    }



    //as long as there is no detection of a mismatched hit between the operator and
    //one of the sliders, then the game continues to go.
    private void runGamingSequenceIfLevelActive(Canvas canvas, PlayerBars playerBars){

        if(player.isLevelRebooted() ){

            if(!mismatchedHit){

                runTheGame(canvas, playerBars);
            }

        } else if(!player.isLevelRebooted() && !mismatchedHit && player.getTimeLeft() > -1) {

            Paint textPaint = new Paint();

            textPaint.setColor(getResources().getColor(R.color.black));
            textPaint.setTextSize(40);
            canvas.drawText("Push the start button to begin the game.", 100, canvas.getHeight() / 2, textPaint);
            invalidate();

        }
    }


    //takes place whenever a mismatched hit occurs between the operator and a slider.
    //a black rectangle shoots across the screen to simulate a life being lost.
    //then, all the default flags and values are reset to prepare the player to use their
    //next life for the next round of the game.  The game button is re-activated so that the
    //player can start the next round if they have any lives remaining.
    private void runLifeLostSequenceWhenMismatchedHit(Canvas canvas, PlayerBars playerBars){

        if(mismatchedHit) {

            playerBars.drawTheBars(canvas);
            drawTheLines(canvas);
            playerBars.expandBlackRectIfMismatchedHit(canvas);
            invalidate();

            if(!defaultsAreSet){

                player.setLevelRebooted(false);
                player.subtractOneLife();
                player.killTheTimerProcess();
                rebootTheSlidersAndQuadrants();
                gameButton.setClickable(true);
                gameButton.setTextColor(getResources().getColor(R.color.black));
                defaultsAreSet = true;

            }
        }
    }

    private void runLevelClearedSequenceWhenTimeLeftAtZero(Canvas canvas, PlayerBars playerBars){

        if(player.getTimeLeft() < 0) {

            playerBars.drawTheBars(canvas);
            drawTheLines(canvas);
            playerBars.expandOperatorRectIfLevelCleared(canvas);
            invalidate();

            timerTextView.setText("CLEAR!!");

            if(!defaultsAreSet){

                player.setLevelRebooted(false);
                player.levelUp();
                player.killTheTimerProcess();
                rebootTheSlidersAndQuadrants();
                gameButton.setClickable(true);
                gameButton.setTextColor(getResources().getColor(R.color.black));
                defaultsAreSet = true;

                player.incrementMinimumOrMaximumSliderSpeed();



            }


            displayLevelClearedMessage(canvas);
        }
    }




    //once the black rectangle covers the screen, a message flashes to notify the player
    //that they have lost a life. If they still have lives remaining, they can play again.
    //Otherwise, they will be prompted that the game is over and they must start again.
    private void displayLifeLostMessageWhenMismatchedHit(Canvas canvas){

        if (playerBars.blackRectHasExpanded(canvas) ){

            playerBars.setStarterBarsAreSet(false);
            Paint textPaint = new Paint();
            textPaint.setColor(getResources().getColor(R.color.white));
            textPaint.setTextSize(40);


            if(player.getLivesLeft() >= 0) {

                canvas.drawText("Oops, you hit the wrong slider!", 100, canvas.getHeight() / 2, textPaint);
                canvas.drawText("Push start to continue", 100, ( canvas.getHeight() /2 ) + 60, textPaint);

            } else {

                canvas.drawText("GAME OVER", 100, canvas.getHeight() / 2, textPaint);
                canvas.drawText("Push start to play again", 100, ( canvas.getHeight() /2 ) + 60, textPaint);
                player.setLevel(1);

            }
        }
    }

    //once the black rectangle covers the screen, a message flashes to notify the player
    //that they have lost a life. If they still have lives remaining, they can play again.
    //Otherwise, they will be prompted that the game is over and they must start again.
    private void displayLevelClearedMessage(Canvas canvas){

        if (playerBars.operatorRectRectHasExpanded(canvas) ){

            playerBars.setStarterBarsAreSet(false);
            Paint textPaint = new Paint();
            textPaint.setColor(getResources().getColor(R.color.white));
            textPaint.setTextSize(40);

            canvas.drawText("Level Cleared!", 100, canvas.getHeight() / 2, textPaint);
            canvas.drawText("Push start to continue", 100, ( canvas.getHeight() /2 ) + 60, textPaint);



        }
    }

    //resets all the default values on the quadrant level in order to re-start the level
    //or go to the next level with a clean slate.  Only the player's score is maintained.
    private void rebootTheSlidersAndQuadrants() {

        for (int i = 0; i < quadrants.size(); i++) {

            quadrants.get(i).resetAllOnMismatchHit();

        }
    }
}
