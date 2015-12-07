package com.app.bestbet.bestbet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity
    implements View.OnClickListener{

        private Button activeBetsButton;
        private Button createBetButton;
        private Button addPersonButton;
        private Button leaderboardsButton;
        private Button pastBetsButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_menu);

            //get references to buttons
            activeBetsButton = (Button) findViewById(R.id.btnActiveBets);
            createBetButton = (Button) findViewById(R.id.btnCreateBet);
            addPersonButton = (Button) findViewById(R.id.btnAddPlayer);
            leaderboardsButton = (Button) findViewById(R.id.btnLeaderboards);
            pastBetsButton = (Button) findViewById(R.id.btnPastBets);

            //set listeners on buttons
            activeBetsButton.setOnClickListener(this);
            createBetButton.setOnClickListener(this);
            addPersonButton.setOnClickListener(this);
            leaderboardsButton.setOnClickListener(this);
            pastBetsButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnActiveBets:
                    startActivity(new Intent(getApplicationContext(), ActiveBets.class));
                    break;
                case R.id.btnCreateBet:
                    startActivity(new Intent(getApplicationContext(), CreateBet.class));
                    break;
                case R.id.btnAddPlayer:
                    startActivity(new Intent(getApplicationContext(), AddPlayer.class));
                    break;
                case R.id.btnLeaderboards:
                    startActivity(new Intent(getApplicationContext(), Leaderboards.class));
                    break;
                case R.id.btnPastBets:
                    startActivity(new Intent(getApplicationContext(), PastBets.class));
                    break;
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
