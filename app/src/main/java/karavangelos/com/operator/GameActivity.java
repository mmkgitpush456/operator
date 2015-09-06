package karavangelos.com.operator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import karavangelos.com.operator.fragments.GameFragment;

/**
 * Created by karavangelos on 2/14/15.
 */

//game activity container for the game screen.  Game Fragment falls within this block
public class GameActivity extends ActionBarActivity{

    private static final String TAG = "GameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        establishTheGameFragment();


    }

    @Override
    public void onBackPressed() {

      //  super.onBackPressed();

        if(!GameFragment.newInstance().gameIsCurrentlyPausedForActivityBackPress()){

            GameFragment.newInstance().pauseTheGameFromActivityBackPress();

        } else {

            finish();
        }

        if(GameFragment.newInstance().isBetweenLevels()) {

            finish();

        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    //wires the main fragment to start off the view of the Main Activity.
    //used within the onCreate section of this activity.
    private void establishTheGameFragment(){

        android.support.v4.app.FragmentManager fm  = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment == null) {

            fragment = GameFragment.newInstance();
            fm.beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    private Fragment getCurrentFragment(){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentByTag(fragmentTag);
        return currentFragment;
    }










}
