package karavangelos.com.operator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/14/15.
 */
public class GameFragment extends Fragment{

    private static final String TAG = "GameFragment";

    private View v;

    public GameFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.game_layout, null);

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

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
