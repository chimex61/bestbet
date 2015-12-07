package com.app.bestbet.bestbet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CreateBet extends AppCompatActivity
implements View.OnClickListener{

    private BestBetDB db;
    private Button cancelButton;
    private Button createBetButton;

    private Spinner personSpinner;
    private TextView betDescription;
    private TextView betAmount;
    private TextView betDate;
    ArrayList<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bet);

        db = new BestBetDB(this);
        cancelButton = (Button) findViewById(R.id.btnCancel);
        createBetButton = (Button) findViewById(R.id.btnCreateBet);
        personSpinner = (Spinner) findViewById(R.id.personSpinner);
        betDescription = (TextView) findViewById(R.id.txtBetDescription);
        betAmount = (TextView) findViewById(R.id.txtBetAmount);
        betDate = (TextView) findViewById(R.id.txtBetDate);

        cancelButton.setOnClickListener(this);
        createBetButton.setOnClickListener(this);

        ArrayList<String> names = new ArrayList<String>();
        people = db.getPeople();

        for (Person person: people) {
            names.add(person.getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        personSpinner.setAdapter(spinnerAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateBet:
                Bet bet = new Bet();


                Person betPerson = people.get(Integer.parseInt(personSpinner.getSelectedItem().toString()));

                bet.setPersonId(betPerson.getId());
                bet.setDescription(String.valueOf(betDescription.getText()));
                bet.setDate(String.valueOf(betDate.getText()));
                bet.setAmount(Integer.parseInt(String.valueOf(betAmount.getText())));
                bet.setCompleted(0);
                bet.setWon(0);
                db.insertBet(bet);

                break;
            case R.id.btnCancel:
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_bet, menu);
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
