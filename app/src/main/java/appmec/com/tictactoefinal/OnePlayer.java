package appmec.com.tictactoefinal;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class OnePlayer extends AppCompatActivity {
    final Context context = this;
    int c[][];
    static int index=0;
    static int drawCount=0;
    static int xcount = 0;
    static int ycount = 0;
    static int adcount=0;
    int i, j, k = 0;
    Button b[][];
    TextView textView;
    TextView score;
    AI ai;
    int[] unicode = {0x1F60A,0x1F64C,0x1F603,0x1F604,0x1F60E,0x1F61B};
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    TableLayout mainBoard;
    Button menu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_player);

        getSupportActionBar().setTitle("One Player");

        setBoard();
        mainBoard = (TableLayout) findViewById(R.id.mainBoard);
        score = (TextView) findViewById(R.id.score);
        score.setText(xcount+"              "+ycount+"               "+drawCount);
        Button rstbtn = (Button) findViewById(R.id.reset);

        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-7860341576927713~8020931580");
        mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("D8D6B049EDAB3CB2227DD36B3ED29F2D")
                .addTestDevice("25F149879ED72631F3CB460DEED0436A")
                .addTestDevice("B117F7C5611FB5503E6D2BD2CCA8C928")
                .addTestDevice("5FBF995F76CDF38832A294D8A2EE7DD0")
                .build();
        mAdView.loadAd(adRequest);
        final Context context = this;
        menu = (Button) findViewById(R.id.mainmenu);
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

            }

        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7860341576927713/6404597587");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("D8D6B049EDAB3CB2227DD36B3ED29F2D")
                .addTestDevice("25F149879ED72631F3CB460DEED0436A")
                .addTestDevice("B117F7C5611FB5503E6D2BD2CCA8C928")
                .addTestDevice("5FBF995F76CDF38832A294D8A2EE7DD0")
                .build());


        rstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBoard();
                if (mInterstitialAd.isLoaded() && adcount==4) {
                    mInterstitialAd.show();
                    mInterstitialAd = new InterstitialAd(OnePlayer.this);
                    mInterstitialAd.setAdUnitId("ca-app-pub-7860341576927713/6404597587");
                    mInterstitialAd.loadAd(new AdRequest.Builder()
                            .addTestDevice("D8D6B049EDAB3CB2227DD36B3ED29F2D")
                            .addTestDevice("25F149879ED72631F3CB460DEED0436A")
                            .addTestDevice("B117F7C5611FB5503E6D2BD2CCA8C928")
                            .addTestDevice("5FBF995F76CDF38832A294D8A2EE7DD0")
                            .build());
                    adcount=0;
                }
            }
        });

    }
    protected void stopMatch() {
        for (int i = 0; i < mainBoard.getChildCount(); i++) {
            TableRow row = (TableRow) mainBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                tv.setOnClickListener(null);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.add("About Us");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(OnePlayer.this);
        dialog.setMessage(Html.fromHtml("<b>Appmec Developers:</b><br/><br/> email : <a>akashplaystoreapps2@gmail.com</a>"));
        dialog.show();
        return true;
    }


    // Set up the game board.
    private void setBoard() {
        ai = new AI();
        b = new Button[4][4];
        c = new int[4][4];
        adcount++;

        textView = (TextView) findViewById(R.id.dialogue);

        b[1][3] = (Button) findViewById(R.id.one);
        b[1][2] = (Button) findViewById(R.id.two);
        b[1][1] = (Button) findViewById(R.id.three);


        b[2][3] = (Button) findViewById(R.id.four);
        b[2][2] = (Button) findViewById(R.id.five);
        b[2][1] = (Button) findViewById(R.id.six);


        b[3][3] = (Button) findViewById(R.id.seven);
        b[3][2] = (Button) findViewById(R.id.eight);
        b[3][1] = (Button) findViewById(R.id.nine);

        for (i = 1; i <= 3; i++) {
            for (j = 1; j <= 3; j++)
                c[i][j] = 2;
        }

        textView.setText("Start Game!!!");

        for (i = 1; i <= 3; i++) {
            for (j = 1; j <= 3; j++) {
                b[i][j].setOnClickListener(new MyClickListener(i, j));
                if(!b[i][j].isEnabled()) {
                    b[i][j].setText("       ");
                    b[i][j].setEnabled(true);
                }
            }
        }
    }


    class MyClickListener implements View.OnClickListener {
        int x;
        int y;


        public MyClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public void onClick(View view) {
            if (b[x][y].isEnabled()) {
                b[x][y].setEnabled(false);
                b[x][y].setText("  O  ");
                c[x][y] = 0;
                textView.setText("");
                if (!checkBoard()) {
                    ai.takeTurn();
                }
            }
        }
    }

    private void showDialogBox(String textmsg) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dailog_box);
   //     dialog.setCanceledOnTouchOutside(false);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(textmsg);
        Button dialogPlayAgain = (Button) dialog.findViewById(R.id.dialogPlayAgain);
        Button dialogQuit = (Button) dialog.findViewById(R.id.dialogQuit);

        dialogPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBoard();
                if (mInterstitialAd.isLoaded() && adcount==4) {
                    mInterstitialAd.show();
                    mInterstitialAd = new InterstitialAd(OnePlayer.this);
                    mInterstitialAd.setAdUnitId("ca-app-pub-7860341576927713/6404597587");
                    mInterstitialAd.loadAd(new AdRequest.Builder()
                            .addTestDevice("D8D6B049EDAB3CB2227DD36B3ED29F2D")
                            .addTestDevice("25F149879ED72631F3CB460DEED0436A")
                            .addTestDevice("B117F7C5611FB5503E6D2BD2CCA8C928")
                            .addTestDevice("5FBF995F76CDF38832A294D8A2EE7DD0")
                            .build());
                    adcount=0;
                }
                dialog.dismiss();
            }
        });

        dialogQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    private class AI {
        public void takeTurn() {
            //Condition For Comp to Win
            if(c[1][1]==2 &&
                    ((c[1][2]==1 && c[1][3]==1) ||
                            (c[2][2]==1 && c[3][3]==1) ||
                            (c[2][1]==1 && c[3][1]==1))) {
                markSquare(1,1);
            } else if (c[1][2]==2 &&
                    ((c[2][2]==1 && c[3][2]==1) ||
                            (c[1][1]==1 && c[1][3]==1))) {
                markSquare(1,2);
            } else if(c[1][3]==2 &&
                    ((c[1][1]==1 && c[1][2]==1) ||
                            (c[3][1]==1 && c[2][2]==1) ||
                            (c[2][3]==1 && c[3][3]==1))) {
                markSquare(1,3);
            } else if(c[2][1]==2 &&
                    ((c[2][2]==1 && c[2][3]==1) ||
                            (c[1][1]==1 && c[3][1]==1))){
                markSquare(2,1);
            } else if(c[2][2]==2 &&
                    ((c[1][1]==1 && c[3][3]==1) ||
                            (c[1][2]==1 && c[3][2]==1) ||
                            (c[3][1]==1 && c[1][3]==1) ||
                            (c[2][1]==1 && c[2][3]==1))) {
                markSquare(2,2);
            } else if(c[2][3]==2 &&
                    ((c[2][1]==1 && c[2][2]==1) ||
                            (c[1][3]==1 && c[3][3]==1))) {
                markSquare(2,3);
            } else if(c[3][1]==2 &&
                    ((c[1][1]==1 && c[2][1]==1) ||
                            (c[3][2]==1 && c[3][3]==1) ||
                            (c[2][2]==1 && c[1][3]==1))){
                markSquare(3,1);
            } else if(c[3][2]==2 &&
                    ((c[1][2]==1 && c[2][2]==1) ||
                            (c[3][1]==1 && c[3][3]==1))) {
                markSquare(3,2);
            }else if( c[3][3]==2 &&
                    ((c[1][1]==1 && c[2][2]==1) ||
                            (c[1][3]==1 && c[2][3]==1) ||
                            (c[3][1]==1 && c[3][2]==1))) {
                markSquare(3,3);
            }
            else if(c[1][1]==2 &&//condtition for comp to restrict win
                    ((c[1][2]==0 && c[1][3]==0) ||
                            (c[2][2]==0 && c[3][3]==0) ||
                            (c[2][1]==0 && c[3][1]==0))) {
                markSquare(1,1);
            } else if (c[1][2]==2 &&
                    ((c[2][2]==0 && c[3][2]==0) ||
                            (c[1][1]==0 && c[1][3]==0))) {
                markSquare(1,2);
            } else if(c[1][3]==2 &&
                    ((c[1][1]==0 && c[1][2]==0) ||
                            (c[3][1]==0 && c[2][2]==0) ||
                            (c[2][3]==0 && c[3][3]==0))) {
                markSquare(1,3);
            } else if(c[2][1]==2 &&
                    ((c[2][2]==0 && c[2][3]==0) ||
                            (c[1][1]==0 && c[3][1]==0))){
                markSquare(2,1);
            } else if(c[2][2]==2 &&
                    ((c[1][1]==0 && c[3][3]==0) ||
                            (c[1][2]==0 && c[3][2]==0) ||
                            (c[3][1]==0 && c[1][3]==0) ||
                            (c[2][1]==0 && c[2][3]==0))) {
                markSquare(2,2);
            } else if(c[2][3]==2 &&
                    ((c[2][1]==0 && c[2][2]==0) ||
                            (c[1][3]==0 && c[3][3]==0))) {
                markSquare(2,3);
            } else if(c[3][1]==2 &&
                    ((c[1][1]==0 && c[2][1]==0) ||
                            (c[3][2]==0 && c[3][3]==0) ||
                            (c[2][2]==0 && c[1][3]==0))){
                markSquare(3,1);
            } else if(c[3][2]==2 &&
                    ((c[1][2]==0 && c[2][2]==0) ||
                            (c[3][1]==0 && c[3][3]==0))) {
                markSquare(3,2);
            }else if( c[3][3]==2 &&
                    ((c[1][1]==0 && c[2][2]==0) ||
                            (c[1][3]==0 && c[2][3]==0) ||
                            (c[3][1]==0 && c[3][2]==0))) {
                markSquare(3,3);
            }else {
                Random rand = new Random();

                int a = rand.nextInt(4);
                int b = rand.nextInt(4);
                while(a==0 || b==0 || c[a][b]!=2) {
                    a = rand.nextInt(4);
                    b = rand.nextInt(4);
                }
                markSquare(a,b);
            }
        }


        private void markSquare(int x, int y) {
            b[x][y].setEnabled(false);
            b[x][y].setText("  X  ");
            c[x][y] = 1;
            checkBoard();
        }
    }

    private boolean checkBoard() {
        boolean gameOver = false;
        if ((c[1][1] == 0 && c[2][2] == 0 && c[3][3] == 0)){
            c[1][3] = 9;
            c[2][2] = 9;
            c[3][1] = 9;
            gameOver = isGameOverComputerLoss();
        }else if ((c[1][3] == 0 && c[2][2] == 0 && c[3][1] == 0)){
            c[1][1] = 9;
            c[2][2] = 9;
            c[3][3] = 9;
            gameOver = isGameOverComputerLoss();
        }else if ((c[1][2] == 0 && c[2][2] == 0 && c[3][2] == 0)){
            c[1][2] = 9;
            c[2][2] = 9;
            c[3][2] = 9;
            gameOver = isGameOverComputerLoss();
        }else if ((c[1][3] == 0 && c[2][3] == 0 && c[3][3] == 0)){
            c[1][1] = 9;
            c[2][1] = 9;
            c[3][1] = 9;
            gameOver = isGameOverComputerLoss();
        }else if ((c[1][1] == 0 && c[1][2] == 0 && c[1][3] == 0)){
            c[1][3] = 9;
            c[1][2] = 9;
            c[1][1] = 9;
            gameOver = isGameOverComputerLoss();
        }else if ((c[2][1] == 0 && c[2][2] == 0 && c[2][3] == 0)){
            c[2][3] = 9;
            c[2][2] = 9;
            c[2][1] = 9;
            gameOver = isGameOverComputerLoss();
        }else if ((c[3][1] == 0 && c[3][2] == 0 && c[3][3] == 0)){
            c[3][3] = 9;
            c[3][2] = 9;
            c[3][1] = 9;
            gameOver = isGameOverComputerLoss();
        }else if ((c[1][1] == 0 && c[2][1] == 0 && c[3][1] == 0)){
            c[1][3] = 9;
            c[2][3] = 9;
            c[3][3] = 9;
            gameOver = isGameOverComputerLoss();

        } else if ((c[1][1] == 1 && c[2][2] == 1 && c[3][3] == 1)){
            c[1][3] = 9;
            c[2][2] = 9;
            c[3][1] = 9;
            gameOver = isGameOverComputerWin();}
          else if ((c[1][3] == 1 && c[2][2] == 1 && c[3][1] == 1)){
            c[1][1] = 9;
            c[2][2] = 9;
            c[3][3] = 9;
            gameOver = isGameOverComputerWin();}
          else if ((c[1][2] == 1 && c[2][2] == 1 && c[3][2] == 1)){
            c[1][2] = 9;
            c[2][2] = 9;
            c[3][2] = 9;
            gameOver = isGameOverComputerWin();}
          else if ((c[1][3] == 1 && c[2][3] == 1 && c[3][3] == 1)){
            c[1][1] = 9;
            c[2][1] = 9;
            c[3][1] = 9;
            gameOver = isGameOverComputerWin();}
          else if ((c[1][1] == 1 && c[1][2] == 1 && c[1][3] == 1)){
            c[1][3] = 9;
            c[1][2] = 9;
            c[1][1] = 9;
            gameOver = isGameOverComputerWin();}
          else if ((c[2][1] == 1 && c[2][2] == 1 && c[2][3] == 1)){
            c[2][3] = 9;
            c[2][2] = 9;
            c[2][1] = 9;
            gameOver = isGameOverComputerWin();}
          else if ((c[3][1] == 1 && c[3][2] == 1 && c[3][3] == 1)){
            c[3][3] = 9;
            c[3][2] = 9;
            c[3][1] = 9;
            gameOver = isGameOverComputerWin();}
          else if ((c[1][1] == 1 && c[2][1] == 1 && c[3][1] == 1)) {
            c[1][3] = 9;
            c[2][3] = 9;
            c[3][3] = 9;
            gameOver = isGameOverComputerWin();
        } else {
            boolean empty = false;
            for(i=1; i<=3; i++) {
                for(j=1; j<=3; j++) {
                    if(c[i][j]==2) {
                        empty = true;
                        break;
                    }
                }
            }
            if(!empty) {
                gameOver = true;
                textView.setText("Game over. It's a draw!");
                showDialogBox("Game over. It's a draw!");
                drawCount++;
                score.setText(xcount+"              "+ycount+"               "+drawCount);
            }
        }
        return gameOver;
    }

    private boolean isGameOverComputerLoss() {
        boolean gameOver;
        ycount++;
        score.setText(xcount+"              "+ycount+"               "+drawCount);
        textView.setText("Game over. You win!");
        showDialogBox("Game over. You win!");
        resultStrip();
        gameOver = true;
        stopMatch();
        return gameOver;
    }

    private boolean isGameOverComputerWin() {
        boolean gameOver;
        xcount++;
        score.setText(xcount+"              "+ycount+"               "+drawCount);
        textView.setText("Game over. You lost!");
        showDialogBox("Game over. You lost!");
        gameOver = true;
        resultStripForComputer();
        stopMatch();
        return gameOver;
    }

    public void resultStrip(){
        for (int i = 1; i < 4; i++) {
            TableRow row = (TableRow) mainBoard.getChildAt(i-1);
            for (int j = 1; j < 4; j++) {
                Button tv = (Button) row.getChildAt(j-1);
                if(c[i][j] == 9){
                    tv.setText(" "+getEmojiByUnicode()+" ");
                }
            }
        }
        if(index==5)
            index=0;
        else
            index++;
    }

    public void resultStripForComputer() {
        for (int i = 1; i < 4; i++) {
            TableRow row = (TableRow) mainBoard.getChildAt(i - 1);
            for (int j = 1; j < 4; j++) {
                Button tv = (Button) row.getChildAt(j - 1);
                if (c[i][j] == 9) {
                    tv.setText(" " + getEmojiByUnicodeForComputer() + " ");
                }
            }
        }
    }

    public String getEmojiByUnicode(){

        return new String(Character.toChars(unicode[index]));

    }
    public String getEmojiByUnicodeForComputer(){

        return new String(Character.toChars(0x1F623));

    }

}
