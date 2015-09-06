package karavangelos.com.operator.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        setListenersForTheOptions();

        return new AlertDialog.Builder( getActivity() )
                .setView(v)
                .create();

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void defineViews(View v){

        soundRadioGroup = (RadioGroup) v.findViewById(R.id.soundRadioGroup);
        soundOnRadioButton = (RadioButton) v.findViewById(R.id.soundOnRadioButton);
        soundOffRadioButton = (RadioButton) v.findViewById(R.id.soundOffRadioButton);
        setWhichRadioButtonChecked();
    }


    private void setListenersForTheOptions(){

        soundRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.soundOnRadioButton:

                        player.saveInPreferences(getString(R.string.sound_status), getString(R.string.sound_on));
                        String soundStatus = player.retrieveSavedPreference(getString(R.string.sound_status));
                        Log.d(TAG, "The sound status is now " + soundStatus);
                        break;

                    case R.id.soundOffRadioButton:

                        player.saveInPreferences(getString(R.string.sound_status), getString(R.string.sound_off));
                        soundStatus = player.retrieveSavedPreference(getString(R.string.sound_status));
                        Log.d(TAG, "The sound status is now " + soundStatus);
                        break;
                }
            }
        });
    }

    private void setWhichRadioButtonChecked(){

        String soundstatus = player.retrieveSavedPreference(getString(R.string.sound_status));

        if(soundstatus.equals(getString(R.string.sound_on) ) ){

            soundOnRadioButton.setChecked(true);
            soundOffRadioButton.setChecked(false);

        }

        if(soundstatus.equals(getString(R.string.sound_off) ) ) {

            soundOffRadioButton.setChecked(true);
            soundOnRadioButton.setChecked(false);

        }

    }




}
