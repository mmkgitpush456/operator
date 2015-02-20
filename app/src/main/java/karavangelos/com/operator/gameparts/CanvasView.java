package karavangelos.com.operator.gameparts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/14/15.
 */
public class CanvasView extends View {

    private static final String TAG = "CanvasView";


    public int width;
    public int height;
    Context context;


    //constructor.  Assigns member context variable from inherited parent
    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
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

        drawVerticalLines(canvas);
        drawHorizontal(canvas);


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
        int iterator = (int) (canvasWidth / 6);
        int verticalLineMarker = iterator;

        for(int i = 0; i < 6; i++){

            if(i < 5) {

                canvas.drawLine(verticalLineMarker, 0, verticalLineMarker, canvas.getHeight(), linePaint);
                verticalLineMarker += iterator;

            }
        }
    }


    private void drawHorizontal(Canvas canvas){

        Paint linePaint = getLinePaint(canvas);
        int canvasHeight = canvas.getHeight();
        int iterator = (int) (canvasHeight / 11);
        int horizontalLineMarker = iterator;

        for(int i = 0; i < 11; i++){

            if(i < 10) {

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

    ////////////////////////////////////////////////////////////////////////////////////////////////



}
