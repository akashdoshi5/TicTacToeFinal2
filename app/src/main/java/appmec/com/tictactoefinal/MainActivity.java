package appmec.com.tictactoefinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button button2;
    Button button3;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;

        button = (Button) findViewById(R.id.button1);
        button2= (Button) findViewById(R.id.button2);
        button3= (Button) findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                System.exit(0);

            }

        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, OnePlayer.class);
                startActivity(intent);

            }

        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, TwoPlayer.class);
                startActivity(intent);


            }

        });

        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-7860341576927713~8020931580");
        mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7860341576927713/6404597587");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());*/

        /*mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(mInterstitialAd.isLoaded())
                            MainActivity.this.mInterstitialAd.show();
                    }
                }, 200000);
            }
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });*/
    }

}
