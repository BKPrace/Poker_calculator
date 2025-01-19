package com.example.poker_calculator.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

//společné karty z ruky a ze stolu, ze terých se hledá nejlepší kombinace,
//serazena od nejmensi karty po nejvetsi pro jednodusi hledani kombinaci

public class WorkingCards {
    private List<Integer> hand = new ArrayList<Integer>();
    public WorkingCards(Set<Integer> hand, Set<Integer> dealer) {
        List<Integer> list = new ArrayList<>();
        list.addAll(hand);
        list.addAll(dealer);
        Collections.sort(list);
        this.hand = list;
    }
    public List<Integer> getHand() {
        return hand;

    }
    public void addHand(Set<Integer> hand) {
        this.hand.addAll(hand);

    }
    public void setHand(Set<Integer> hand) {
        this.hand.clear();
        this.hand.addAll(hand);
    }
    public void clearHand() {
        this.hand.clear();
    }
    public WorkingCards() {}
    

}
