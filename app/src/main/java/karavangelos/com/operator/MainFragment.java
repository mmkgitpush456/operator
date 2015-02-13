package karavangelos.com.operator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by karavangelos on 2/12/15.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MainFragemnt";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        return v;

    }//end onCreate view





    @Override
    public void onClick(View v) {

    }
}
