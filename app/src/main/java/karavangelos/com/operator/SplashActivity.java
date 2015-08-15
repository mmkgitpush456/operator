package karavangelos.com.operator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by karavangelos on 8/15/15.
 */
public class SplashActivity extends Activity{


    private TextView splashTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        splashTextView = (TextView) findViewById(R.id.splashTextView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/hemi.ttf");
        splashTextView.setTypeface(typeface);


        runSplashSequence();
    }






    private void runSplashSequence(){

        Thread t = new Thread(){

            @Override
            public void run() {
               super.run();

                try{

                    Thread.sleep(3000);

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();

                }catch(Exception e){

                }



            }
        };

        t.start();
    }
}
