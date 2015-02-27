package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/14/15.
 */
public class CanvasView extends View {

    private static final String TAG = "CanvasView";
    private Context context;
    private Canvas canvas;

    private int verticalGridBreaks;                                                              //helps determine distance between vertical grid lines
    private int horizontalGridBreaks;                                                            //helps determine distance between horizontal grid lines


    private PlayerBars playerBars;


    /*
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
    */


    //constructor.  Assigns member context variable from inherited parent
    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        verticalGridBreaks = 7;
        horizontalGridBreaks = 11;

        playerBars = new PlayerBars(c, attrs);
        playerBars.setBarPaint();

       // setBarPaint();

        //starterBarsAreSet = false;

        //horizontalBar = new Rect();
        //verticalBar = new Rect();

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

        playerBars.setUserBarStartingCoordinates(canvas, horizontalGridBreaks, verticalGridBreaks);
        playerBars.drawTheBars(canvas);


        drawTheLines(canvas);

        invalidate();
    }


    //On touch events.  User operations all fall within this block of programming.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)  event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if(!playerBars.touchedTheExactCenter(x, y) ) {

                    playerBars.touchedTheVerticalBar(x);
                    playerBars.touchedTheHorizontalBar(y);

                }


                break;
            case MotionEvent.ACTION_MOVE:

                playerBars.moveTheVerticalBar(x);
                playerBars.moveTheHorizontalBar(y, horizontalGridBreaks);



                break;
            case MotionEvent.ACTION_UP:


                playerBars.setTouchedTheVerticalBar(false);
                playerBars.setTouchedTheHorizontalBar(false);

                playerBars.moveVerticalBarToRowActionUp();
                playerBars.moveHorizontalBarToRowActionUp();

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
        int iterator = (int) (canvasWidth / verticalGridBreaks);
        int verticalLineMarker = iterator;

        for(int i = 0; i < verticalGridBreaks; i++){

            if(i < (verticalGridBreaks - 1)) {

                canvas.drawLine(verticalLineMarker, 0, verticalLineMarker, canvas.getHeight(), linePaint);
                verticalLineMarker += iterator;
            }
        }
    }


    private void drawHorizontalLines(Canvas canvas){

        Paint linePaint = getLinePaint(canvas);
        int canvasHeight = canvas.getHeight();
        int iterator = (int) (canvasHeight / horizontalGridBreaks);
        int horizontalLineMarker = iterator;

        for(int i = 0; i < horizontalGridBreaks; i++){

            if(i < (horizontalGridBreaks - 1) ) {

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

}
