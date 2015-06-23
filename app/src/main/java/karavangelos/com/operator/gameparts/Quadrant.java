package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by karavangelos on 6/19/15.
 */

    //the quadrant class is a container and monitor of sliders that move from 1 side of the canvas.
    //This class maintains multiple sliders heading from the direction, checks for their interaction
    //with the operator object, and re-boots them when they exit the screen or when they are elligible
    //to be dissolved by the operator.
public class Quadrant {

    private static final String TAG = "Quadrant";

    private Context context;                                                                        //inherited context from the Canvas View to supply the sliders
    private AttributeSet attrs;                                                                     //inherited AttributeSet from the Canvas View to supply the sliders

    private int quadrantKey;                                                                        //key that helps tell the sliders which section of the canvas they will be moving from
    private int maxNumSliders;                                                                      //maximum number of sliders that are utilized within the quadrant
    private int sliderQueueKey;                                                                     //key that tells which slider's turn it is to move across the canvas.

    private ArrayList<Slider> slidersContainer;                                                     //Array container that holds all of the sliders.
  //  private ArrayList<Integer> activeSliders;

    private android.os.Handler handler;                                                             //handler and runnable are used to queue sliders on interval instead of having them move all at once
    private Runnable runnable;


    //Constructor.  Assigns context and Attribute Set.
    //Assigns the Quadrant key to assist with slider movement.
    //Sets the maximum number of sliders for the quadrant.
    //Sets the sliderQueueKey so that the first slider will be called at the start of the game sequence.

    public Quadrant(Context context, AttributeSet attrs, int quadrantKey){

        this.context = context;
        this.attrs = attrs;

        this.quadrantKey = quadrantKey;
        maxNumSliders = 11;
        sliderQueueKey = 0;

        slidersContainer = new ArrayList<Slider>();
     //   activeSliders = new ArrayList<Integer>();
        generateSlidersForQuadrant();

        handler = new android.os.Handler();

    }


    //creates a set of Sliders and puts them within the slidersContainer.
    //this container then moves and resets its child sliders depending on their
    //interactions with both the canvas and the Operator.
    private void generateSlidersForQuadrant(){

        for(int i = 0; i < maxNumSliders; i++){

            Slider slider = new Slider(context, attrs, quadrantKey);
            slidersContainer.add(i, slider);
        }
    }


    //Process which uses a Handler and runnable to the next slider within the
    //slidersContainer to move across the canvas.  When the value of the sliderQueKey
    //hits the same value as the maximum number of sliders, its value is set back to 0
    //to ensure that the first slider in the container will be called and run again
    //instead of constantly creating and destroying sliders.
    protected void runProcessForCallingSliders(){

        runnable = new Runnable() {
            @Override
            public void run() {
                
                activateTheNextSlider();
                sliderQueueKey++;

                if(sliderQueueKey == maxNumSliders){

                    sliderQueueKey = 0;
                    Log.d(TAG, "reset the slider queue key to 0");

                }

                handler.postDelayed(this, 5000);
            }
        };
        runnable.run();
    }



    //This is the process that moves the sliders within the container across the canvas.
    //When a slider is queued, it moves across the canvas as long as its dissolved flag
    //doesn't flip.  If the slider safely makes it across the canvas or gets hit by
    //a matching color operator, it dissolves and all of its initial features are reset.
    protected void performSliderActivities(Canvas canvas, PlayerBars playerBars){

        for(int i = 0; i < slidersContainer.size(); i++){

            if(!slidersContainer.get(i).isDissolved() ) {

                slidersContainer.get(i).moveTheSlider(canvas, playerBars);

            } else {

               // slidersContainer.get(i).resetTheSlider();
                resetHorizontalOrVerticalSlider(slidersContainer.get(i) );

            }
        }
    }

    //Queues the next slider in line to move across the canvas by flipping
    //its isDissolvedFlag to false.  Used on the runProcessForCallingSliders
    //method.
    protected void activateTheNextSlider(){

        slidersContainer.get(sliderQueueKey).setIsDissolved(false);
      //  Log.d(TAG, "made slider # " + sliderQueueKey + " active ");

    }


    //Called in the event that the player decides to pause or stop the game.
    //the runnable will not stop unless there is a direct call to remove
    //handler callbacks.
    protected void stopTheHandlerAndRunnable(){

        handler.removeCallbacks(runnable);

    }


    private void resetHorizontalOrVerticalSlider(Slider slider){

        if(quadrantKey == 1 || quadrantKey == 3){

            slider.resetTheSlider(11);

        }

        if(quadrantKey == 2 || quadrantKey == 4){

            slider.resetTheSlider(7);

        }

    }


}