package karavangelos.com.operator.fragments;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.InputStream;

import karavangelos.com.operator.R;

/**
 * Created by karavangelos on 2/14/15.
 */
public class InstructionsFragment extends Fragment{

    private static final String TAG = "InstructionsFragment";
    private static InstructionsFragment sIntructionsFragment;
    private View v;
    private TextView instructionsTextView;
    private TextView instructionsTitleTextView;


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
        instructionsTextView = (TextView) v.findViewById(R.id.instructionsTextView);
        instructionsTitleTextView = (TextView) v.findViewById(R.id.instructionsTitleTextView);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/hemi.ttf");
        instructionsTextView.setTypeface(typeface);
        instructionsTitleTextView.setTypeface(typeface);

        populateTextView(R.raw.instructions, instructionsTextView);


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


    ////////////////////////////////////////////////////////////////////////////////////////////////


    //method used to populate the text view with the requested text file.
    //the method works by declaring an input-stream that obtains one of the TXT files from the raw folder.
    //the input stream then converts the text to bytes by performing the stream's native available method.
    //the stream then reads every line and gets converted into a string with the text view's set text method.
    private void populateTextView(int whichFile, TextView textView){

        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(whichFile);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);

            textView.setText(new String(b));

        } catch (Exception e) {

            Log.e(TAG, "parsing error caught at " + e);
        }

    }

}
