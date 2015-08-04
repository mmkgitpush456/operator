package karavangelos.com.operator.objects;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 8/3/15.
 */
public class HighScoresAdapter extends ArrayAdapter {


    private static final String TAG = "HighScoresAdapter";
    private Activity activity;

    public HighScoresAdapter(ArrayList<HighScore> highScores, Activity a) {
        super(a, 0, highScores);

        activity = a;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       if(convertView == null){

           LayoutInflater inflater = activity.getLayoutInflater();

           convertView = inflater.inflate(R.layout.high_scores_list_item, null);

       }

        return convertView;
    }
}
