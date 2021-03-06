package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

import karavangelos.com.operator.R;
import karavangelos.com.operator.objects.Player;

/**
 * Created by karavangelos on 3/26/15.
 */

//the slider class controls all the boxes that move across, up, and down the canvas and which may
// collide with the operator object.
public class Slider extends View{

    private static final String TAG = "Slider";

    private Rect theSlider;

    private int sliderLeft;                                                                         //slider left coordinate
    private int sliderTop;                                                                          //slider top coordinate
    private int sliderRight;                                                                        //slider right coordinate
    private int sliderBottom;                                                                       //slider bottom coordinate
    private int sliderWidth;                                                                        //width of the slider object
    private int sliderHeight;                                                                       //height of the slider object

    private int operatorLeft;                                                                       //operator coordinates pulled into the class for collision detection
    private int operatorTop;
    private int operatorRight;
    private int operatorBottom;

    private int quadrantKey;                                                                        //integer key that decides where to manifest the slider rectangle from.  1 for left, 2 for top, 3 for right, 4 for bottom.
    private int vectorKey;                                                                          //integer key that determines where to draw the new slider according to the grid lines
    private int paintKey;                                                                           //integer key that determines what color to paint the slider with.
    private int sliderSpeed;                                                                        //speed of the slider object.  Determined according to numerous factors.
    private Paint sliderPaint;                                                                      //paint that will color the slider rectangle.

    private boolean startingPositionsEstablished;                                                   //flag that tells whether or not the starting positions of the slider have been defined.
    private Context context;                                                                        //member variant of global context
    private boolean hasCollided;                                                                    //helper flag that tells whether the operator and this slider have collided.
    private boolean colorsMatch;                                                                    //helper flag that tells whether the operator and this Slider's colors match
    private boolean isDissolved;                                                                    //checks to tell if and when this slider has been visibly dissolved from the screen.
    private boolean isMismatch;

    private boolean hasIncrementedScore;                                                            //increments player score whenever a matching color hit occurs.

    private int killCollide;

    private Player player;                                                                          //player singleton instance.
    private String difficultyStatus;
    private int totalPaintNumber;


    public Slider(Context c, AttributeSet attrs, int quadrantKey) {
        super(c, attrs);
        context = c;

        killCollide = 0;
        if(quadrantKey == 1 || quadrantKey == 3){

            setVectorKey(getRandomNumber(1, 11));

        }

        if(quadrantKey == 2 || quadrantKey == 4){

            setVectorKey(getRandomNumber(1, 7));
        }

        player = Player.newInstance(context);
        difficultyStatus = player.getDifficultyStatus();
        totalPaintNumber = getTotalPaintNumber();
        setConstructorProtocol(quadrantKey);

    }

    //getters and setters
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public int getSliderLeft() {
        return sliderLeft;
    }
    public void setSliderLeft(int sliderLeft) {
        this.sliderLeft = sliderLeft;
    }
    public int getSliderTop() {
        return sliderTop;
    }
    public void setSliderTop(int sliderTop) {
        this.sliderTop = sliderTop;
    }
    public int getSliderRight() {
        return sliderRight;
    }
    public void setSliderRight(int sliderRight) {
        this.sliderRight = sliderRight;
    }
    public int getSliderBottom() {
        return sliderBottom;
    }
    public void setSliderBottom(int sliderBottom) {
        this.sliderBottom = sliderBottom;
    }
    public boolean isStartingPositionsEstablished() {
        return startingPositionsEstablished;
    }
    public void setStartingPositionsEstablished(boolean startingPositionsEstablished) {
        this.startingPositionsEstablished = startingPositionsEstablished;
    }
    public int getSliderSpeed() {
        return sliderSpeed;
    }
    public void setSliderSpeed(int sliderSpeed) {
        this.sliderSpeed = sliderSpeed;
    }
    public int getPaintKey() {
        return paintKey;
    }
    public void setPaintKey(int paintKey) {
        this.paintKey = paintKey;
    }
    public int getVectorKey() {
        return vectorKey;
    }
    public void setVectorKey(int vectorKey) {
        this.vectorKey = vectorKey;
    }
    public int getQuadrantKey() {
        return quadrantKey;
    }
    public void setQuadrantKey(int quadrantKey) {
        this.quadrantKey = quadrantKey;
    }

