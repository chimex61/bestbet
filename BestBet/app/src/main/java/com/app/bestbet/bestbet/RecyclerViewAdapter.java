package com.app.bestbet.bestbet;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mcouture-cc on 12/5/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView betId;
        TextView playerName;
        TextView betDescription;

        RecyclerViewHolder(View itemView) {
            //Sets items for recyclerview from player_item layout
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            betId = (TextView) itemView.findViewById(R.id.betId);
            playerName = (TextView) itemView.findViewById(R.id.personName);
            betDescription = (TextView) itemView.findViewById(R.id.betDescription);
        }
    }

    ArrayList<Bet> bets;
    ArrayList<Person> people;
    RecyclerViewAdapter(ArrayList<Bet> bets, ArrayList<Person> people){
        this.bets = bets;
        this.people = people;
    }

    @Override
    public int getItemCount() {
        return bets.size();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate
                (R.layout.bet_item, viewGroup, false);
        //Creates a card
        RecyclerViewHolder rvh = new RecyclerViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder playerViewHolder, int i) {

        playerViewHolder.betId.setText(String.valueOf(bets.get(i).getId()));

        //Fixed name version
        String name = "";
        for (Person person: people )
        {
            if (person.getId() == bets.get(i).getPersonId())
            {
                name = person.getName();
                break;
            }
        }
        playerViewHolder.playerName.setText(name);
        //This used to work but now it doesnt
        //playerViewHolder.playerName.setText(String.valueOf(people.get(bets.get(i).getPersonId()).getName()));
        //playerViewHolder.playerName.setText(String.valueOf(bets.get(i).getPersonId()));
        playerViewHolder.betDescription.setText(bets.get(i).getDescription());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }
}
