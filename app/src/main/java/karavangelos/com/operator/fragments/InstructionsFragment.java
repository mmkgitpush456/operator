package karavangelos.com.operator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/14/15.
 */
public class InstructionsFragment extends Fragment{

    private static final String TAG = "InstructionsFragment";
    private static InstructionsFragment sIntructionsFragment;
    private View v;


    public InstructionsFragment(){

    }

    public static InstructionsFragment newInstance(){

        if(sIntructionsFragment == null) {

            Log.d(TAG, "Instructions Fragment created once");
            sIntructionsFragment = new InstructionsFragment();

        }

        return sIntructionsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.instructions_layout, null);

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