    public int getSliderWidth() {
        return sliderWidth;
    }

    public void setSliderWidth(int sliderWidth) {
        this.sliderWidth = sliderWidth;
    }

    public int getSliderHeight() {
        return sliderHeight;
    }

    public void setSliderHeight(int sliderHeight) {
        this.sliderHeight = sliderHeight;
    }

    public boolean isHasCollided() {
        return hasCollided;
    }

    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }

    public boolean isDissolved() {
        return isDissolved;
    }

    public void setIsDissolved(boolean isDissolved) {
        this.isDissolved = isDissolved;
    }

    public boolean isMismatch() {
        return isMismatch;
    }

    public void setIsMismatch(boolean isMismatch) {
        this.isMismatch = isMismatch;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    //assigns where the slider will start on the canvas, what color it will be,
    //and which direction on the grid it will be traveling.  Used on the constructor.
    private void setConstructorProtocol(int quadrantKey){

        theSlider = new Rect();
        startingPositionsEstablished = false;
        setThePaint();
        this.quadrantKey = quadrantKey;

        sliderSpeed = getRandomNumber(player.getMinimumSliderSpeed(), player.getMaximumSliderSpeed());

        hasCollided = false;
        isDissolved = true;
        isMismatch = false;
        hasIncrementedScore = false;
    }

    //public method that updates and draws the slider object on the canvas.
    public void drawTheSlider(Canvas canvas){

        theSlider.set(sliderLeft, sliderTop, sliderRight, sliderBottom);
        canvas.drawRect(theSlider, sliderPaint);
        switchCollisionFlagIfCollides();
        moveTheSliderBasedOnQuadrant();
        runTheCollisionLogic();
    }

    //checks to see whether or not a collision has occured between the slider and
    //the operator.  Should a collision occur, the proper process is run depending on whether the
    //operator and slider have matching colors or not.  Used within the drawTheSlider method
    //of this class.
    private void runTheCollisionLogic(){

        if(hasCollided){

            if(colorsMatch) {

                if(!hasIncrementedScore){

                    hasIncrementedScore = true;
                    player.incrementScore(2);
                    player.addLifeIfElligible();
                    player.playTheSound(R.raw.kill_slider);

                }

                killCollide = 1;
                wipeSliderClean();

            } else {

                if(killCollide == 0) {

                    player.setHitWrongSlider(true);
                    setIsMismatch(true);
                    player.playTheSound(R.raw.mismatch_hit);
                }
            }
        }
    }

    public void keepPausedSliderInPlace(Canvas canvas){

        theSlider.set(sliderLeft, sliderTop, sliderRight, sliderBottom);

    }



    //process for killing off the slider object on the screen.
    private void dissolveTheSlider(){

        switch (quadrantKey){

            case 1:

                if(sliderRight > sliderLeft){

                    sliderRight -= 10;
                }

                if(sliderBottom > sliderTop){

                    sliderBottom-= 15;
                    sliderTop+=15;
                }

                break;

            case 2:

                if(sliderRight > sliderLeft){

                    sliderRight -= 10;
                    sliderLeft += 10;
                }

                if(sliderBottom > sliderTop){

                    sliderBottom -= 15;
                }

                break;

            case 3:

                if(sliderRight > sliderLeft){

                    sliderLeft += 10;
                }

                if(sliderBottom > sliderTop){

                    sliderBottom-= 15;
                    sliderTop+=15;
                }

                break;

            case 4:

                if(sliderRight > sliderLeft){

                    sliderRight -= 10;
                    sliderLeft += 10;
                }

                if(sliderBottom > sliderTop){

                    sliderTop += 15;
                }
                break;
        }//end of the switch and case

        checkIfTheSliderIsDissolved();
    }

    //if the slider still needs to shrink after a matching color collision, it is dissolved
    //using the dissolveTheSlider method.  Otherwise, the slider is set to null.
    //Used within the runTheCollisionLogic of this class.
    private void wipeSliderClean(){

        if (!isDissolved) {

            dissolveTheSlider();

        } else {

            // Log.d(TAG, "the slider has been dissolved");
            theSlider = null;


        }

    }

    //helper method that sets the starting location of the slider based on the
    //random quadrant and vector variables set from the constructor.  In each case,
    //the location coordinates are generated so that the slider object is just outside of visibility
    //of the canvas.  This method is run once per slider cycle since the startingPositionsEstablished
    //flag is flipped to true after the positioning has run its course.
    //additional calcuations are placed for the highest vector keys to even out the
    //slider's measurement across the vector that it moves across.
    protected void setSliderCoordinates(PlayerBars playerBars, Canvas canvas){

        if(!startingPositionsEstablished){

            sliderWidth = playerBars.getVertBarWidth();
            sliderHeight = playerBars.getHorzBarWidth();

            switch(quadrantKey){

                case 1: //LEFT

                    sliderRight = 0;



                    sliderBottom = (sliderHeight * vectorKey);


                    if(vectorKey == 11){

                        sliderBottom += (canvas.getHeight() - sliderBottom);

                    }



                    sliderTop = (sliderBottom - sliderHeight);
                    sliderLeft = (sliderRight - sliderWidth);
                    break;

                case 2: //TOP

                    sliderBottom = 0;
                    sliderTop = (sliderBottom - sliderHeight);
                    sliderRight = (sliderWidth * vectorKey);

                    if(vectorKey == 7){

                        sliderRight += (canvas.getWidth() - sliderRight);

                    }

                    sliderLeft = (sliderRight - sliderWidth);
                    break;

                case 3: //RIGHT

                    sliderLeft = canvas.getWidth();
                    sliderRight = (sliderLeft + sliderWidth);
                    sliderBottom = (sliderHeight * vectorKey);

                    if(vectorKey == 11){

                        sliderBottom += (canvas.getHeight() - sliderBottom);

                    }


                    sliderTop = (sliderBottom - sliderHeight);

                    break;

                case 4: //BOTTOM

                    sliderTop = canvas.getHeight();
                    sliderBottom = (sliderTop + sliderHeight);
                    sliderRight = (sliderWidth * vectorKey);

                    if(vectorKey == 7){

                        sliderRight += (canvas.getWidth() - sliderRight);

                    }

                    sliderLeft = (sliderRight - sliderWidth);

                    break;
            }

            startingPositionsEstablished = true;
        }
    }

    private int getTotalPaintNumber(){

        int totalPaintNumber = 0;

        if(difficultyStatus.equals(context.getString(R.string.difficulty_easy))){

            totalPaintNumber = 4;
        }

        if(difficultyStatus.equals(context.getString(R.string.difficulty_medium))){

            totalPaintNumber = 6;
        }

        if(difficultyStatus.equals(context.getString(R.string.difficulty_hard))){

            totalPaintNumber = 8;
        }


        return totalPaintNumber;
    }


    //sets the color of the slider object through the assistance of the key encoder.
    //from the sliderPaintColor method.
    private void setThePaint(){

        sliderPaint = new Paint();

        paintKey = getRandomNumber(1, totalPaintNumber);


        setSliderPaintColor(paintKey);


    }


    //convenience method that sets the paint color of the slider object based on the number key
    //passed in from the argument.
    //1 FOR RED
    //2 FOR BEIGE
    //3 FOR AQUA
    //4 FOR PINK
    private void setSliderPaintColor(int paintColorCode){

        switch (paintColorCode){

            case 1:

                sliderPaint.setColor(getResources().getColor(R.color.red));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;

            case 2:

                sliderPaint.setColor(getResources().getColor(R.color.beige));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;

            case 3:

                sliderPaint.setColor(getResources().getColor(R.color.aqua));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;


            case 4:

                sliderPaint.setColor(getResources().getColor(R.color.pink));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;

            case 5:

                sliderPaint.setColor(getResources().getColor(R.color.dark_green));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;

            case 6:

                sliderPaint.setColor(getResources().getColor(R.color.orange));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;

            case 7:

                sliderPaint.setColor(getResources().getColor(R.color.purple));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;

            case 8:

                sliderPaint.setColor(getResources().getColor(R.color.sand));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;
        }
    }



    //method used to obtain a random number between the min and max arguments.  Min being the lowest
    //number that can be obtained, and max being the highest number that can be obtained.  This method
    //is used for a number of contributing factors across the Slider class.
    protected int getRandomNumber(int min, int max){

        Random random = new Random();

        int randomNumber = random.nextInt(max) + min;
        return randomNumber;
    }


    //moves the slider object depending on the value of the quadrant
    protected void moveTheSliderBasedOnQuadrant(){

        switch (quadrantKey){

            case 1: //LEFT

                moveSliderToTheRight();
                break;


            case 2: //TOP

                moveSliderDown();
                break;


            case 3: //RIGHT

                moveSliderToTheLeft();
                break;

            case 4: //BOTTOM

                moveSliderUp();
                break;

        }
    }


    //the methods below move the slider in the specified directions
    protected void moveSliderToTheRight(){

        sliderLeft += sliderSpeed;
        sliderRight += sliderSpeed;

    }

    protected void moveSliderToTheLeft(){

        sliderLeft -= sliderSpeed;
        sliderRight -= sliderSpeed;

    }

    protected void moveSliderDown(){

        sliderTop += sliderSpeed;
        sliderBottom += sliderSpeed;

    }

    protected void moveSliderUp(){

        sliderTop -= sliderSpeed;
        sliderBottom -= sliderSpeed;
    }


    //the following methods provide interaction with the operator object.
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //convenience method that obtains the current position of the
    public void setOperatorPositions(PlayerBars playerBars){

         operatorLeft =  playerBars.obtainOperatorPosition(context.getString(R.string.left) );
         operatorTop = playerBars.obtainOperatorPosition(context.getString(R.string.top) );
         operatorRight = playerBars.obtainOperatorPosition(context.getString(R.string.right) );
         operatorBottom = playerBars.obtainOperatorPosition(context.getString(R.string.bottom) );
    }


    //convenience method that sets the rules for collision detection between the slider and the operator.
    //if the operator position (left, top, right, or bottom) goes within the listed boudaries of the slider, then
    //a flag is returned signalling that a successful collision has occured.  The process goes through numerous steps
    //of flags to check for a correct geographic collision.
    private boolean isElligibleForCollision(int operatorPosition, int sliderTopOrLeft, int sliderBottomOrRight){

        boolean isElligible = false;

        if( (operatorPosition > sliderTopOrLeft) && (operatorPosition < sliderBottomOrRight) ){

            isElligible = true;
        }

        return isElligible;
    }


    //checks to see whether the vertical boundaries of the operator and the slider are eligible for a collision.
    private boolean verticalCollideIsElligible(){

        boolean isElligible = false;

        if(isElligibleForCollision(operatorTop, sliderTop, sliderBottom) || isElligibleForCollision(operatorBottom, sliderTop, sliderBottom)  ) {

            isElligible = true;

        }

        return isElligible;
    }


    //checks to see whether the horizontal boundaries of the operator and the slide are eligible for collision.
    private boolean horizontalCollideIsElligible(){

        boolean isElligible = false;


        if(isElligibleForCollision(operatorLeft, sliderLeft, sliderRight) || isElligibleForCollision(operatorRight, sliderLeft, sliderRight)  ) {

            isElligible = true;
        }

        return isElligible;
    }


    //helper method that checks for a slider/operator collision
    //if the operator and slider are within the same vector and collide from top down or bottom up
    private boolean linearCollideVertical(){

        if(verticalCollideIsElligible() && (operatorLeft == sliderLeft) ) {
            //Log.d(TAG, "VERTICAL COLLIDE");

            return true;
        }
        return false;
    }



    //helper method that checks for a slider/operator collision
    //if the operator and slider are within the same vector and collide from left to right or right to left
    private boolean linearCollideHorizontal(){

        if(horizontalCollideIsElligible() && operatorTop == sliderTop){

            //Log.d(TAG, "HORIZONTAL COLLIDE");
            return true;

        }

        if(vectorKey == 11){


            if(horizontalCollideIsElligible() && operatorBottom == sliderBottom){

                return true;
            }

        }


        return false;

    }





    //if both the vertical and horizontal boundaries have reached a point of eligibility for collision
    //(the operator and slider have collided), then a flag is returned which signals that a collision has occurred.
    private void checkForCollision(){

        if( (verticalCollideIsElligible() && horizontalCollideIsElligible() ) || (linearCollideVertical() || linearCollideHorizontal())  ){

            hasCollided = true;
        }
    }

    private void switchCollisionFlagIfCollides(){

        if(!hasCollided){

            checkForCollision();
        }
    }

    //When the slider rectangle has completely disappeared from the canvas in terms of visibility,
    //the flag isDissolved is fliopped to true.  The flag assists with nullifying the object and helping
    //with garbage collection.
    private void checkIfTheSliderIsDissolved(){

        if( (sliderLeft > sliderRight) && (sliderTop > sliderBottom) ) {

            isDissolved = true;

        }
    }

    //simple process which constantly checks to make sure that the slider and the operator have matching
    //colors.  The corresponding flag is set accordingly.  This method is run constantly since the operator
    //can change colors over periods of time or through potential power-ups.
    public void checkIfColorMatchesOperatorColor(PlayerBars playerBars){

        if(paintKey == playerBars.getColorKey()) {

            colorsMatch = true;

        } else {

            colorsMatch = false;

        }

    }

    //helper method that checks to see if the slider has left the visible canvas.
    //If it does, then the same process goes into effect to nullify the slider object.
    public void checkIfTheSliderHasPassedTheCanvas(Canvas canvas){

        if(sliderLeft >  (canvas.getWidth() + 10) ){

            isDissolved = true;
        }

        if(sliderTop > (canvas.getHeight() + 10) ) {

            isDissolved = true;
        }

        if(sliderRight < -10){

            isDissolved = true;

        }

        if(sliderBottom < -10){

            isDissolved = true;
        }
    }

    private void logAllCoordinatesForDebugging(){

      //  Log.d(TAG, "the collision has occurred");

        Log.d(TAG, "operatorleft: " + operatorLeft);
        Log.d(TAG, "sliderLeft: " + sliderLeft);

        Log.d(TAG, "operatorTop: " + operatorTop);
        Log.d(TAG, "sliderTop: " + sliderTop);

        Log.d(TAG, "operatorRight: " + operatorRight);
        Log.d(TAG, "sliderRight: " + sliderRight);

        Log.d(TAG, "operatorBottom: " + operatorBottom);
        Log.d(TAG, "sliderBottom: " + sliderBottom);
    }


    //method that maintains all of the slider's movements across the canvas view.
    //the slider's position is assigned first, then it is moved across the canvas
    //based on the quadrant it is set to.
    //Finally, all of the conditional logic is checked to update the slider's status
    //on the game board.
    protected void moveTheSlider(Canvas canvas, PlayerBars playerBars){

        setSliderCoordinates(playerBars, canvas);

        drawTheSlider(canvas);
        setOperatorPositions(playerBars);
        checkIfColorMatchesOperatorColor(playerBars);
        checkIfTheSliderHasPassedTheCanvas(canvas);
    }

    //When a slider makes it across the canvas, or has been
    //dissolved by a matching color operator, it's starting flags are reset.
    //Then, its speed, color, and position on the canvas are updated
    //for the next time it is queued.
    protected void resetTheSlider(int max){

        int one = 1;

        setHasCollided(false);
        setStartingPositionsEstablished(false);
        setIsDissolved(true);

        sliderSpeed = getRandomNumber(player.getMinimumSliderSpeed(), player.getMaximumSliderSpeed());

        setThePaint();
        rebootVector(one, max);
        setIsMismatch(false);
        killCollide = 0;
        hasIncrementedScore = false;



    }

    //sets the position on the quadrant that the slider will be moving across.
    //used withing the resetTheSlider method.
    protected void rebootVector(int min, int max){

        vectorKey = getRandomNumber(min, max);
    }

    //maintains a slider's coordinates should the player decide to pause the game.
    //Used on the performSliderActivities method of the quadrant class.
    protected void setPausedSlider(){

        theSlider.set(sliderLeft, sliderTop, sliderRight, sliderBottom);
    }


    private void playSound(int soundFile){

        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        /** soundId for Later handling of sound pool **/
        int soundId = sp.load(context, soundFile, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(soundId, 1, 1, 0, 0, 1);

    }





}
