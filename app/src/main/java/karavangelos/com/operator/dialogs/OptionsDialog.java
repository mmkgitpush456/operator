package karavangelos.com.operator.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import karavangelos.com.operator.R;
import karavangelos.com.operator.objects.Player;

/**
 * Created by karavangelos on 9/6/15.
 */
public class OptionsDialog extends DialogFragment{

    private static final String TAG = "OptionsDialog";
    private static OptionsDialog sOptionsDialog;

    private Player player;

    private RadioGroup soundRadioGroup;
    private RadioButton soundOnRadioButton;
    private RadioButton soundOffRadioButton;

    private RadioGroup difficultyRadioGroup;
    private RadioButton easyRadioButton;
    private RadioButton mediumRadioButton;
    private RadioButton hardRadioButton;

    private TextView optionsTextView;
    private TextView soundTextView;
    private TextView difficultyTextView;

    public OptionsDialog(){



    }

    public static OptionsDialog newInstance(){

        if(sOptionsDialog == null){

            sOptionsDialog = new OptionsDialog();
        }

        return sOptionsDialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        player = Player.newInstance( getActivity() );

        View v = getActivity().getLayoutInflater()																		//create the view made from dialog_date.xml
                .inflate(R.layout.dialog_options, null);

        defineViews(v);
        setListenersForTheSoundOptions();
        setListenersForDifficultyOptions();

        return new AlertDialog.Builder( getActivity() )
                .setView(v)
                .create();

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void defineViews(View v){

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/hemi.ttf");
        soundRadioGroup = (RadioGroup) v.findViewById(R.id.soundRadioGroup);
        soundOnRadioButton = (RadioButton) v.findViewById(R.id.soundOnRadioButton);
        soundOffRadioButton = (RadioButton) v.findViewById(R.id.soundOffRadioButton);

        difficultyRadioGroup = (RadioGroup) v.findViewById(R.id.difficultyRadioGroup);
        easyRadioButton = (RadioButton) v.findViewById(R.id.easyRadioButton);
        mediumRadioButton = (RadioButton) v.findViewById(R.id.mediumRadioButton);
        hardRadioButton = (RadioButton) v.findViewById(R.id.hardRadioButton);

        optionsTextView = (TextView) v.findViewById(R.id.optionsTextView);
        soundTextView = (TextView) v.findViewById(R.id.soundTextView);
        difficultyTextView = (TextView) v.findViewById(R.id.difficultyTextView);


        soundOnRadioButton.setTypeface(typeface);
        soundOffRadioButton.setTypeface(typeface);

        easyRadioButton.setTypeface(typeface);
        mediumRadioButton.setTypeface(typeface);
        hardRadioButton.setTypeface(typeface);


        optionsTextView.setTypeface(typeface);
        soundTextView.setTypeface(typeface);
        difficultyTextView.setTypeface(typeface);

        setWhichRadioButtonChecked();
    }


    private void setListenersForTheSoundOptions(){

        soundRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.soundOnRadioButton:

                        player.saveInPreferences(getString(R.string.sound_status), getString(R.string.sound_on));
                      //  String soundStatus = player.retrieveSavedPreference(getString(R.string.sound_status));
                      //  Log.d(TAG, "The sound status is now " + soundStatus);
                        break;

                    case R.id.soundOffRadioButton:

                        player.saveInPreferences(getString(R.string.sound_status), getString(R.string.sound_off));
                      //  soundStatus = player.retrieveSavedPreference(getString(R.string.sound_status));
                      //  Log.d(TAG, "The sound status is now " + soundStatus);
                        break;
                }
            }
        });
    }


    private void setListenersForDifficultyOptions(){

        difficultyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.easyRadioButton:

                        player.saveInPreferences(getString(R.string.difficulty_status), getString(R.string.diffiiculty_easy));
                        break;

                    case R.id.mediumRadioButton:


                        player.saveInPreferences(getString(R.string.difficulty_status), getString(R.string.difficulty_medium));
                        break;


                    case R.id.hardRadioButton:

                        player.saveInPreferences(getString(R.string.difficulty_status), getString(R.string.difficulty_hard));
                        break;
                }

            }
        });
    }



    private void setWhichRadioButtonChecked(){

        String soundstatus = player.retrieveSavedPreference(getString(R.string.sound_status));
        String difficultyStatus = player.retrieveSavedPreference(getString(R.string.difficulty_status));
        setSoundRadio(soundstatus);
        setDifficultyRadio(difficultyStatus);


    }

    private void setSoundRadio(String soundstatus){

        if(soundstatus.equals(getString(R.string.sound_on) ) ){

            soundOnRadioButton.setChecked(true);
            soundOffRadioButton.setChecked(false);

        }
        if(soundstatus.equals(getString(R.string.sound_off) ) ) {

            soundOffRadioButton.setChecked(true);
            soundOnRadioButton.setChecked(false);

        }
    }

    private void setDifficultyRadio(String difficultyStatus){

        if(difficultyStatus.equals(getActivity().getString(R.string.diffiiculty_easy) ) ){
            easyRadioButton.setChecked(true);
            mediumRadioButton.setChecked(false);
            hardRadioButton.setChecked(false);
        }

        if(difficultyStatus.equals(getActivity().getString(R.string.difficulty_medium) ) ){
            mediumRadioButton.setChecked(true);
            easyRadioButton.setChecked(false);
            hardRadioButton.setChecked(false);
        }

        if(difficultyStatus.equals(getActivity().getString(R.string.difficulty_hard) ) ){
            hardRadioButton.setChecked(true);
            easyRadioButton.setChecked(false);
            mediumRadioButton.setChecked(false);
        }

    }




}
