package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

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

    private android.os.Handler handler;                                                             //handler and runnable are used to queue sliders on interval instead of having them move all at once
    private Runnable runnable;

    private boolean mismatchedHit;                                                                  //flag that is flipped to true if the operator has a different color than the slider and the 2 collide
    private int handlerDelayer;                                                                     //integer flag that prevents the slider from launching at the very beginning of the game or level OR after a player resumes a paused game.
    private boolean paused;                                                                         //flag that detects whether or not the game is paused

    private Player player;                                                                          //player singleton instance.

    //Constructor.  Assigns context and Attribute Set.
    //Assigns the Quadrant key to assist with slider movement.
    //Sets the maximum number of sliders for the quadrant.
    //Sets the sliderQueueKey so that the first slider will be called at the start of the game sequence.

    public Quadrant(Context context, AttributeSet attrs, int quadrantKey){

        this.context = context;
        this.attrs = attrs;

        this.quadrantKey = quadrantKey;
        maxNumSliders = 7;
        sliderQueueKey = 0;

        slidersContainer = new ArrayList<Slider>();
        generateSlidersForQuadrant();

        handler = new android.os.Handler();
        mismatchedHit = false;
        handlerDelayer = 0;
        paused = false;

        player = Player.newInstance();

    }


    //getters and setters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public int getMaxNumSliders() {
        return maxNumSliders;
    }

    public void setMaxNumSliders(int maxNumSliders) {
        this.maxNumSliders = maxNumSliders;
    }

    public int getQuadrantKey() {
        return quadrantKey;
    }

    public void setQuadrantKey(int quadrantKey) {
        this.quadrantKey = quadrantKey;
    }

    public int getSliderQueueKey() {
        return sliderQueueKey;
    }

    public void setSliderQueueKey(int sliderQueueKey) {
        this.sliderQueueKey = sliderQueueKey;
    }

    public ArrayList<Slider> getSlidersContainer() {
        return slidersContainer;
    }

    public void setSlidersContainer(ArrayList<Slider> slidersContainer) {
        this.slidersContainer = slidersContainer;
    }

    public boolean isMismatchedHit() {
        return mismatchedHit;
    }

    public void setMismatchedHit(boolean mismatchedHit) {
        this.mismatchedHit = mismatchedHit;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


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

               // Log.d(TAG, "running slider push process");
                if(!isPaused()) {

                    handler.postDelayed(this, getHandlerDelay() );
                    if(handlerDelayer > 0){

                        sliderQueueKey++;
                        activateTheNextSlider();


                        if(sliderQueueKey == maxNumSliders){

                            sliderQueueKey = 0;
                        }

                    } else {

                        handlerDelayer++;
                    }



                }
            }
        };
        runnable.run();
    }

    //returns a random integer based on the getRandomNumber method
    //which tells the runnable how long to wait before releasing
    //a slider.  Used within the runProcessForCallingSliders above.
    private int getHandlerDelay(){

        int handlerDelay = (getRandomNumber() * 1000);

        //Log.d(TAG, "handlerDelay: " + handlerDelay);
        return handlerDelay;
    }

    //if the local paused flag is true, then the handler is removed
    //from making calls to the runnable and the handler delayer, which
    //prevents sliders from launching from the start of the runnable, is
    //put back to 0.  Otherwise, the runProcessForCallingSliders method is
    //called again and the game resumes.
    public void pauseOrResumeTheSliders(){

        if(isPaused()){

            handler.removeCallbacks(runnable);
           // Log.d(TAG, "Pausing all sliders");
            handlerDelayer = 0;

        } else {

            runProcessForCallingSliders();

        }

    }


    //obtains a random integer based on the current values obtained from the
    //player's minimum and maximum timeout quadrant variables.
    protected int getRandomNumber(){

        Random random = new Random();

        int randomNumber = random.nextInt(player.getMaximumQuadrantTimeOut()) + player.getMinimumQuadrantTimeOut();
        return randomNumber;
    }


    //This is the process that moves the sliders within the container across the canvas.
    //When a slider is queued, it moves across the canvas as long as its dissolved flag
    //doesn't flip.  If the slider safely makes it across the canvas or gets hit by
    //a matching color operator, it dissolves and all of its initial features are reset.
    protected void performSliderActivities(Canvas canvas, PlayerBars playerBars){

        for(int i = 0; i < slidersContainer.size(); i++){

            Slider theSlider = slidersContainer.get(i);

            if(!theSlider.isDissolved() ) {

                if(isPaused()) {

                    theSlider.setPausedSlider();

                } else {

                    theSlider.moveTheSlider(canvas, playerBars);
                }

            } else {

                resetHorizontalOrVerticalSlider( theSlider );

            }

            if(theSlider.isMismatch() ) {

                mismatchedHit = true;

            }
        }
    }


    //should the operator hit a slider with a mismatched color,
    //all the sliders within the quadrant are reset to their original values
    //prior to launch and the member variables of the quadrant are returned to their
    //original values as well.  Used within the rebootTheSlidersAndQuadrants
    //method on the canvas view.
    protected void resetAllOnMismatchHit(){

        for(int i = 0; i < slidersContainer.size(); i++){

            Slider theSlider = slidersContainer.get(i);

            resetHorizontalOrVerticalSlider(theSlider);


        }
        mismatchedHit = false;
        sliderQueueKey = 0;
        handler.removeCallbacks(runnable);
        handlerDelayer = 0;

       // Log.d(TAG, "mismatch = " + mismatchedHit);
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


    //resets a slider if it completes its path across the board or if the
    //operator hits it.  Used within the resetAllOnMismatchHit and
    //performSliderActivities methods within this class.
    private void resetHorizontalOrVerticalSlider(Slider slider){

        if(quadrantKey == 1 || quadrantKey == 3){

            slider.resetTheSlider(11);
        }

        if(quadrantKey == 2 || quadrantKey == 4){

            slider.resetTheSlider(7);

        }
    }




}
