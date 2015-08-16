package karavangelos.com.operator.objects;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 8/3/15.
 */
public class HighScoresAdapter extends ArrayAdapter {


    private static final String TAG = "HighScoresAdapter";
    private Activity activity;
    private ArrayList<HighScore> highScores;

    public HighScoresAdapter(ArrayList<HighScore> highScores, Activity a) {
        super(a, 0, highScores);

        activity = a;
        this.highScores = highScores;
    }

    static class ViewHolder{

        LinearLayout scoresListItemContainerLayout;
        TextView scoreTextView;
        TextView levelTextView;
        TextView dateTextView;
        TextView scoreIndexTextView;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

       if(convertView == null){

           LayoutInflater inflater = activity.getLayoutInflater();
           convertView = inflater.inflate(R.layout.high_scores_list_item, null);

           viewHolder = new ViewHolder();

           viewHolder.scoresListItemContainerLayout = (LinearLayout) convertView.findViewById(R.id.scoresListItemContainerLayout);
           viewHolder.scoreTextView = (TextView) convertView.findViewById(R.id.scoreTextView);
           viewHolder.levelTextView = (TextView) convertView.findViewById(R.id.levelTextView);
           viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
           viewHolder.scoreIndexTextView = (TextView) convertView.findViewById(R.id.scoreIndexTextView);
           Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/hemi.ttf");

           viewHolder.scoreTextView.setTypeface(typeface);
           viewHolder.levelTextView.setTypeface(typeface);
           viewHolder.dateTextView.setTypeface(typeface);
           viewHolder.scoreIndexTextView.setTypeface(typeface);


           convertView.setTag(viewHolder);
           convertView.setTag(R.id.scoresListItemContainerLayout, viewHolder.scoresListItemContainerLayout);
           convertView.setTag(R.id.scoreTextView, viewHolder.scoreTextView);
           convertView.setTag(R.id.levelTextView, viewHolder.levelTextView);
           convertView.setTag(R.id.dateTextView, viewHolder.dateTextView);
           convertView.setTag(R.id.scoreIndexTextView, viewHolder.scoreIndexTextView);


       } else {

           viewHolder = (ViewHolder) convertView.getTag();
           //viewHolder.scoreTextView.setText(highScores.get(position).getScore());
          // viewHolder.completedCheckBox.setOnCheckedChangeListener(null);
          // viewHolder.completedCheckBox.setChecked(questionsOrFollowUps.get(position).isCompleted());

       }

        viewHolder.scoresListItemContainerLayout.setBackgroundColor(highScores.get(position).getColor());
        viewHolder.scoreTextView.setText(highScores.get(position).getScore());
        viewHolder.scoreIndexTextView.setText(((position + 1) + ":" ));
        viewHolder.levelTextView.setText(highScores.get(position).getLevel());
        viewHolder.dateTextView.setText(highScores.get(position).getDateOfScore());

        return convertView;
    }
}
