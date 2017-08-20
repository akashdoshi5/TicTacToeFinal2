package appmec.com.tictactoefinal;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button button2;
    Button button3;
    Button button4;
    final Context context = this;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    public static String PlayerX="Player X";
    public static String PlayerY="Player Y";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Home");

        final Context context = this;

        button = (Button) findViewById(R.id.button1);
        button2= (Button) findViewById(R.id.button2);
        button3= (Button) findViewById(R.id.button3);
        button4= (Button) findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener() {

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

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialogBox();
            }

        });



        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-7860341576927713~8020931580");
        mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("D8D6B049EDAB3CB2227DD36B3ED29F2D")
                .addTestDevice("25F149879ED72631F3CB460DEED0436A")
                .addTestDevice("B117F7C5611FB5503E6D2BD2CCA8C928")
                .addTestDevice("5FBF995F76CDF38832A294D8A2EE7DD0")
                .build();
        mAdView.loadAd(adRequest);
    }

    private void showDialogBox() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.player_dialog_box);
        dialog.setCanceledOnTouchOutside(false);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        Button setPlayerName = (Button) dialog.findViewById(R.id.setPlayerName);
        Button dialogQuit = (Button) dialog.findViewById(R.id.dialogQuit);

        setPlayerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView playerX = (TextView) dialog.findViewById(R.id.playerX);
                TextView playerO = (TextView) dialog.findViewById(R.id.playerO);

                MainActivity.PlayerX=playerX.getText().toString();
                MainActivity.PlayerY=playerO.getText().toString();
                dialog.dismiss();
            }
        });

        dialogQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}
