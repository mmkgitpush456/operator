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

    private ArrayList<Slider> slidersContainer;
    private ArrayList<Integer> activeSliders;

    private android.os.Handler handler;
    private Runnable runnable;


    public Quadrant(Context context, AttributeSet attrs){

        this.context = context;
        this.attrs = attrs;

        quadrantKey = 1;
        maxNumSliders = 11;
        slidersContainer = new ArrayList<Slider>();
        activeSliders = new ArrayList<Integer>();
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
                handler.postDelayed(this, 5000);
            }
        };
        runnable.run();
    }



    protected void performSliderActivities(Canvas canvas, PlayerBars playerBars){

        for(int i = 0; i < activeSliders.size(); i++){

            int whichSlider = (activeSliders.get(i));

            if(!slidersContainer.get(whichSlider).isDissolved() ) {

                slidersContainer.get(whichSlider).moveTheSlider(canvas, playerBars);

            } else {

                /*
                for(int j = 0; j < slidersContainer.size(); j++){

                    if(activeSliders.get(j) == whichSlider){

                        activeSliders.remove(j);

                    }
                }
                */



                    Log.d(TAG, "resetting slider # " + activeSliders.get(i));
                    activeSliders.remove(i);
                    Log.d(TAG, "size of the active sliders is " + activeSliders.size() );




                slidersContainer.get(whichSlider).resetTheSlider();


            }
        }


        /*
        for(int i = 0; i < maxNumSliders; i++){

            if(!slidersContainer.get(i).isDissolved() ) {

                slidersContainer.get(i).moveTheSlider(canvas, playerBars);

            } else {

                slidersContainer.get(i).resetTheSlider();

            }
        }
        */
    }

    protected void activateRandomSlider(){

        Random random = new Random();
        int randomNumber = random.nextInt(9) + 1;

        slidersContainer.get(randomNumber).setIsDissolved(false);
        activeSliders.add(randomNumber);


       // Log.d(TAG, "added " + randomNumber + " to the activeSliders ");



    }




}
