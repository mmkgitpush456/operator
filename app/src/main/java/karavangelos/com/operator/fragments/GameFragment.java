package karavangelos.com.operator.fragments;

import android.app.ActionBar;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import karavangelos.com.operator.GameActivity;
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
    private android.support.v7.app.ActionBar actionBar;
    private View actionBarView;

    public GameFragment(){

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = (LayoutInflater) getActivity() .getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        actionBarView = inflator.inflate(R.layout.action_bar_game_fragment, null);
        actionBar.setCustomView(actionBarView);
        Toolbar parent =(Toolbar) actionBarView.getParent();
        parent.setContentInsetsAbsolute(0,0);






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

    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

        canvasView.freezeTheGame();
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
      //  canvasView.setContext(getActivity());
        TextView scoreTextView = (TextView) v.findViewById(R.id.scoreTextView);
        TextView livesTextView = (TextView) v.findViewById(R.id.livesTextView);
        Button powerUpButton = (Button) v.findViewById(R.id.powerButton);
        Button pauseButton = (Button) v.findViewById(R.id.pauseButton);
        Button gameButton = (Button) v.findViewById(R.id.gameButton);

        TextView timertextView = (TextView) actionBarView.findViewById(R.id.gameBarTimerTextView);
        TextView levelTextView = (TextView) actionBarView.findViewById(R.id.gameBarLevelTextView);

        pauseButton.setClickable(false);
        canvasView.setScoreTextView(scoreTextView);
        canvasView.setLivesTextView(livesTextView);
        canvasView.setPowerUpButton(powerUpButton);
        canvasView.setPauseButton(pauseButton);
        canvasView.setGameButton(gameButton);
        canvasView.setTimerTextView(timertextView);
        canvasView.setLevelTextView(levelTextView);




    }








}
