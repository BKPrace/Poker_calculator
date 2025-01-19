package com.example.poker_calculator.Model;

import java.util.ArrayList;
import java.util.List;


//jde o nepouzivanou tridu nikde krome testu,
// byla upravena v calculateMP a pozdeji v CalculateMPWithOthers, která je aktuální
//bude pozdeji smazana az se ujistim ze z ni nic nepotrebuji

public class Calculate {
    private List<Integer> cards = new ArrayList<>(AllCards.getList());
    private Hand hand = new Hand();
    private Table table = new Table();
    private Hand newHand = new Hand();
    private Table newTable = new Table();
    private int count = 0;
    public List<Integer> getCards() {
        return cards;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Calculate() {
    }
    public Calculate(Hand hand, Table table) {
        this.hand = hand;
        this.table = table;
    }

    public float calculate() {



        //jde o nepouzivanou tridu nikde krome testu,
        // byla upravena v calculateMP a pozdeji v CalculateMPWithOthers, která je aktuální

        //kolik je potreba generovat karet
        int length = 5- table.getCards().size();
        int win = 0;
        if (table.getCards().size()>5) {
            System.out.println("moc karet na stole");
        }

        // vyradime karty v ruce a na stole z celkovych karet
        for (int i= 0; i < cards.size(); i++){
            if (hand.getCards().contains(cards.get(i)) || table.getCards().contains(cards.get(i))) {
                cards.remove(i);
                i--;
            }
        }
        //jdeme generovat mozne vylozeni na stul
        if (length ==0){
            newTable.setCards(table.getCards());
            win+= calculateHands();
        } else {
        for (int i =0; i< cards.size(); i++) {
            if (length == 1) {
                newTable.setCards(table.getCards());
                newTable.addCard(cards.get(i));
                win += calculateHands();

            } else {
                for (int j = i+1; j < cards.size(); j++) {
                    System.out.println(i*100 + j);
                    if (length == 2) {

                        newTable.setCards(table.getCards());
                        newTable.addCard(cards.get(j));
                        newTable.addCard(cards.get(i));
                        win += calculateHands();
                    } else {
                        for (int k = j+1; k <cards.size() ; k++) {
                            for (int l = k+1; l <cards.size(); l++) {
                                for (int m = l+1; m <cards.size(); m++) {
                                    newTable.setCards(table.getCards());
                                    newTable.addCard(cards.get(i));
                                    newTable.addCard(cards.get(j));
                                    newTable.addCard(cards.get(k));
                                    newTable.addCard(cards.get(l));
                                    newTable.addCard(cards.get(m));
                                    win += calculateHands();

                                }
                            }

                        }
                    }

                }
            }
        }
        }

        return (float) win/count;
    }
    //dostane 5 vylozenych karet a porovnava karty v ruce s kartami v ostatnich rukou
    public int calculateHands() {

        int val;
        int win=0;

        Combination comb = new Combination();
        WorkingCards workingCards = new WorkingCards();
        workingCards.addHand(hand.getCards());
        workingCards.addHand(newTable.getCards());
        comb.setCards(workingCards.getHand() );
        val = comb.combination();

        //pocitame mozne ruce souperu

        for (int i = 0; i < cards.size(); i++) {
            if (newTable.getCards().contains(cards.get(i) )) {
                continue;
            }
            for (int j = i+1; j < cards.size(); j++) {
                if (newTable.getCards().contains(cards.get(j))) {
                    continue;
                }
                newHand.setCards(cards.get(i), cards.get(j));
                workingCards.setHand(newTable.getCards());
                workingCards.addHand(newHand.getCards());
                comb.setCards(workingCards.getHand());
                if (val > comb.combination()) {
                    win++;
                }
                count++;
            }

        }
        return win;

    }
}
