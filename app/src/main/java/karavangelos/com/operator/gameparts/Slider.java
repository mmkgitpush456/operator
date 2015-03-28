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
    //collide with the operator object.
public class Slider extends View{

    private static final String TAG = "Slider";

    private Rect theSlider;

    private int sliderLeft;                                                                         //slider left coordinate
    private int sliderTop;                                                                          //slider top coordinate
    private int sliderRight;                                                                        //slider right coordinate
    private int sliderBottom;                                                                       //slider bottom coordinate
    private int sliderWidth;
    private int sliderHeight;

    private int quadrantKey;                                                                        //integer key that decides where to manifest the slider rectangle from.  1 for left, 2 for top, 3 for right, 4 for bottom.
    private int lineKey;                                                                            //integer key that determines where to draw the new slider according to the grid lines
    private int paintKey;                                                                           //integer key that determines what color to paint the slider with.
    private int sliderSpeed;                                                                        //speed of the slider object.  Determined according to numerous factors.
    private Paint sliderPaint;                                                                      //paint that will color the slider rectangle.

    private boolean startingPositionsEstablished;                                                   //flag that tells whether or not the starting positions of the slider have been defined.
    private Context context;                                                                        //member variant of global context


    public Slider(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        theSlider = new Rect();
        startingPositionsEstablished = false;

        setthePaint();
        quadrantKey = getRandomNumber(1, 4);

       // setSliderCoordinates();

      //  Log.d(TAG, "the random number for the quadrant key is " + quadrantKey );


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
    public int getLineKey() {
        return lineKey;
    }
    public void setLineKey(int lineKey) {
        this.lineKey = lineKey;
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


    public void drawTheSlider(Canvas canvas){


        theSlider.set(sliderLeft, sliderTop, sliderRight, sliderBottom);
        canvas.drawRect(theSlider, sliderPaint);

    }

    protected void setSliderCoordinates(PlayerBars playerBars){

        if(!startingPositionsEstablished){

            sliderWidth = playerBars.getVertBarWidth();
            sliderHeight = playerBars.getHorzBarWidth();

            if(quadrantKey == 1){

                sliderRight = 0;
                sliderTop = 0;
                sliderBottom = (sliderTop + sliderHeight);
                sliderLeft = (sliderRight - sliderWidth);

            } else {

                sliderLeft = 0;
                sliderTop = 0;
                sliderRight = 100;
                sliderBottom = 100;

            }


              Log.d(TAG, "quadrant position: " + quadrantKey);
              Log.d(TAG, "left position: " + sliderLeft);
              Log.d(TAG, "top position: " + sliderTop);
              Log.d(TAG, "right position: " + sliderRight);
              Log.d(TAG, "bottom position: " + sliderBottom);
              Log.d(TAG, "width: " + sliderWidth);
              Log.d(TAG, "height: " + sliderHeight);


            startingPositionsEstablished = true;
        }
    }


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


}
