package karavangelos.com.operator.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;

import karavangelos.com.operator.objects.HighScore;
import karavangelos.com.operator.objects.HighScoresAdapter;
import karavangelos.com.operator.objects.HighScoresModel;

/**
 * Created by karavangelos on 8/3/15.
 */
public class HighScoresListFragment extends ListFragment{

    private static final String TAG = "HighScoresListFragment";
    private HighScoresAdapter adapter;
    private ArrayList<HighScore> theScores;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        theScores = HighScoresModel.newInstance().getTheScores();
        adapter = new HighScoresAdapter(theScores, getActivity());
        setListAdapter(adapter);


    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


}



