package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by karavangelos on 3/26/15.
 */

//the slider class controls all the boxes that move across, up, and down the canvas and which may
    //collide with the operator object.
public class Slider extends View{

    private Rect theSlider;

    private int sliderLeft;                                                                         //slider left coordinate
    private int sliderTop;                                                                          //slider top coordinate
    private int sliderRight;                                                                        //slider right coordinate
    private int sliderBottom;                                                                       //slider bottom coordinate
    private int quadrantKey;                                                                        //integer key that decides where to manifest the slider rectangle from.  1 for left, 2 for top, 3 for right, 4 for bottom.
    private int lineKey;                                                                            //integer key that determines where to draw the new slider according to the grid lines
    private int paintKey;                                                                           //integer key that determines what color to paint the slider with.
    private int sliderSpeed;                                                                        //speed of the slider object.  Determined according to numerous factors.

    private Paint sliderPaint;                                                                      //paint that will color the slider rectangle.

    private Context context;                                                                        //member variant of global context

    public Slider(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

    }




    //method used to obtain a random number between the min and max arguments.  Min being the lowest
    //number that can be obtained, and max being the highest number that can be obtained.  This method
    //is used for a number of contributing factors across the Slider class.
    private void getRandomNumber(int min, int max){

        Random random = new Random();

        int randomNumber = random.nextInt(max) + min;

        //   Log.d(TAG, "The Random number between " + min + " and " + max + " is " + randomNumber);

    }


    private void moveSliderToTheRight(){

        sliderLeft ++;
        sliderRight ++;

    }

    private void moveSliderToTheLeft(){

        sliderLeft--;
        sliderRight--;

    }

    private void moveSliderDown(){

        sliderTop++;
        sliderBottom++;

    }

    private void moveSliderUp(){

        sliderTop--;
        sliderBottom--;
    }


}
