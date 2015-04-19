package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

import karavangelos.com.operator.R;

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

    private int operatorLeft;
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


    public Slider(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        setConstructorProtocol();
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

    ////////////////////////////////////////////////////////////////////////////////////////////////


    //assigns where the slider will start on the canvas, what color it will be,
    //and which direction on the grid it will be traveling.  Used on the constructor.
    private void setConstructorProtocol(){

        theSlider = new Rect();
        startingPositionsEstablished = false;
        setThePaint();
        quadrantKey = getRandomNumber(1, 4);
        setVectorAccordingToQuadrant();

        hasCollided = false;
        isDissolved = false;

    }

    //public method that updates and draws the slider object on the canvas.
    public void drawTheSlider(Canvas canvas){


        theSlider.set(sliderLeft, sliderTop, sliderRight, sliderBottom);
        canvas.drawRect(theSlider, sliderPaint);
        switchCollisionFlagIfCollides();
        moveTheSliderBasedOnQuadrant();

        if(hasCollided){

            if(colorsMatch) {

                checkIfTheSliderIsDissolved();
                wipeSliderClean();

            } else {

                Log.d(TAG, "The colors don't match.  You lost a life");

            }
        }
    }

    //process for killing off the slider object on the screen.
    private void dissolveTheSlider(){

        switch (quadrantKey){

            case 1:

                if(sliderRight > sliderLeft){

                    sliderRight -= 4;
                }

                if(sliderBottom > sliderTop){

                    sliderBottom-= 3;
                    sliderTop+=3;
                }

                break;

            case 2:

                if(sliderRight > sliderLeft){

                    sliderRight -= 2;
                    sliderLeft += 2;
                }

                if(sliderBottom > sliderTop){

                    sliderBottom -= 6;
                }

                break;

            case 3:

                if(sliderRight > sliderLeft){

                    sliderLeft += 4;
                }

                if(sliderBottom > sliderTop){

                    sliderBottom-= 3;
                    sliderTop+=3;
                }

                break;

            case 4:

                if(sliderRight > sliderLeft){

                    sliderRight -= 2;
                    sliderLeft += 2;
                }

                if(sliderBottom > sliderTop){

                    sliderTop += 6;
                }
                break;
        }//end of the switch and case
    }

    private void wipeSliderClean(){

        if (!isDissolved) {

            dissolveTheSlider();

        } else {

            // Log.d(TAG, "the slider has been dissolved");
            theSlider = null;

        }

    }


    //generates a random number to be used as a vector coordinate for initial positioning of
    //the slider.  If the quadrant is 1 or 3 (Left of Right of canvas), then the quadrant can
    //be any number between 1 and 11.  Otherwise, for 2 and 4 (top and bottom of canvas),
    //the quadrant can have a max value of 7.
    private void setVectorAccordingToQuadrant(){

        if(quadrantKey == 1 || quadrantKey == 3){

            vectorKey = getRandomNumber(1, 11);

        }

        if(quadrantKey == 2 || quadrantKey == 4){

            vectorKey = getRandomNumber(1, 7);
        }

    }

    //helper method that sets the starting location of the slider based on the
    //random quadrant and vector variables set from the constructor.  In each case,
    //the location coordinates are generated so that the slider object is just outside of visibility
    //of the canvas.  This method is also only run once since the startingPositionsEstablished
    //flag is flipped to true after the positioning has run its course.
    protected void setSliderCoordinates(PlayerBars playerBars, Canvas canvas){

        if(!startingPositionsEstablished){

            sliderWidth = playerBars.getVertBarWidth();
            sliderHeight = playerBars.getHorzBarWidth();

            switch(quadrantKey){

                case 1: //LEFT

                    sliderRight = 0;
                    sliderBottom = (vectorKey * sliderHeight);
                    sliderTop = (sliderBottom - sliderHeight);
                    sliderLeft = (sliderRight - sliderWidth);
                    break;

                case 2: //TOP

                    sliderBottom = 0;
                    sliderTop = (sliderBottom - sliderHeight);
                    sliderRight = (sliderWidth * vectorKey);
                    sliderLeft = (sliderRight - sliderWidth);
                    break;

                case 3: //RIGHT

                    sliderLeft = canvas.getWidth();
                    sliderRight = (sliderLeft + sliderWidth);
                    sliderBottom = (sliderHeight * vectorKey);
                    sliderTop = (sliderBottom - sliderHeight);

                    break;

                case 4: //BOTTOM

                    sliderTop = canvas.getHeight();
                    sliderBottom = (sliderTop + sliderHeight);
                    sliderRight = (sliderWidth * vectorKey);
                    sliderLeft = (sliderRight - sliderWidth);

                    break;
            }

            /*
              Log.d(TAG, "quadrant position: " + quadrantKey);
              Log.d(TAG, "vector key: " + vectorKey);
              Log.d(TAG, "left position: " + sliderLeft);
              Log.d(TAG, "top position: " + sliderTop);
              Log.d(TAG, "right position: " + sliderRight);
              Log.d(TAG, "bottom position: " + sliderBottom);
              Log.d(TAG, "width: " + sliderWidth);
              Log.d(TAG, "height: " + sliderHeight);
            */

            startingPositionsEstablished = true;
        }
    }


    //sets the color of the slider object through the assistance of the key encoder.
    //from the sliderPaintColor method.
    private void setThePaint(){

        sliderPaint = new Paint();

        paintKey = getRandomNumber(1, 4);
        setSliderPaintColor(paintKey);

     //   Log.d(TAG, "The paint's color code is " + paintColorCode);

    }


    //convenience method that sets the paint color of the slider object based on the number key
    //passed in from the argument.
    //1 FOR RED
    //2 FOR GREEN
    //3 FOR BLUE
    //4 FOR PINK
    private void setSliderPaintColor(int paintColorCode){

        switch (paintColorCode){

            case 1:

                sliderPaint.setColor(getResources().getColor(R.color.red));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;


            case 2:

                sliderPaint.setColor(getResources().getColor(R.color.green));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;



            case 3:

                sliderPaint.setColor(getResources().getColor(R.color.blue));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;


            case 4:

                sliderPaint.setColor(getResources().getColor(R.color.pink));
                sliderPaint.setStyle(Paint.Style.FILL);
                break;

        }
    }



    //method used to obtain a random number between the min and max arguments.  Min being the lowest
    //number that can be obtained, and max being the highest number that can be obtained.  This method
    //is used for a number of contributing factors across the Slider class.
    private int getRandomNumber(int min, int max){

        Random random = new Random();

        int randomNumber = random.nextInt(max) + min;

        //   Log.d(TAG, "The Random number between " + min + " and " + max + " is " + randomNumber);

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

        sliderLeft += 3;
        sliderRight += 3;

    }

    protected void moveSliderToTheLeft(){

        sliderLeft -= 3;
        sliderRight -= 3;

    }

    protected void moveSliderDown(){

        sliderTop += 3;
        sliderBottom += 3;

    }

    protected void moveSliderUp(){

        sliderTop -= 3;
        sliderBottom -= 3;
    }


    //the following methods provide interaction with the operator object.
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //convenience method that obtains the current position of the
    public void setOperatorPositions(PlayerBars playerBars){

         operatorLeft =  playerBars.obtainOperatorPosition(context.getString(R.string.left) );
         operatorTop = playerBars.obtainOperatorPosition(context.getString(R.string.top) );
         operatorRight = playerBars.obtainOperatorPosition(context.getString(R.string.right) );
         operatorBottom = playerBars.obtainOperatorPosition(context.getString(R.string.bottom) );

      //  Log.d(TAG, whichCoordinate + " position: " + selectedPosition);

        /*
        Log.d(TAG, "LEFT: " + operatorLeft);
        Log.d(TAG, "RIGHT: " + operatorRight);
        Log.d(TAG, "TOP: " + operatorTop);
        Log.d(TAG, "BOTTOM: " + operatorBottom);
        */
    }


    //convenience method that sets the rules for collision detection between the slider and the operator.
    //if the operator position (left, top, right, or bottom) goes within the listed boudaries of the slider, then
    //a flag is returned signalling that a successful collision has occured.  The process goes through numerous steps
    //of flags to check for a correct geographic collision.
    private boolean isElligibleForCollision(int operatorPosition, int sliderTopOrLeft, int sliderBottomOrRight){

        boolean isElligible = false;

        if( (operatorPosition >= sliderTopOrLeft) && (operatorPosition <= sliderBottomOrRight) ){

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


        if(isElligibleForCollision(operatorLeft, sliderLeft, sliderRight) || isElligibleForCollision(operatorRight, sliderLeft, sliderRight) ) {

            isElligible = true;
        }

        return isElligible;

    }

    //if both the vertical and horizontal boundaries have reached a point of eligibility for collision
    //(the operator and slider have collided), then a flag is returned which signals that a collision has occurred.
    private void checkForCollision(){

        if(verticalCollideIsElligible() && horizontalCollideIsElligible() ){

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

     //   Log.d(TAG, "paint key is " + paintKey);
     //   Log.d(TAG, "operator color key is " + playerBars.getColorKey() );

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
            Log.d(TAG, "slider has disappeared left to right");
        }

        if(sliderTop > (canvas.getHeight() + 10) ) {

            isDissolved = true;
            Log.d(TAG, "slider has disappeared top to bottom");
        }

        if(sliderRight < -10){

            isDissolved = true;
            Log.d(TAG, "slider has disappeared right to left");

        }

        if(sliderBottom < -10){

            isDissolved = true;
            Log.d(TAG, "slider has disappeared bottom to top");

        }


    }



    private void logAllCoordinatesForDebugging(){

        Log.d(TAG, "the collision has occurred");

        Log.d(TAG, "operatorleft: " + operatorLeft);
        Log.d(TAG, "sliderLeft: " + sliderLeft);

        Log.d(TAG, "operatorTop: " + operatorTop);
        Log.d(TAG, "sliderTop: " + sliderTop);

        Log.d(TAG, "operatorRight: " + operatorRight);
        Log.d(TAG, "sliderRight: " + sliderRight);

        Log.d(TAG, "operatorBottom: " + operatorBottom);
        Log.d(TAG, "sliderBottom: " + sliderBottom);
    }

}
