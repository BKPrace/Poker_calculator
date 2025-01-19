package com.example.poker_calculator.Model;

import java.util.HashSet;
import java.util.Set;

//karty vyložené na stůl, ulozene v mnozine

public class Table {
    private Set<Integer> cards = new HashSet<>();

    public Set<Integer> getCards() {
        return cards;
    }
    public void setCards(Set<Integer> cardss) {
        this.cards.clear();
        this.cards.addAll(cardss);

    }

    public void addCard(int card) {
        cards.add(card);
    }
    public int removeCard(int card) {
        cards.remove(card);
        return card;
    }

}
