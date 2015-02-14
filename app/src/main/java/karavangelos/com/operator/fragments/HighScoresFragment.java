package karavangelos.com.operator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/14/15.
 */
public class HighScoresFragment extends Fragment{

    private static final String TAG = "HighScoresFragment";
    private static HighScoresFragment sHighScoresFragment;
    private View v;


    public HighScoresFragment(){

    }


    public static HighScoresFragment newInstance(){

        if(sHighScoresFragment == null){

            sHighScoresFragment = new HighScoresFragment();

        }

        return sHighScoresFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_high_scores, null);

        return v;
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
