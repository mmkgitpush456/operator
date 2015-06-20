package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by karavangelos on 6/19/15.
 */
public class Quadrant {

    private static final String TAG = "Vector";

    private Context context;
    private AttributeSet attrs;

    private int quadrantKey;
    private int maxNumSliders;

    private ArrayList<Slider> slidersContainer;


    public Quadrant(Context context, AttributeSet attrs){

        this.context = context;
        this.attrs = attrs;

        quadrantKey = 1;
        maxNumSliders = 11;
        slidersContainer = new ArrayList<Slider>();

        setDummySlidersForVector();

    }


    private void setDummySlidersForVector(){

        for(int i = 0; i < maxNumSliders; i++){

            Slider slider = new Slider(context, attrs, quadrantKey, (i + 1));
            slidersContainer.add(i, slider);
        }
    }

    protected void performSliderActivities(Canvas canvas, PlayerBars playerBars){

        for(int i = 0; i < maxNumSliders; i++){

            if(!slidersContainer.get(i).isDissolved() ) {

                slidersContainer.get(i).setSliderCoordinates(playerBars, canvas);
                slidersContainer.get(i).drawTheSlider(canvas);
                slidersContainer.get(i).setOperatorPositions(playerBars);
                slidersContainer.get(i).checkIfColorMatchesOperatorColor(playerBars);
                slidersContainer.get(i).checkIfTheSliderHasPassedTheCanvas(canvas);

            } else {

                slidersContainer.get(i).setHasCollided(false);
                slidersContainer.get(i).setStartingPositionsEstablished(false);
                slidersContainer.get(i).setIsDissolved(false);
                //slidersContainer.get(i).setSliderCoordinates(playerBars, canvas);
            }
        }
    }





}
