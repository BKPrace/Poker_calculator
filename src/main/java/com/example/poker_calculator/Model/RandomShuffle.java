package com.example.poker_calculator.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
    // nahodne rozdani karet pro pripad moc vysoke narocnosti na vykonost. jde o slozenu pravdepodobnost a
    // tudiz by se to mohlo pocitat roky pri dost vysokem poctu hracu
public class RandomShuffle {
    private List<Integer> cards = new ArrayList<>(AllCards.getList());
    private List<Hand> hand;
    private Table table = new Table();
    private Hand newHand = new Hand();
    private Table newTable = new Table();
    private int count = 0;
    private int[] win;
    private int players;
    private float[] result;
    private int length ;
    public RandomShuffle() {}
    public RandomShuffle( List<Hand> hand, Table table ,int players ) {
        this.hand = hand;
        this.table = table;

        //pocet hracu co se musi generovat
        this.players = players;
        //pocet karet co je potreba generovat na stul
        this.length = 5- table.getCards().size();

    }

    public float[] calculate() {
        //pocet karet na stul
        this.length = 5- table.getCards().size();
        // pocet vitestvi pro kazdeho zadaneho hrace plus jedno pro remizu a jedno pro generovany hrace navic dohromady
        win = new int[hand.size() +2 ];
        //odstranime pouzite karty at muzeme generovat
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

        //nahodne naplnime karty a uvidime kdo vyhraje
        Random rand = new Random();

        //momentalne je nastaveno 500 000 rozdani,
        // netrva to az moc dlouho a vysledky jsou srovnatelne s milionem rozdani
        // vseobecne jsou mimo ani ne o jedno procento od vysledku generovanych za pomoci CalculateMPWithOthers
        for (int i= 0; i < 500000; i++){

            newTable = new Table();
            newTable.setCards(table.getCards());
            List<Hand> newHands = new ArrayList<>(hand);


            ArrayList<Integer> used = new ArrayList<>();
            for (int a = 0; a < length + players; a++){
                //naplnime stul
                if (a < length) {
                    int card = rand.nextInt(cards.size());
                    while (used.contains(card)) {
                        card = rand.nextInt(cards.size());
                    }
                    used.add(card);
                    newTable.addCard(cards.get(card));

                    //vytvorime hrace
                } else {
                    int card = rand.nextInt(cards.size());
                    while (used.contains(card)) {
                        card = rand.nextInt(cards.size());
                    }
                    used.add(card);
                    int card2 = rand.nextInt(cards.size());
                    while (used.contains(card2)) {
                        card2 = rand.nextInt(cards.size());
                    }
                    used.add(card2);
                    Hand hand1 = new Hand();
                    hand1.setCards(cards.get(card), cards.get(card2));

                    newHands.add(hand1);

                }
            }
            //nechame CalculateMPWithOthers vyhodnotit kdo vyhral,
            // ale s plnym stolem a poctem hracu takze dojde pouze ke zkoumani teto jedne kombinace
            // bez jakehokoliv dalsiho generovani
            CalculateMPWithOthers calc = new CalculateMPWithOthers(newHands, newTable,0 );
            calc.calculate();
            //najdeme nejsilnejsi ruku
            int winner =0;
            for ( int a =0; a<calc.getResult().length; a++) {
                // vzdy bude jasne vraceno jestli pro nase generovani vyhral jeden hrac nebo je remiza
                if (calc.getResult()[a]==1.0) {
                    winner = a;
                }
            }
            //remiza je ulozena druha od konce, posledni je vzdy sance ze vyhral generovany hrac
            if (winner == calc.getResult().length-2) {
                win[win.length-2]++;
                //v pripade vyhry  libovolneho generovaneho hrace ukladame vsechny na jedno misto
            } else if (winner> hand.size()-1) {
                win[win.length-1]++;

                //vitez je zadany hrac
            } else {
                win[winner] ++;
            }

            count++;


        }
        //prepocet na procenta
        result = new float[win.length];
        for (int a =0; a < win.length; a++) {
            result[a] = (float) win[a]/count;
        }

        return result;

    }




}
