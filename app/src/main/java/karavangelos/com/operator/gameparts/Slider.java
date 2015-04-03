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

    ////////////////////////////////////////////////////////////////////////////////////////////////


    //assigns where the slider will start on the canvas, what color it will be,
    //and which direction on the grid it will be traveling.  Used on the constructor.
    private void setConstructorProtocol(){

        theSlider = new Rect();
        startingPositionsEstablished = false;
        setthePaint();
        quadrantKey = getRandomNumber(1, 4);
        setVectorAccordingToQuadrant();

    }

    //public method that updates and draws the slider object on the canvas.
    public void drawTheSlider(Canvas canvas){


        theSlider.set(sliderLeft, sliderTop, sliderRight, sliderBottom);
        canvas.drawRect(theSlider, sliderPaint);
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


    //sets the color of the slider object.
    private void setthePaint(){

        sliderPaint = new Paint();
        sliderPaint.setColor(getResources().getColor(R.color.red));
        sliderPaint.setStyle(Paint.Style.FILL);

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

        sliderLeft ++;
        sliderRight ++;

    }

    protected void moveSliderToTheLeft(){

        sliderLeft--;
        sliderRight--;

    }

    protected void moveSliderDown(){

        sliderTop++;
        sliderBottom++;

    }

    protected void moveSliderUp(){

        sliderTop--;
        sliderBottom--;
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


}
