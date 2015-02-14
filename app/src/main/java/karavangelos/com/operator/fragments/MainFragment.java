package karavangelos.com.operator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import karavangelos.com.operator.MainActivity;
import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/12/15.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MainFragemnt";

    private View v;

    private Button newGameButton;
    private Button highScoresButton;
    private Button instructionsButton;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_main, container, false);
        return v;

    }//end onCreate view


    @Override
    public void onResume() {
        super.onResume();

        renderButtons(v);
        assignListeners();


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

                Log.d(TAG, "new game button pressed");

                break;


            case R.id.highScoresButton:

              //  Log.d(TAG, "high scores button pressed");
                launchNewFragment( HighScoresFragment.newInstance() );

                break;


            case R.id.instructionsButton:

               // Log.d(TAG, "instructions button pressed");
                launchNewFragment( InstructionsFragment.newInstance() );

                break;


        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void renderButtons(View v){

        newGameButton = (Button) v.findViewById(R.id.newGameButton);
        highScoresButton = (Button) v.findViewById(R.id.highScoresButton);
        instructionsButton = (Button) v.findViewById(R.id.instructionsButton);

    }

    private void assignListeners(){

        newGameButton.setOnClickListener(this);
        highScoresButton.setOnClickListener(this);
        instructionsButton.setOnClickListener(this);

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
