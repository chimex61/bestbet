package com.app.bestbet.bestbet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddPlayer extends AppCompatActivity
implements View.OnClickListener{

        private Button addNewPlayerButton;
        private Button backButton;
        private TextView playerNameTextView;
        BestBetDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        //Set listeners on buttons
        playerNameTextView = (TextView) findViewById(R.id.txtPlayerName);
        addNewPlayerButton = (Button) findViewById(R.id.btnAddNewPlayer);
        addNewPlayerButton.setOnClickListener(this);
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        // get db object
        db = new BestBetDB(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddNewPlayer:
                String newPlayerName = playerNameTextView.getText().toString();
                //Creates a player with the given name and inserts player into db
                Person person = new Person(0, newPlayerName, 0, 0, 0);
                if (!newPlayerName.trim().equals("")) {
                    long insertId = db.insertPerson(person);

                    if (insertId > 0) {
                        Toast.makeText(this, newPlayerName + " added successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Can't add player with a blank name",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.backButton:
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_player, menu);
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
