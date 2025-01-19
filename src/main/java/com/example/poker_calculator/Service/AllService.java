package com.example.poker_calculator.Service;

import com.example.poker_calculator.Model.*;
import org.springframework.stereotype.Service;

import java.util.*;


//service ktera prenasi karty z formulare a controlleru, zarizuje spravny vypocet a vraceni vysledku

@Service
public class AllService {

    //error message
    private String eMessage = "";

    //mame nastaveno maximalne 5 hracu na zadani karet ne kvuli vykonu ale kvuli pripadne zacpanosti webove stranky
    //v pripade zajmu neni velky problem rozsirit
    private List<Integer> handsFromSite = new ArrayList<>(List.of(0, 0, 0,0,0,0,0,0,0,0));
    private List<Integer> tableFromSite = new ArrayList<>(List.of(0,0,0,0,0));
    private List<Hand> hand = new ArrayList<Hand>();
    private Table table = new Table();
    private float[] result;

    //pocet generovanych hracu. je jich klidne mozne generovat 10 ale postupne to spomaluje vypocet.
    // momentalne je ve formulari max mozny 5
    private int players =0;

    public AllService() {
    }
    public AllService(List<Integer> handsFromSite, List<Integer> tableFromSite, float[] result, int players) {
        this.handsFromSite = handsFromSite;
        this.tableFromSite = tableFromSite;
        this.result = result;
        this.players = players;
    }

    public List<Integer> getHandsFromSite() {
        return handsFromSite;
    }
    public String resultToString() {
        return Arrays.toString(result);
    }

    public String geteMessage() {
        return eMessage;
    }

    public void setHandsFromSite(List<Integer> handsFromSite) {
        this.handsFromSite = handsFromSite;
    }

    public List<Integer> getTableFromSite() {
        return tableFromSite;
    }

    public void setTableFromSite(List<Integer> tableFromSite) {
        this.tableFromSite = tableFromSite;
    }

    public List<Hand> getHand() {
        return hand;
    }
    public void setHand(List<Hand> hand) {
        this.hand = hand;
    }
    public void addHand(Hand hand) {
        this.hand.add(hand);
    }
    public void calculate(){


            //resi duplicity
        for (int i : handsFromSite) {
            if (i!=0 && (Collections.frequency( handsFromSite, i) >1|| tableFromSite.contains(i))) {
                eMessage = "Same card was used multiple times";
                return;
            }
        }
        //resi duplicity
        for (int i : tableFromSite) {
            if( i!=0 && Collections.frequency(tableFromSite,i)>1){
                eMessage = "Same card was used multiple times";
                return;
            }
        }

        hand = new ArrayList<Hand>();
        table = new Table();
        for (int i = 0; i < handsFromSite.size(); i=i+2) {
            Hand hand1 = new Hand();
            hand1.setCards(handsFromSite.get(i), handsFromSite.get(i+1));
            if (!hand1.getCards().contains(0)) {
                hand.add(hand1);
            }
        }
        if (hand.isEmpty()) {
            eMessage = "No cards in hand / incomplete hand  ";
            return;
        }
        //zpracuje karty z web formulare
        for (int i = 0; i < tableFromSite.size(); i++) {
            if (!(tableFromSite.get(i)==0)) {
                table.addCard(tableFromSite.get(i));
            }
        }
        eMessage = "";
        //nahodne rozdani pro moc vykonostne narocne vypocty,
        // byla zvolena hranice ktera snad zabrani delsimu vypoctu nez 10 vterin (na mem zarizeni)
        if (players >2 || (5-(table.getCards().size()) + players) >4) {

            //System.out.println("random shuffle");
            eMessage = "Because of computing requirements of this calculation, posibly taking minutes or hours, " +
                    "expect to be given a simulated " +
                    "result based on half a million shuffles, which might not be accurate";
            RandomShuffle calc = new RandomShuffle(hand,table,players);
            float[] x = calc.calculate();
            result =x ;

        } else {
            //jinak dojde k presnemu vypoctu
            CalculateMPWithOthers calc = new CalculateMPWithOthers(hand, table, players);
            float[] x = calc.calculate();
            result =x ;
        }






    }
        // funkce pro formular na prevod karet na text
    public String cardToString(int card) {
        if (card ==0) {
            return "Choose a card";
        }
        String value="";
        if ((card >>2) <11) {
            value = Integer.toString(card >>2);
        } else if (card >>2 ==11) {
            value = "J";
        } else if (card >>2 ==12) {
            value = "Q";
        } else if (card >>2 ==13) {
            value = "K";
        } else if (card >>2 ==14) {
            value = "A";
        }
        String house = "";
        if ((card & 3) ==0 ) {
            house = "♠";
        } else if ((card & 3) ==1 ) {
            house = "♥";
        } else if ((card & 3) ==2 ) {
            house = "♦";
        } else if ((card & 3) ==3 ) {
            house = "♣";
        }
        return (value + " " + house);

    }

        //promazani formulare
    public void clear(){
        hand = new ArrayList<Hand>();
        table = new Table();
        result = null;
        players = 0;
        handsFromSite = new ArrayList<>(List.of(0, 0, 0,0,0,0,0,0,0,0));
        tableFromSite = new ArrayList<>(List.of(0,0,0,0,0));
        eMessage = "";


    }

    public Set<Integer> getTable() {
        return table.getCards();
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public float[] getResult() {
        return result;
    }

    public void setResult(float[] result) {
        this.result = result;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }






}
