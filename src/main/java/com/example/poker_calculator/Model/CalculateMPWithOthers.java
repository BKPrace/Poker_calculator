package com.example.poker_calculator.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// vytvari mozne kombinace karet a nasledne je odesila na "posouzeni kombinace" classe Combination
//CalculateMultiplePlayers

public class CalculateMPWithOthers {

    private List<Integer> cards = new ArrayList<>(AllCards.getList());

    //input
    private List<Hand> hand;
    //input
    private Table table = new Table();

    //slouzi k nahromadeni simulovanych karet s kartami rozdanymi
    private Hand newHand = new Hand();
    private Table newTable = new Table();

    //celkovy pocet zkousenych kombinaci, slouzi k prevodu na procenta
    private int count = 0;

    //zaznamenava celkovy pocet vyhernich kombinaci
    private int[] win;

    //pocet hracu ke generaci
    private int players;
    //prepinac pokud je volan tridou randomShuffle a nevi ze pocita s generovanymi hraci.
    // ovlivnuje vystup pro vyhru
    private int randomShuffle =0;

    //vysledny return v procentech
    private float[] result;
    public List<Integer> getCards() {
        return cards;
    }

    public List<Hand> getHand() {
        return hand;
    }
    public int getPlayers() {
        return players;
    }
    public Table getTable() {
        return table;
    }
    public float[] getResult() {
        return result;
    }


    public void setHand(List<Hand> hand) {
        this.hand = hand;
    }

    public CalculateMPWithOthers() {
    }
    public CalculateMPWithOthers(List<Hand> hand, Table table, int players) {
        //seznam karet hracu plus zname karty na stole a pocet hracu ktere musime nasimulovat
        this.hand = hand;
        this.table = table;
        //
        // soucasne max 2 hraci z vykonostnich duvodu, vyssi cislo resi trida RandomShuffle
        this.players = players;
    }

    public CalculateMPWithOthers(List<Hand> hand, Table table, int players, int randomShuffle) {
        this.hand = hand;
        this.table = table;
        this.players = players;
        this.randomShuffle = randomShuffle;
    }

