package karavangelos.com.operator.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import karavangelos.com.operator.GameActivity;
import karavangelos.com.operator.MainActivity;
import karavangelos.com.operator.R;
import karavangelos.com.operator.dialogs.OptionsDialog;
import karavangelos.com.operator.objects.DBHandler;

/**
 * Created by karavangelos on 2/12/15.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MainFragemnt";

    private View v;

    private TextView mainLayoutTextView;
    private Button newGameButton;
    private Button highScoresButton;
    private Button instructionsButton;
    private Button optionsButton;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.main_layout, container, false);

        mainLayoutTextView = (TextView) v.findViewById(R.id.mainLayoutTextView);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/hemi.ttf");
        mainLayoutTextView.setTypeface(typeface);

        return v;

    }//end onCreate view


    @Override
    public void onResume() {
        super.onResume();

        renderButtons(v);
        assignListeners();

        /*
        DBHandler db = new DBHandler(getActivity());
        db.insertRowIntoDB(223, 22);
        db.insertRowIntoDB(93, 7);
        db.insertRowIntoDB(34, 2);
        db.insertRowIntoDB(54, 5);
        db.insertRowIntoDB(12, 6);
        db.insertRowIntoDB(33, 8);
        db.insertRowIntoDB(99, 3);
        db.insertRowIntoDB(42, 5);
        db.insertRowIntoDB(67, 8);
        db.insertRowIntoDB(102,12);
        db.insertRowIntoDB(73, 5);
        db.insertRowIntoDB(32, 7);
        db.insertRowIntoDB(77, 3);
        db.insertRowIntoDB(145, 9);
        db.insertRowIntoDB(83, 1);
        */


        /*


         */

    }

    @Override
    public void onPause() {
        super.onPause();

        nullifyButtons();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.newGameButton:

               // Log.d(TAG, "new game button pressed");

                Intent intent = new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);

                DBHandler dbHandler = new DBHandler(getActivity());

                dbHandler.getStatsFromSelectedDate();

                break;


            case R.id.highScoresButton:

              //  Log.d(TAG, "high scores button pressed");
                launchNewFragment( HighScoresFragment.newInstance() );

                break;


            case R.id.instructionsButton:

               // Log.d(TAG, "instructions button pressed");
                launchNewFragment( InstructionsFragment.newInstance() );

                break;

            case R.id.optionsButton:

                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();

                OptionsDialog dialog = OptionsDialog.newInstance();
                dialog.show(fm, "OPTIONS_DIALOG");


                break;

        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void renderButtons(View v){

        newGameButton = (Button) v.findViewById(R.id.newGameButton);
        highScoresButton = (Button) v.findViewById(R.id.highScoresButton);
        instructionsButton = (Button) v.findViewById(R.id.instructionsButton);
        optionsButton = (Button) v.findViewById(R.id.optionsButton);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/hemi.ttf");
        newGameButton.setTypeface(typeface);
        highScoresButton.setTypeface(typeface);
        instructionsButton.setTypeface(typeface);
        optionsButton.setTypeface(typeface);



     //   Log.d(TAG, "loading buttons");

    }

    private void assignListeners(){

        newGameButton.setOnClickListener(this);
        highScoresButton.setOnClickListener(this);
        instructionsButton.setOnClickListener(this);
        optionsButton.setOnClickListener(this);

    }

    private void nullifyButtons(){

        newGameButton = null;
        highScoresButton = null;
        instructionsButton = null;

    }


    private void launchNewFragment(Fragment fragment) {

        android.support.v4.app.FragmentManager fm  = getActivity().getSupportFragmentManager();

        fm.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragment)
                .commit();

        MainActivity.stackCount++;

    }





}
