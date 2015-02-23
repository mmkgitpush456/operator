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
    private int horzbarheight;
    private int horzBarDistanceTop;
    private int horzBarDistanceBottom;

    private int vertBarLeft;                                                                        //coordinates that maintain the vertical gray bar
    private int vertBarTop;
    private int vertBarRight;
    private int vertBarBottom;
    private int vertBarWidth;
    private int vertTouchDistanceLeft;
    private int vertTouchDistanceRight;



    private boolean starterBarsAreSet;                                                              //flag that determines if the player bars have been initialized or not.
    private boolean touchedTheVerticalBar;
    private boolean touchedTheHorizontalBar;



    public PlayerBars(Context c, AttributeSet attrs){
        super(c, attrs);
        context = c;

        starterBarsAreSet = false;
        touchedTheVerticalBar = false;


        horizontalBar = new Rect();
        verticalBar = new Rect();

    }



    ////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isTouchedTheVerticalBar() {
        return touchedTheVerticalBar;
    }

    public void setTouchedTheVerticalBar(boolean touchedTheVerticalBar) {
        this.touchedTheVerticalBar = touchedTheVerticalBar;
    }

    public boolean isTouchedTheHorizontalBar() {
        return touchedTheHorizontalBar;
    }

    public void setTouchedTheHorizontalBar(boolean touchedTheHorizontalBar) {
        this.touchedTheHorizontalBar = touchedTheHorizontalBar;
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
            vertBarWidth = getVertBarWidth();
            horzbarheight = getHorzBarHeight();

        //    Log.d(TAG, "The bars are being set once and only once");
        //    Log.d(TAG, "The bar width is " + vertBarWidth );
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

    public boolean touchedTheExactCenter(int xPos, int yPos){

        int left = vertBarLeft;
        int right =  vertBarRight;
        int top = horzBarTop;
        int bottom = horzBarBottom;

        boolean touchedTbeExactCenter = false;

        if( ( (xPos >= left) && (xPos <= right)  )

                &&

           ( (yPos >= top) && (yPos <= bottom) )

                ){

            touchedTbeExactCenter = true;

        }


        return touchedTbeExactCenter;

    }


    public void touchedTheVerticalBar (int xPos){


        int left = vertBarLeft;
        int right =  vertBarRight ;


        if( (xPos >= left) && (xPos <= right)  ){

            setTouchedTheVerticalBar(true);
            vertTouchDistanceLeft = (xPos - vertBarLeft);
            vertTouchDistanceRight = (vertBarRight - xPos);

        }
    }


    public void moveTheVerticalBar(int xPos){



        if(touchedTheVerticalBar) {

            vertBarLeft = (xPos - vertTouchDistanceLeft);
            vertBarRight = (xPos + vertTouchDistanceRight);

        }
    }


    public void touchedTheHorizontalBar(int yPos){

        int top = horzBarTop;
        int bottom = horzBarBottom;


        if ( (yPos >= top) && (yPos <= bottom)   ) {

            setTouchedTheHorizontalBar(true);
            horzBarDistanceTop = (yPos - horzBarTop);
            horzBarDistanceBottom = (horzBarBottom - yPos);
        }
    }

    public void moveTheHorizontalBar(int yPos){


        if(touchedTheHorizontalBar){

            horzBarTop = (yPos - horzBarDistanceTop);
            horzBarBottom = (yPos + horzBarDistanceBottom);

        }
    }






    private int getVertBarWidth(){

        int verticalBarWidth = (vertBarRight - vertBarLeft);
        return verticalBarWidth;

    }

    private int getHorzBarHeight(){

        int horzBarHeight = (vertBarBottom - vertBarTop);
        return horzBarHeight;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
}
