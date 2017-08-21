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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class TwoPlayer extends AppCompatActivity {

    int[] unicode = {0x1F60A, 0x1F64C, 0x1F603, 0x1F604, 0x1F60E, 0x1F61B};
    static int index = 0;
    private int size;
    TableLayout mainBoard;
    TextView tv_turn;
    TextView score;
    TextView scorelabel;

    static int drawCount=0;
    static int xcount = 0;
    static int ycount = 0;
    static int adcount=0;
    char[][] board;
    char[][] dumyBoard;
    char turn;
    Button menu;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    final Context context = this;
    String x_name= "Player X";
    String y_name= "Player Y";

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_player);

        getSupportActionBar().setTitle("Two Player");

        final Context context = this;

        size = Integer.parseInt(getString(R.string.size_of_board));
        board = new char[size][size];
        dumyBoard = new char[size][size];

        mainBoard = (TableLayout) findViewById(R.id.mainBoard);
        tv_turn = (TextView) findViewById(R.id.turn);
        score = (TextView) findViewById(R.id.score);
        score.setText(xcount+"                   "+ycount+"                  "+drawCount);
        scorelabel=(TextView) findViewById(R.id.scorelabel);
        String player_x= "Player X";
        String player_y= "Player Y";
        if(MainActivity.PlayerX.length() != 0){
            player_x = MainActivity.PlayerX;
            int length = 8 - MainActivity.PlayerX.length();
            for(int i =0; i<=length; i++){
                    player_x = player_x + " ";
            }
        }
        if(MainActivity.PlayerY.length() != 0){
            player_y = MainActivity.PlayerY;
            int length = 8 - MainActivity.PlayerY.length();
            for(int i =0; i<=length; i++){
                if(i%2 != 0 || i ==0) {
                    player_y = " "+player_y;
                }else{
                    player_y = player_y + " ";
                }
            }
        }
        x_name=player_x.trim();
        y_name=player_y.trim();
        scorelabel.setText(player_x+"      "+player_y+"         Draw");
        menu = (Button) findViewById(R.id.mainmenu);
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

            }

        });

        resetBoard();

        for (int i = 0; i < mainBoard.getChildCount(); i++) {
            TableRow row = (TableRow) mainBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                tv.setText(R.string.none);
                tv.setOnClickListener(Move(i, j, tv));
            }
        }

        Button rstbtn = (Button) findViewById(R.id.reset);
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-7860341576927713~8020931580");
        mAdView = (AdView) findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("D8D6B049EDAB3CB2227DD36B3ED29F2D")
                .addTestDevice("25F149879ED72631F3CB460DEED0436A")
                .addTestDevice("B117F7C5611FB5503E6D2BD2CCA8C928")
                .addTestDevice("5FBF995F76CDF38832A294D8A2EE7DD0")
                .build();

        mAdView.loadAd(adRequest);

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
                resetBoard();
                if (mInterstitialAd.isLoaded() && adcount==4) {
                    mInterstitialAd.show();
                    mInterstitialAd = new InterstitialAd(TwoPlayer.this);
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

    protected void resetBoard() {
        size = Integer.parseInt(getString(R.string.size_of_board));
        board = new char[size][size];
        dumyBoard = new char[size][size];
        adcount++;
        turn = 'X';
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
        for (int i = 0; i < mainBoard.getChildCount(); i++) {
            TableRow row = (TableRow) mainBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                tv.setText(R.string.none);
                tv.setOnClickListener(Move(i, j, tv));
            }
        }
        tv_turn.setText("Turn: " + x_name);
    }

    protected int gameStatus() {

        //0 Continue
        //1 X Wins
        //2 O Wins
        //-1 Draw
        int rowX = 0, colX = 0, rowO = 0, colO = 0;
        for (int i = 0; i < size; i++) {
            if (check_Row_Equality(i, 'X'))
                return 1;
            if (check_Column_Equality(i, 'X'))
                return 1;
            if (check_Row_Equality(i, 'O'))
                return 2;
            if (check_Column_Equality(i, 'O'))
                return 2;
            if (check_Diagonal('X'))
                return 1;
            if (check_Diagonal('O'))
                return 2;
        }

        boolean boardFull = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == ' ')
                    boardFull = false;
            }
        }
        if (boardFull)
            return -1;
        else return 0;
    }

    protected boolean check_Diagonal(char player) {
        int count_Equal1 = 0, count_Equal2 = 0;
        for (int i = 0; i < size; i++)
            if (board[i][i] == player)
                count_Equal1++;
        for (int i = 0; i < size; i++)
            if (board[i][size - 1 - i] == player)
                count_Equal2++;
        if (count_Equal1 == size || count_Equal2 == size) {
            if (count_Equal1 == size) {
                for (int i = 0; i < size; i++)

                    if (board[i][i] == player)
                        dumyBoard[i][i] = 'R';
            } else {
                for (int i = 0; i < size; i++)
                    if (board[i][size - 1 - i] == player)
                        dumyBoard[i][size - 1 - i] = 'R';
            }
            return true;
        } else return false;
    }

    protected boolean check_Row_Equality(int r, char player) {
        int count_Equal = 0;
        for (int i = 0; i < size; i++) {
            if (board[r][i] == player)
                count_Equal++;
        }

        if (count_Equal == size) {
            for (int i = 0; i < size; i++) {
                if (board[r][i] == player)
                    dumyBoard[r][i] = 'R';
            }
            return true;
        } else
            return false;
    }

    protected boolean check_Column_Equality(int c, char player) {
        int count_Equal = 0;
        for (int i = 0; i < size; i++) {
            if (board[i][c] == player)
                count_Equal++;
        }
        if (count_Equal == size) {
            for (int i = 0; i < size; i++) {
                if (board[i][c] == player)
                    dumyBoard[i][c] = 'R';
            }
            return true;
        } else
            return false;
    }

    protected boolean Cell_Set(int r, int c) {
        return !(board[r][c] == ' ');
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

    View.OnClickListener Move(final int r, final int c, final TextView tv) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char previousTurn = turn;
                if (!Cell_Set(r, c)) {
                    board[r][c] = turn;
                    if (turn == 'X') {
                        tv.setText(R.string.X);
                       turn = 'O';
                    } else if (turn == 'O') {
                        tv.setText(R.string.O);
                        turn = 'X';
                    }
                    if (gameStatus() == 0) {
                        if (turn == 'X')
                            tv_turn.setText("Turn: " + x_name);
                        else if (turn == 'O')
                            tv_turn.setText("Turn: " + y_name);
                    } else if (gameStatus() == -1) {
                        tv_turn.setText("Game: Draw");
                        showDialogBox("Game: Draw");
                        drawCount++;
                        score.setText(xcount+"                   "+ycount+"                  "+drawCount);
                        stopMatch();
                    } else {
                        resultStrip(previousTurn);
                        if(previousTurn == 'X'){
                            xcount++;
                            tv_turn.setText( "'"+x_name+ "' Wins !!! ");
                            showDialogBox( "'"+x_name+ "' Wins !!! ");
                        }else if(previousTurn == 'O'){
                            ycount++;
                            tv_turn.setText( "'"+y_name+ "' Wins !!! ");
                            showDialogBox( "'"+y_name + "' Wins !!! ");
                        }
                        score.setText(xcount+"                   "+ycount+"                  "+drawCount);
                        stopMatch();
                    }
                } else {
                    showToast("Please choose a cell which is not already occupied!");
                    tv_turn.setText(tv_turn.getText());
                }
            }
        };
    }

    private void showToast(String msg){
        if(toast != null)
        {
            toast.cancel();
        }
        toast = Toast.makeText(TwoPlayer.this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showDialogBox(String textmsg) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dailog_box);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(textmsg);
        Button dialogPlayAgain = (Button) dialog.findViewById(R.id.dialogPlayAgain);
        Button dialogQuit = (Button) dialog.findViewById(R.id.dialogQuit);

        dialogPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
                if (mInterstitialAd.isLoaded() && adcount==4) {
                    mInterstitialAd.show();
                    mInterstitialAd = new InterstitialAd(TwoPlayer.this);
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

    public void resultStrip(char result) {
        for (int i = 0; i < mainBoard.getChildCount(); i++) {
            TableRow row = (TableRow) mainBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                char ch = board[i][j];
                char rsch = dumyBoard[i][j];
                if (ch == result && rsch == 'R') {
                    tv.setText(" " + getEmojiByUnicode() + " ");
                }
            }
        }
        if (index == 5)
            index = 0;
        else
            index++;
    }

    public String getEmojiByUnicode() {
        return new String(Character.toChars(unicode[index]));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(TwoPlayer.this);
            dialog.setMessage(Html.fromHtml("<b>Appmec Developers:</b><br/><br/> email : <a>akashplaystoreapps2@gmail.com</a>"));
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}
