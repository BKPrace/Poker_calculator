package com.example.poker_calculator.Model;

import java.util.HashSet;
import java.util.Set;
    //Zakladni ruka libovolneho hrace ulozena v Setu.
public class Hand {
    private Set<Integer> cards = new HashSet<>();

    public Set<Integer> getCards() {
        return cards;
    }
    public void setCards(int a, int b) {
        cards.clear();
        cards.add(a);
        cards.add(b);

    }
    public Hand(Set<Integer> cards) {
        this.cards = cards;
    }
    public Hand() {}

}

