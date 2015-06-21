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
public class Quadrant {

    private static final String TAG = "Quadrant";

    private Context context;
    private AttributeSet attrs;

    private int quadrantKey;
    private int maxNumSliders;
    private int sliderQueueKey;

    private ArrayList<Slider> slidersContainer;
  //  private ArrayList<Integer> activeSliders;

    private android.os.Handler handler;
    private Runnable runnable;


    public Quadrant(Context context, AttributeSet attrs){

        this.context = context;
        this.attrs = attrs;

        quadrantKey = 1;
        maxNumSliders = 11;
        sliderQueueKey = 0;

        slidersContainer = new ArrayList<Slider>();
     //   activeSliders = new ArrayList<Integer>();
        setDummySlidersForQuadrant();

        handler = new android.os.Handler();

    }


    private void setDummySlidersForQuadrant(){

        for(int i = 0; i < maxNumSliders; i++){

            Slider slider = new Slider(context, attrs, quadrantKey, (i + 1));
            slidersContainer.add(i, slider);
        }
    }


    protected void runProcessForCallingSliders(){

        runnable = new Runnable() {
            @Override
            public void run() {
                
                activateRandomSlider();
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



    protected void performSliderActivities(Canvas canvas, PlayerBars playerBars){

        for(int i = 0; i < slidersContainer.size(); i++){

            if(!slidersContainer.get(i).isDissolved() ) {

                slidersContainer.get(i).moveTheSlider(canvas, playerBars);

            } else {

                slidersContainer.get(i).resetTheSlider();

            }
        }
    }

    protected void activateRandomSlider(){

        slidersContainer.get(sliderQueueKey).setIsDissolved(false);
        Log.d(TAG, "made slider # " + sliderQueueKey + " active ");

    }


    protected void stopTheHandlerAndRunnable(){

        handler.removeCallbacks(runnable);

    }


}
