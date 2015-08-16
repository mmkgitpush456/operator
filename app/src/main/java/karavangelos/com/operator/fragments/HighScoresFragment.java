package karavangelos.com.operator.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/14/15.
 */
public class HighScoresFragment extends Fragment{

    private static final String TAG = "HighScoresFragment";
    private static HighScoresFragment sHighScoresFragment;
    private View v;

    private TextView highScoresTitleTextView;
    private TextView scoreIndexTextView;
    private TextView scoreTextView;
    private TextView levelTextView;
    private TextView dateTextView;

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

        v = inflater.inflate(R.layout.high_scores_layout, null);

        highScoresTitleTextView = (TextView) v.findViewById(R.id.highScoresTitleTextView);
        scoreIndexTextView = (TextView) v.findViewById(R.id.scoreIndexTextView);
        scoreTextView = (TextView) v.findViewById(R.id.scoreTextView);
        levelTextView = (TextView) v.findViewById(R.id.levelTextView);
        dateTextView = (TextView) v.findViewById(R.id.dateTextView);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/hemi.ttf");
        highScoresTitleTextView.setTypeface(typeface);
        scoreIndexTextView.setTypeface(typeface);
        scoreTextView.setTypeface(typeface);
        levelTextView.setTypeface(typeface);
        dateTextView.setTypeface(typeface);

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