    public float[] calculate() {

        //kolik je potreba generovat karet na stul
        int length = 5- table.getCards().size();

        //jedno misto pro remizu, jedno misto pro moznost ze nevyhraje zadny ze znamych hracu ale generovany hrac
        // prvni mista win jsou podle poradi rukou v hand
        win = new int[hand.size() +2];

        //odstranime karty ktere jiz jsou napevno rozdane a nebudou se menit ze seznamu generovatelnych karet.
        // if je zde pro pripad, ze je tato funkce volana z classy RandomShuffle,
        // ktera se jiz ujistila ze zde nejsou zadne duplikace a neni jiz potreba zadne karty generovat
        if (players>0 || length>0) {
            for (int i= 0; i < cards.size(); i++){
                if (table.getCards().contains(cards.get(i))) {
                    cards.remove(i);
                    i--;
                } else {
                    for (int a = 0; a< hand.size(); a++){
                        if (hand.get(a).getCards().contains(cards.get(i)) ) {
                            cards.remove(i);
                                i--;
                                break;
                        }
                    }
                }
            }
        }
        //jdeme generovat mozne vylozeni na stul
        //length = pocet chybejicich karet na stole
        if (length ==0) {
            //vytvarime simulovany stul
            newTable.setCards(table.getCards());
            calculateHands();
        } else{
        for (int i =0; i< cards.size(); i++) {
            if (length == 1) {
                newTable.setCards(table.getCards());
                newTable.addCard(cards.get(i));
                calculateHands();
            } else {
                for (int j = i+1; j < cards.size(); j++) {
                    if (length == 2) {
                        newTable.setCards(table.getCards());
                        newTable.addCard(cards.get(j));
                        newTable.addCard(cards.get(i));
                        calculateHands();
                    } else {
                        // neni doporuceno, trva zatracene dlouho
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
                                }
                            }

                        }
                    }

                }
            }
        }
        }
        //prepocet na procenta vyhranych kombinaci z celkovych kombinaci
        result = new float[win.length];
        for (int a =0; a < win.length; a++) {
            result[a] = (float) win[a]/count;
        }

        return result;
    }
    //dostane 5 vylozenych karet a porovnava karty v ruce s kartami v ostatnich rukou
    public void calculateHands() {
        //uklada silu kombinace interpretovanou jako int
        ArrayList<Integer> val = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            val.add(0);
        }
        Combination comb = new Combination();
        WorkingCards workingCards = new WorkingCards();
        //zjistime silu jednotlivych zadanych rukou s jiz drive generovanym stolem
        for (int a = 0; a < hand.size(); a++) {
            workingCards.setHand(hand.get(a).getCards());
            workingCards.addHand(newTable.getCards());
            comb.setCards(workingCards.getHand() );
            val.set(a,comb.combination());
        }
        //nejsilnejsi kombinace
        int max = Collections.max(val);
        //hledame u vsech moznych kombinaci simulovanych rukou ktere vsechny porazime.
        // bohuzel nejde o jednoduchou pravdepodobnost a tak se musi zkoumat kazda mozna kombinace,
        // a to i klidne trojic rukou karet. z vykonostnich duvodu zde maximalne 2 dvojice

        //zajima nas pouze jestli vyhrala nejaka zadana ruka ci ruka generovana, proto je zde prepinac aby se
        // negenerovaly dalsi ruce kdyz uz stejne prohral
        ArrayList<Boolean> better = new ArrayList<>();
        ArrayList<Boolean> draw = new ArrayList<>();
        for (int i =0; i< players;i++) {
            better.add(false);
            draw.add(false);
        }
        int value;
        //pocet generovanych hracu
        if (players>0) {
            for (int i = 0; i < cards.size() - players; i++) {
                if (newTable.getCards().contains(cards.get(i) )) {
                    continue;
                }

                for (int j = i + 1; j < cards.size(); j++) {
                    if (newTable.getCards().contains(cards.get(j) )) {
                        continue;
                    }
                    //urci prvni moznou generovanou ruku. pokud je silnejsi nez ruce dane,
                    // další kombinace se nepocitaji ale jen pricitaji k vitezstvim generovanych rukou
                    newHand.setCards(cards.get(i), cards.get(j));
                    workingCards.setHand(newTable.getCards());
                    workingCards.addHand(newHand.getCards());
                    comb.setCards(workingCards.getHand());
                    value = comb.combination();
                    if (value>max) {
                        better.set(0,true);
                    } else if (value==max) {
                       draw.set(0,true);
                    }
                    if (players > 1) {
                        //pocita druheho hrace a vsechny kombinace s prvnim hracem
                        for (int k = i + 1; k < cards.size() - (players - 1); k++) {
                            if (newTable.getCards().contains(cards.get(k) )) {
                                continue;
                            }
                            if (k == j) {
                                continue;
                            }
                            for (int l = k + 1; l < cards.size(); l++) {
                                if (newTable.getCards().contains(cards.get(l) )) {
                                    continue;
                                }
                                if (l == j) {
                                    continue;
                                }
                                // pokud je vygenerovana silnejsi kombinace nez jaka je na rukach zadanych hracem,
                                // neni smysl dale zkoumat silnejsi
                                if (!better.contains(true)) {
                                    newHand.setCards(cards.get(k), cards.get(l));
                                    workingCards.setHand(newTable.getCards());
                                    workingCards.addHand(newHand.getCards());
                                    comb.setCards(workingCards.getHand());
                                    value = comb.combination();
                                    if (value>max) {
                                        better.set(1, true);
                                    } else if (value==max) {
                                        draw.set(1, true);
                                    }


                                }

                                    if (better.contains(true)) {
                                        win[win.length-1]++;
                                    } else if (draw.contains(true)) {
                                        win[hand.size()] ++;
                                    } else {
                                        if (Collections.frequency(val, max) ==1) {
                                            win[val.indexOf(max)]++;
                                        } else {
                                            win[hand.size()] ++;
                                        }
                                    }

                                    count++;

                                better.set(1,false);
                                draw.set(1,false);
                            }
                        }
                    } else {
                        if (better.contains(true)) {
                            win[win.length-1]++;
                        } else if (draw.contains(true)) {
                            win[hand.size()] ++;
                        } else {
                            if (Collections.frequency(val, max) ==1) {
                                win[val.indexOf(max)]++;
                            } else {
                                win[hand.size()] ++;
                            }
                        }
                        count++;
                    }
                    better.set(0,false);
                    draw.set(0,false);


                }
            }
        } else {
            //v pripade ze zadne hrace negenerujeme tak se pouze najde nejsilnejsi ze vsech
            // a ji se zada vitezstvi
            if (Collections.frequency(val, max) ==1) {
                win[val.indexOf(max)]++;
            } else {
                //vyhral generovany hrac tridy randomShuffle
                if (val.indexOf(max) > (hand.size()-1) -randomShuffle) {
                    win[hand.size()+1] ++;
                } else {
                    win[hand.size()] ++;
                }
            }
            count++;
        }










    }
}
