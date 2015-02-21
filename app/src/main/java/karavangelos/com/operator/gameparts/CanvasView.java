package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/14/15.
 */
public class CanvasView extends View {

    private static final String TAG = "CanvasView";
    Context context;

    private int numVerticalIterations;                                                              //helps determine distance between vertical grid lines
    private int numHorizontalIterations;                                                            //helps determine distance between horizontal grid lines
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

    private boolean starterBarsAreSet;


    //constructor.  Assigns member context variable from inherited parent
    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        numVerticalIterations = 7;
        numHorizontalIterations = 11;

        setBarPaint();

        starterBarsAreSet = false;

        horizontalBar = new Rect();
        verticalBar = new Rect();

    }


    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    // override onDraw.  Where ALL the magic happens
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(context.getResources().getColor(R.color.white));


        setUserBarStartingCoordinates(canvas);
        drawTheBars(canvas);
        drawTheLines(canvas);

        invalidate();
    }


    //On touch events.  User operations all fall within this block of programming.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    //end override methods.
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //getters and setters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////


    //drawing specific methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //The following 2 methods draw the vertical and horizontal lines on the canvas by measuring the canvas size and making 5 drawing
    //iterations across the canvas.  Distance of drawing the lines is determined by grabbing the canvas
    //size and dividing it by 6.  Set up of the method allows for it to scale correctly regardless of
    //the size of the device using the application.
    private void drawVerticalLines(Canvas canvas){

       Paint linePaint = getLinePaint(canvas);
        int canvasWidth = canvas.getWidth();
        int iterator = (int) (canvasWidth / numVerticalIterations);
        int verticalLineMarker = iterator;

        for(int i = 0; i < numVerticalIterations; i++){

            if(i < (numVerticalIterations - 1)) {

                canvas.drawLine(verticalLineMarker, 0, verticalLineMarker, canvas.getHeight(), linePaint);
                verticalLineMarker += iterator;
            }
        }
    }


    private void drawHorizontalLines(Canvas canvas){

        Paint linePaint = getLinePaint(canvas);
        int canvasHeight = canvas.getHeight();
        int iterator = (int) (canvasHeight / numHorizontalIterations);
        int horizontalLineMarker = iterator;

        for(int i = 0; i < numHorizontalIterations; i++){

            if(i < (numHorizontalIterations - 1) ) {

                canvas.drawLine(0, horizontalLineMarker, canvas.getWidth(), horizontalLineMarker, linePaint);
                horizontalLineMarker += iterator;
            }
        }
    }

    //determines the width of the grid lines based
    //on the width of the canvas on the screen.
    //once again, this is a scaled method.
    private int getGridStrokeWidth(Canvas canvas){

        int width = canvas.getWidth();
        int strokewidth = (int) (width * .01);

        return strokewidth;
    }


    //sets up a paint object to help paint the grid
    //lines on the canvas.
    private Paint getLinePaint(Canvas canvas){

        Paint linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.black));
        linePaint.setStrokeWidth(getGridStrokeWidth(canvas));

        return linePaint;
    }

    private void drawTheLines(Canvas canvas){

        drawVerticalLines(canvas);
        drawHorizontalLines(canvas);

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //player bar class methods
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setStartHorizontalBar(Canvas canvas){

        final String whichBar = "HORIZONTAL";

        if(!starterBarsAreSet){

            int startingIncrementer = getStartingIncrementor(numHorizontalIterations);
            int canvasHeight = canvas.getHeight();
            int iterator = (int) canvasHeight / numHorizontalIterations;
            int starterPosition = getStarterPosition(startingIncrementer, iterator);
            int right = canvas.getWidth();
            int bottom = (starterPosition + iterator) ;

            horzBarLeft = 0;
            horzBarTop = starterPosition;
            horzBarRight = right;
            horzBarBottom = bottom;
        }

    }


    private void setStartingVerticalBar(Canvas canvas){


        if(!starterBarsAreSet) {

            int startingIncrementer = getStartingIncrementor(numVerticalIterations);
            int canvasWidth = canvas.getWidth();
            int iterator = (int) canvasWidth / numVerticalIterations;
            int starterPosition = getStarterPosition(startingIncrementer, iterator);
            int bottom = canvas.getHeight();
            int right = (starterPosition + iterator);

            vertBarLeft = starterPosition;
            vertBarTop = 0;
            vertBarRight = right;
            vertBarBottom = bottom;

        }

    }

    private int getStartingIncrementor(int numVerticalIterations){

        int startingIncrementer = 0;


        if( (numVerticalIterations % 2) == 0 ){

            startingIncrementer = (numVerticalIterations / 2);

        } else {

            startingIncrementer = ( (numVerticalIterations - 1) / 2 );

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

    private void setUserBarStartingCoordinates(Canvas canvas){


        setStartHorizontalBar(canvas);
        setStartingVerticalBar(canvas);

        starterBarsAreSet = true;

    }

    private void drawTheBars(Canvas canvas){

        horizontalBar.set(horzBarLeft, horzBarTop, horzBarRight, horzBarBottom );
        canvas.drawRect(horizontalBar, barPaint);

        verticalBar.set(vertBarLeft, vertBarTop, vertBarRight , vertBarBottom);
        canvas.drawRect(verticalBar, barPaint);

    }

    private void setBarPaint(){

        barPaint = new Paint();
        barPaint.setColor(getResources().getColor(R.color.light_gray));
        barPaint.setStyle(Paint.Style.FILL);

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////



}
