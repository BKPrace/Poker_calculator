package com.example.poker_calculator.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



//jde o nepouzivanou tridu nikde krome testu,
// byla upravena v CalculateMPWithOthers, která je aktuální
//bude pozdeji smazana az se ujistim ze z ni nic nepotrebuji


// pocita se znalosti karet nekterych dalsich hracu
//CalculateMultiplePlayers


public class CalculateMP {
    private List<Integer> cards = new ArrayList<>(AllCards.getList());
    private List<Hand> hand;
    private Table table = new Table();
    private Hand newHand = new Hand();
    private Table newTable = new Table();
    private int count = 0;
    private int[] win;
    public List<Integer> getCards() {
        return cards;
    }

    public List<Hand> getHand() {
        return hand;
    }

    public void setHand(List<Hand> hand) {
        this.hand = hand;
    }

    public CalculateMP() {
    }
    public CalculateMP(List<Hand> hand, Table table) {
        this.hand = hand;
        this.table = table;
    }

    public float[] calculate() {
        //kolik je potreba generovat karet
        int length = 5- table.getCards().size();
        win = new int[hand.size() +1];
        if (table.getCards().size()>5) {
            System.out.println("moc karet na stole");
        }

        // vyradime karty v ruce a na stole z celkovych karet
        for (int i= 0; i < cards.size(); i++){
            if (table.getCards().contains(cards.get(i))) {
                cards.remove(i);
                i--;
            } else {
                for (int a = 0; a< hand.size(); a++){
                    if (hand.get(a).getCards().contains(cards.get(i)) ) {
                        cards.remove(i);
                        i--;
                    }
                }

            }
        }
        //jdeme generovat mozne vylozeni na stul, podle poctu chybejicich karet
        if (length ==0) {
            newTable.setCards(table.getCards());
            calculateHands();
            count++;
        } else {
        for (int i =0; i< cards.size(); i++) {
            if (length == 1) {
                newTable.setCards(table.getCards());
                newTable.addCard(cards.get(i));
                calculateHands();
                count++;
            } else {
                for (int j = i+1; j < cards.size(); j++) {
                    //print pro urceni rychosti behu kodu behem testu
                    //System.out.println(i*100 + j);
                    if (length == 2) {

                        newTable.setCards(table.getCards());
                        newTable.addCard(cards.get(j));
                        newTable.addCard(cards.get(i));
                        calculateHands();
                        count++;
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
                                    calculateHands();
                                    count++;

                                }
                            }

                        }
                    }

                }
            }
        }
        }
        //prepocteme vysledky na procenta
        float[] result = new float[win.length];
        for (int a =0; a < win.length; a++) {
            result[a] = (float) win[a]/count;
        }

        return result;
    }
    //dostane 5 vylozenych karet a porovnava karty v ruce s kartami v ostatnich rukou
    public void calculateHands() {
        ArrayList<Integer> val = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            val.add(0);
        }
        Combination comb = new Combination();
        WorkingCards workingCards = new WorkingCards();
        for (int a = 0; a < hand.size(); a++) {
            workingCards.setHand(hand.get(a).getCards());
            workingCards.addHand(newTable.getCards());
            comb.setCards(workingCards.getHand() );
            val.set(a,comb.combination());
        }
        int max = Collections.max(val);
        if (Collections.frequency(val, max) ==1) {
            win[val.indexOf(max)]++;
        } else {
            win[win.length-1] ++;
        }






    }
}
