package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/21/15.
 */
public class PlayerBars extends View {

    private static final String TAG = "PlayerBars";
    private Context context;

    private Paint barPaint;

    private Rect horizontalBar;
    private Rect verticalBar;

    private int horzBarLeft;                                                                        //coordinates that maintain the horizontal gray bar
    private int horzBarTop;
    private int horzBarRight;
    private int horzBarBottom;

    private int vertBarLeft;                                                                        //coordinates that maintain the vertical gray bar
    private int vertBarTop;
    private int vertBarRight;
    private int vertBarBottom;

    private boolean starterBarsAreSet;                                                              //flag that determines if the player bars have been initialized or not.


    public PlayerBars(Context c, AttributeSet attrs){
        super(c, attrs);
        context = c;

        starterBarsAreSet = false;


        horizontalBar = new Rect();
        verticalBar = new Rect();

    }



    ////////////////////////////////////////////////////////////////////////////////////////////////


    //player bar class methods
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setStartHorizontalBar(Canvas canvas, int gridBreaks){

            int startingIncrementer = getStartingIncrementor(gridBreaks);
            int canvasHeight = canvas.getHeight();
            int iterator = (int) canvasHeight / gridBreaks;
            int starterPosition = getStarterPosition(startingIncrementer, iterator);
            int right = canvas.getWidth();
            int bottom = (starterPosition + iterator) ;

            horzBarLeft = 0;
            horzBarTop = starterPosition;
            horzBarRight = right;
            horzBarBottom = bottom;

    }


    private void setStartingVerticalBar(Canvas canvas, int gridBreaks){

            int startingIncrementer = getStartingIncrementor(gridBreaks);
            int canvasWidth = canvas.getWidth();
            int iterator = (int) canvasWidth / gridBreaks;
            int starterPosition = getStarterPosition(startingIncrementer, iterator);
            int bottom = canvas.getHeight();
            int right = (starterPosition + iterator);

            vertBarLeft = starterPosition;
            vertBarTop = 0;
            vertBarRight = right;
            vertBarBottom = bottom;

    }

    private int getStartingIncrementor(int gridBreaks){

        int startingIncrementer = 0;


        if( (gridBreaks % 2) == 0 ){

            startingIncrementer = (gridBreaks / 2);

        } else {

            startingIncrementer = ( (gridBreaks - 1) / 2 );

        }
        return startingIncrementer;

    }


    private int getStarterPosition(int startingIncrementer, int iterator){

        int starterPosition = 0;

        for(int i = 0; i < startingIncrementer; i++) {

            starterPosition += iterator;

        }

        return starterPosition;

    }

    protected void setUserBarStartingCoordinates(Canvas canvas, int horizontalGridBreaks, int verticalGridBreaks){


        if(!starterBarsAreSet) {

            setStartingVerticalBar(canvas, verticalGridBreaks);
            setStartHorizontalBar(canvas, horizontalGridBreaks);

            starterBarsAreSet = true;

            Log.d(TAG, "The bars are being set once and only once");
        }



    }

    protected void drawTheBars(Canvas canvas){

        horizontalBar.set(horzBarLeft, horzBarTop, horzBarRight, horzBarBottom );
        canvas.drawRect(horizontalBar, barPaint);

        verticalBar.set(vertBarLeft, vertBarTop, vertBarRight , vertBarBottom);
        canvas.drawRect(verticalBar, barPaint);

    }

    protected void setBarPaint(){

        barPaint = new Paint();
        barPaint.setColor(getResources().getColor(R.color.light_gray));
        barPaint.setStyle(Paint.Style.FILL);

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////////////////////////////




}
