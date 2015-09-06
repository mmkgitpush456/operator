package karavangelos.com.operator.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import karavangelos.com.operator.R;
import karavangelos.com.operator.objects.Player;

/**
 * Created by karavangelos on 9/6/15.
 */
public class OptionsDialog extends DialogFragment{

    private static final String TAG = "OptionsDialog";
    private static OptionsDialog sOptionsDialog;

    private Player player;

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


        return new AlertDialog.Builder( getActivity() )
                .setView(v)
                .create();


    }
}
