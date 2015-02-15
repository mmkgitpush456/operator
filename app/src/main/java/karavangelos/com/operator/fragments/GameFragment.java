package karavangelos.com.operator.fragments;

import android.app.ActionBar;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import karavangelos.com.operator.R;
import karavangelos.com.operator.gameparts.CanvasView;

/**
 * Created by karavangelos on 2/14/15.
 */
public class GameFragment extends Fragment{

    private static final String TAG = "GameFragment";
    private View v;

    private LinearLayout canvasLayout;

    private CanvasView canvasView;
    int canvasMarginWidth;
    int canvasMarginHeight;


    public GameFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.game_layout, null);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        defineViews(v);
        setCanvasMargins();

    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }





    ////////////////////////////////////////////////////////////////////////////////////////////////

    //define all the XML views within the layout
    private void defineViews(View v){

        canvasLayout = (LinearLayout) v.findViewById(R.id.canvasLayout);
        canvasView = (CanvasView) v.findViewById(R.id.canvasView);

    }


    //established a scaled margin for both the width and the height of the canvas to which the
    //game takes place.  The post method is utilized to obtain the dimensions of the canvas layout
    //at run time.  Used in the onResume override.
    private void setCanvasMargins(){

        canvasLayout.post(new Runnable() {
            @Override
            public void run() {



                int width = canvasLayout.getWidth();
                int height = canvasLayout.getHeight();

                int canvasMarginWidth = (int) (width * .01);
                int canvasMarginHeight = (int) (height * .01);


                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) canvasLayout.getLayoutParams();
                params.setMargins(canvasMarginWidth, canvasMarginHeight, canvasMarginWidth, canvasMarginHeight);
                canvasView.setLayoutParams(params);

            }
        });

    }






}
