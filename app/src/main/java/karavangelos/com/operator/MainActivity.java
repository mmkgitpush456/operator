package karavangelos.com.operator;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import karavangelos.com.operator.fragments.MainFragment;
import karavangelos.com.operator.objects.DBHandler;
import karavangelos.com.operator.objects.Player;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";                                               //logging tag
    public static int stackCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        establishTheMainFragment();
        getSupportActionBar().hide();

    }


    @Override
    protected void onResume() {
        super.onResume();

     //   Log.d(TAG,"stack count: " + stackCount + " from ONRESUME");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //keeps track of the number of fragments on the stack.
    //if the stack goes below 0 (user pushes back button on first main screen),
    //the app is completely killed.
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        stackCount--;

//        Log.d(TAG, "stackCount is " + stackCount);

        if(stackCount < 0) {

            finish();

        }


    }



    //end of override methods
    ////////////////////////////////////////////////////////////////////////////////////////////////


    //wires the main fragment to start off the view of the Main Activity.
    //used within the onCreate section of this activity.
    private void establishTheMainFragment(){

        android.support.v4.app.FragmentManager fm  = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment == null) {

            fragment = new MainFragment();
            fm.beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    //method used to launch a new fragment within mainActivity and adds current fragment on the activity
    //in case the user wants to view it again by pushing the back button.
    private void launchNewFragment(Fragment fragment) {

        android.support.v4.app.FragmentManager fm  = getSupportFragmentManager();

        fm.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragment)
                .commit();

        stackCount++;

    }

}
