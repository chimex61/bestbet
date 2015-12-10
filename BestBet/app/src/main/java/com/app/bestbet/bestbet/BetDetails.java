package com.app.bestbet.bestbet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class BetDetails extends AppCompatActivity
implements View.OnClickListener{

    private SharedPreferences savedValues;
    private String betId;
    private BestBetDB db;
    private Bet currentBet;

    private Button btnBetWon;
    private Button btnBetLost;
    private Button btnUpdateBet;
    private Button btnDeleteBet;

    private Spinner nameSpinner;

    private EditText betDescription;
    private EditText betAmount;
    private EditText betDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_details);

        db = new BestBetDB(this);

        btnBetWon = (Button) findViewById(R.id.btnBetWon);
        btnBetLost = (Button) findViewById(R.id.btnBetLost);
        btnUpdateBet = (Button) findViewById(R.id.btnUpdateBet);
        btnDeleteBet = (Button) findViewById(R.id.btnDeleteBet);

        btnBetWon.setOnClickListener(this);
        btnBetLost.setOnClickListener(this);
        btnUpdateBet.setOnClickListener(this);
        btnDeleteBet.setOnClickListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        betId = savedValues.getString("betID", "");
        currentBet = db.getBet(betId);

        betDescription = (EditText) findViewById(R.id.txtBetDescription);
        betAmount = (EditText) findViewById(R.id.txtBetAmount);
        betDate = (EditText) findViewById(R.id.txtBetDate);

        betDescription.setText(currentBet.getDescription());
        betAmount.setText(String.valueOf(currentBet.getAmount()));
        betDate.setText(String.valueOf(currentBet.getDate()));

        ArrayList<String> names = new ArrayList<String>();
        String currentPerson = "";

        for (Person person: db.getPeople()  ) {
            names.add(person.getName());
            if (currentBet.getPersonId() == person.getId())
            {
                currentPerson = person.getName();
            }
        }

        nameSpinner = (Spinner) findViewById(R.id.personSpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        nameSpinner.setAdapter(spinnerAdapter);
        nameSpinner.setSelection(names.indexOf(currentPerson));
    }

    @Override
    public void onClick(View v) {
        Bet newBet = db.getBet(betId);
        switch (v.getId()) {
            case R.id.btnBetWon:
                Person person = db.getPerson(nameSpinner.getSelectedItem().toString());
                person.setWins(person.getWins() + 1);
                person.setGains(person.getGains() + newBet.getAmount());
                newBet.setCompleted(1);
                newBet.setWon(1);
                db.updateBet(newBet);
                db.updatePerson(person);
                Toast.makeText(this, "Bet won successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                break;
            case R.id.btnBetLost:
                Person newPerson = db.getPerson(nameSpinner.getSelectedItem().toString());
                newBet.setCompleted(1);
                newBet.setWon(0);
                newPerson.setLosses(newPerson.getLosses() + 1);
                newPerson.setGains(newPerson.getGains() - newBet.getAmount());
                db.updateBet(newBet);
                db.updatePerson(newPerson);
                Toast.makeText(this, "Bet lost successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                break;
            case R.id.btnUpdateBet:
                try {
                    newBet.setPersonId(db.getPerson(nameSpinner.getSelectedItem().toString()).getId());

                    newBet.setAmount(Integer.parseInt(String.valueOf(betAmount.getText())));
                    newBet.setDate(String.valueOf(betDate.getText()));
                    newBet.setDescription(String.valueOf(betDescription.getText()));
                    db.updateBet(newBet);
                    Toast.makeText(this, "Bet updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                }
                catch(Exception e)
                {
                    Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDeleteBet:
                db.deleteBet(newBet.getId());
                Toast.makeText(this, "Bet deleted successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bet_details, menu);
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
