package com.example.poker_calculator;

import com.example.poker_calculator.Model.*;
import com.example.poker_calculator.Service.AllService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    //test postupka v barvě
    @Test
    public void test(){
        Hand hand = new Hand();
        hand.setCards(9,12);
        Table table = new Table();
        table.addCard(25);
        table.addCard(13);
        table.addCard(17);
        table.addCard(8);
        table.addCard(21);
        WorkingCards workingCards = new WorkingCards();
        workingCards.addHand(hand.getCards());
        workingCards.addHand(table.getCards());

        Combination combination = new Combination();
        combination.setCards(workingCards.getHand());
        int x;
        x =combination.combination();
        System.out.println(x);
        assertEquals((10<<25) + (6<<20),x);

    }
    //postupka v barvě s esem jako 1
    @Test
    public void test2(){
        Hand hand = new Hand();
        hand.setCards(9,4);
        Table table = new Table();
        table.addCard(35);
        table.addCard(13);
        table.addCard(17);
        table.addCard(57);
        table.addCard(21);
        WorkingCards workingCards = new WorkingCards();
        workingCards.addHand(hand.getCards());
        workingCards.addHand(table.getCards());

        Combination combination = new Combination();
        combination.setCards(workingCards.getHand());
        int x;
        x =combination.combination();
        System.out.println(x);
        assertEquals((10<<25) + (5<<20),x);
    }

    //čtverice
    @Test
    public void test4(){
        Hand hand = new Hand();
        hand.setCards(8,9);
        Table table = new Table();
        table.addCard(18);
        table.addCard(10);
        table.addCard(11);
        table.addCard(25);
        table.addCard(49);
        WorkingCards workingCards = new WorkingCards();
        workingCards.addHand(hand.getCards());
        workingCards.addHand(table.getCards());

        Combination combination = new Combination();
        combination.setCards(workingCards.getHand());


        int x;
        x =combination.combination();
        System.out.println(x);
        assertEquals((9<<25) + (2<<20) + 12,x);
    }
    //fullhouse
    @Test
    public void test5(){
        Hand hand = new Hand();
        hand.setCards(8,9);
        Table table = new Table();
        table.addCard(18);
        table.addCard(10);
        table.addCard(19);
        table.addCard(14);
        table.addCard(49);
        WorkingCards workingCards = new WorkingCards();
        workingCards.addHand(hand.getCards());
        workingCards.addHand(table.getCards());

        Combination combination = new Combination();
        combination.setCards(workingCards.getHand());


        int x;
        x =combination.combination();
        System.out.println(x);
        assertEquals((8<<25) + (2<<20) + (4<<16),x);
    }
    //flash
    @Test
    public void testx(){
        Hand hand = new Hand();
        hand.setCards(8,32);
        Table table = new Table();
        table.addCard(16);
        table.addCard(10);
        table.addCard(52);
        table.addCard(44);
        table.addCard(49);
        WorkingCards workingCards = new WorkingCards();
        workingCards.addHand(hand.getCards());
        workingCards.addHand(table.getCards());

        Combination combination = new Combination();
        combination.setCards(workingCards.getHand());


        int x;
        x =combination.combination();
        System.out.println(x);
        assertEquals((7<<25) + (combination.getValue(52)<<20)
                + (combination.getValue(44)<<16) + (combination.getValue(32)<<12)
                + (combination.getValue(16)<<8) + (combination.getValue(8)<<4),x);
    }
    //postupka
    @Test
    public void test7(){
        Hand hand = new Hand();
        hand.setCards(8,15);
        Table table = new Table();
        table.addCard(16);
        table.addCard(10);
        table.addCard(19);
        table.addCard(22);
        table.addCard(26);
        WorkingCards workingCards = new WorkingCards();
        workingCards.addHand(hand.getCards());
        workingCards.addHand(table.getCards());

        Combination combination = new Combination();
        combination.setCards(workingCards.getHand());


        int x;
        x =combination.combination();
        System.out.println(x);
        assertEquals((6<<25) + (combination.getValue(26) <<20),x);
    }
    //postupka eso jako 1
    @Test
    public void test8(){
        Hand hand = new Hand();
        hand.setCards(8,15);
        Table table = new Table();
        table.addCard(16);
        table.addCard(10);
        table.addCard(19);
        table.addCard(22);
        table.addCard(59);
        WorkingCards workingCards = new WorkingCards();
        workingCards.addHand(hand.getCards());
        workingCards.addHand(table.getCards());

        Combination combination = new Combination();
        combination.setCards(workingCards.getHand());


        int x;
        x =combination.combination();
        System.out.println(x);
        assertEquals((6<<25) + (5 <<20),x);
    }

    @Test
    public void testDoplneniJedneKarty() {
        Hand hand = new Hand();
        hand.setCards(8, 15);
        Table table = new Table();
        table.addCard(16);
        table.addCard(10);
        table.addCard(19);
        table.addCard(22);

        Calculate calculate = new Calculate(hand, table);
        float x;
        x = calculate.calculate();
        System.out.println(x);
        // tento assert upozorní na změnu výsledku, ne jeho správnost. ta byla ověřena porovnáním s internetovými aplikacemi
        assertEquals(0.5461572408676147, x);
    }

    @Test
    public void testDoplneniJedneKarty2() {
        Hand hand = new Hand();
        hand.setCards(34, 15);
        Table table = new Table();
        table.addCard(39);
        table.addCard(51);
        table.addCard(19);
        table.addCard(45);

        Calculate calculate = new Calculate(hand, table);
        float x;
        x = calculate.calculate();
        System.out.println(x);
        // tento assert upozorní na změnu výsledku, ne jeho správnost. ta byla ověřena porovnáním s internetovými aplikacemi
        assertEquals(0.28849363327026367, x);
    }

    @Test
    public void testcosedeje(){
        Hand hand = new Hand();
        hand.setCards(9,10);
        Table table = new Table();
        table.addCard(17);
        table.addCard(16);
        table.addCard(22);
        table.addCard(19);
        table.addCard(59);
        WorkingCards workingCards = new WorkingCards();
        workingCards.addHand(hand.getCards());
        workingCards.addHand(table.getCards());

        Combination combination = new Combination();
        combination.setCards(workingCards.getHand());


        int x;
        x =combination.combination();
        System.out.println(x);
        assertEquals((8<<25) + (combination.getValue(16)<<20)
                + (combination.getValue(8)<<16) ,x);
    }
    @Test
    public void testporod(){
        Hand hand = new Hand();
        hand.setCards(8,15);
        Table table = new Table();
        table.addCard(16);
        table.addCard(10);
        table.addCard(19);
        table.addCard(22);
        table.addCard(52);

        Hand hand2 = new Hand();
        hand2.setCards(13,30);
        WorkingCards workingCards = new WorkingCards();
        workingCards.addHand(hand.getCards());
        workingCards.addHand(table.getCards());

        Combination combination = new Combination();
        combination.setCards(workingCards.getHand());
        WorkingCards workingCards2 = new WorkingCards();
        workingCards2.addHand(hand2.getCards());
        workingCards2.addHand(table.getCards());
        Combination combination2 = new Combination();
        combination2.setCards(workingCards2.getHand());



        int x;
        //x =combination.combination();
        int y;
        y = combination2.combination();
        //System.out.println(x);
        System.out.println(y);
        assertEquals((3<<25) + (combination.getValue(16) <<20) + (combination.getValue(52) <<16) + (combination.getValue(30) <<12) + (combination.getValue(22) <<8) ,y);
    }

    @Test
    public void testDoplneniDvouKaret() {
        Hand hand = new Hand();
        hand.setCards(8, 15);
        Table table = new Table();
        table.addCard(16);
        table.addCard(10);
        table.addCard(19);


        Calculate calculate = new Calculate(hand, table);
        float x;
        x = calculate.calculate();
        System.out.println(x);
        // tento assert upozorní na změnu výsledku, ne jeho správnost. ta byla ověřena porovnáním s internetovými aplikacemi
        assertEquals(0.5479970574378967, x);
    }

    @Test
    public void testDoplneniDvouKaret2() {
        Hand hand = new Hand();
        hand.setCards(25, 38);
        Table table = new Table();
        table.addCard(14);
        table.addCard(44);
        table.addCard(31);


        Calculate calculate = new Calculate(hand, table);
        float x;
        x = calculate.calculate();
        System.out.println(x);
        // tento assert upozorní na změnu výsledku, ne jeho správnost. ta byla ověřena porovnáním s internetovými aplikacemi
        assertEquals(0.2669404447078705, x);
    }

    @Test
    public void testDoplneniDvouKaret3() {
        Hand hand = new Hand();
        hand.setCards(45, 34);
        Table table = new Table();
        table.addCard(54);
        table.addCard(42);
        table.addCard(46);


        Calculate calculate = new Calculate(hand, table);
        float x;
        x = calculate.calculate();
        System.out.println(x);
        // tento assert upozorní na změnu výsledku, ne jeho správnost. ta byla ověřena porovnáním s internetovými aplikacemi
        assertEquals(0.7098720669746399, x);
    }


    //test trvá cca 20 min na mém zařízení

    /*
    @Test
    public void testDoplneniPetiKaret() {
        Hand hand = new Hand();
        hand.setCards(45, 34);
        Table table = new Table();



        Calculate calculate = new Calculate(hand, table);
        float x;
        x = calculate.calculate();
        System.out.println(x);
        // tento assert upozorní na změnu výsledku, ne jeho správnost. ta byla ověřena porovnáním s internetovými aplikacemi
        // ty hlásí cca 50%
        assertEquals(0.49693313241004944, x);
    } */

    @Test
    public void testKartyVsechHracu() {
        Table table = new Table();
        table.addCard(54);
        table.addCard(42);
        table.addCard(46);
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        Hand hand3 = new Hand();
        hand1.setCards(45, 34);
        hand2.setCards(28, 15);
        hand3.setCards(12, 43);
        List<Hand> hands = new ArrayList<Hand>();
        hands.add(hand1);
        hands.add(hand2);
        hands.add(hand3);

        CalculateMP calculate = new CalculateMP(hands, table);
        float[] x = calculate.calculate();
        for (float a: x) {
            System.out.print(a+ ", ");
        }
        assertEquals(0.8593577146530151, x[0]);
        assertEquals(0.011074197478592396, x[1]);
        assertEquals(0.10631228983402252, x[2]);
        assertEquals(0.023255813866853714, x[3]);



    }

    @Test
    public void testKartyVsechHracuBezVylozeni() {
        Table table = new Table();

        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        Hand hand3 = new Hand();
        hand1.setCards(45, 34);
        hand2.setCards(28, 15);
        hand3.setCards(12, 43);
        List<Hand> hands = new ArrayList<Hand>();
        hands.add(hand1);
        hands.add(hand2);
        hands.add(hand3);

        CalculateMP calculate = new CalculateMP(hands, table);
        float[] x = calculate.calculate();
        for (float a: x) {
            System.out.print(a+ ", ");
        }
        assertEquals(0.5658338665962219, x[0]);
        assertEquals(0.16529953479766846, x[1]);
        assertEquals(0.226392924785614, x[2]);
        assertEquals(0.04247370362281799, x[3]);



    }

    @Test
    public void testcalculateVsechnyKartyZname() {
        Hand hand = new Hand();
        hand.setCards(45, 34);
        Table table = new Table();
        table.addCard(54);
        table.addCard(42);
        table.addCard(46);
        table.addCard(48);
        table.addCard(12);

        Calculate calculate = new Calculate(hand, table);
        float x;
        x = calculate.calculate();
        System.out.println(x);
        // tento assert upozorní na změnu výsledku, ne jeho správnost. ta byla ověřena porovnáním s internetovými aplikacemi
        assertEquals(0.37979796528816223, x);
    }


    //tento test je chybny, snad ho nezapomenu smazat pred odevzdanim,
    // cela trida calculateMP nejspis nebude ve finalni verzi, a pokud ano tak jen pro moje poznamky a nikdy nebude vyuzita
    @Test
    public void testcalculateMPJedenHrac() {
        Table table = new Table();

        Hand hand1 = new Hand();
        hand1.setCards(45, 34);
        table.addCard(54);
        table.addCard(42);
        table.addCard(46);
        table.addCard(48);
        table.addCard(12);

        List<Hand> hands = new ArrayList<Hand>();
        hands.add(hand1);


        CalculateMP calculate = new CalculateMP(hands, table);
        float[] x = calculate.calculate();
        for (float a: x) {
            System.out.print(a+ ", ");
        }
        assertEquals(1.0, x[0]);




    }
/*
//trva pres minutu
    @Test
    public void testDoplneniDvouKaret2MP() {
        // identicke k testDoplneniDvouKaret2 ale pouziva finalni metodu
        Hand hand = new Hand();
        hand.setCards(25, 38);
        Table table = new Table();
        table.addCard(14);
        table.addCard(44);
        table.addCard(31);

        List<Hand> hands = new ArrayList<Hand>();
        hands.add(hand);
        CalculateMPWithOthers calculate = new CalculateMPWithOthers(hands, table, 2);

        float[] x = calculate.calculate();
        System.out.println(Arrays.toString(x));
        // tento assert upozorní na změnu výsledku, ne jeho správnost. ta byla ověřena porovnáním s internetovými aplikacemi
        assertEquals(0.2669404447078705, x[0]);
    }*/

    @Test
    public void testcalculateMPJeden() {
        Table table = new Table();

        Hand hand1 = new Hand();
        hand1.setCards(45, 34);
        table.addCard(54);
        table.addCard(42);
        table.addCard(46);
        table.addCard(48);
        table.addCard(12);

        List<Hand> hands = new ArrayList<Hand>();
        hands.add(hand1);


        CalculateMPWithOthers calculate = new CalculateMPWithOthers(hands, table, 2);
        float[] x = calculate.calculate();
        for (float a: x) {
            System.out.print(a+ ", ");
        }
        assertEquals(0.13649898767471313, x[0]);
    }

    @Test
    public void testCtvericeViceHracu() {
        Hand hand = new Hand();
        hand.setCards(8, 9);
        Table table = new Table();
        table.addCard(52);
        table.addCard(10);
        table.addCard(11);
        table.addCard(48);
        table.addCard(40);

        List<Hand> hands = new ArrayList<Hand>();
        hands.add(hand);


        CalculateMPWithOthers calculate = new CalculateMPWithOthers(hands, table,3);
        float[] x = calculate.calculate();
        for (float a: x) {
            System.out.print(a+ ", ");
        }
        assertEquals(0.9939393401145935, x[0]);
    }

    @Test
    public void testKartyVsechHracuMP() {
        Table table = new Table();
        table.addCard(54);
        table.addCard(42);
        table.addCard(46);
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        Hand hand3 = new Hand();
        hand1.setCards(45, 34);
        hand2.setCards(28, 15);
        hand3.setCards(12, 43);
        List<Hand> hands = new ArrayList<Hand>();
        hands.add(hand1);
        hands.add(hand2);
        hands.add(hand3);

        CalculateMPWithOthers calculate = new CalculateMPWithOthers(hands, table,0);
        float[] x = calculate.calculate();
        for (float a: x) {
            System.out.print(a+ ", ");
        }
        assertEquals(0.8593577146530151, x[0]);
        assertEquals(0.011074197478592396, x[1]);
        assertEquals(0.10631228983402252, x[2]);
        assertEquals(0.023255813866853714, x[3]);
    }

    @Test
    public void testKartyVsechHracuService() {
        AllService allService = new AllService();
        allService.getHandsFromSite().set(0, 45);
        allService.getHandsFromSite().set(1, 34);
        allService.getHandsFromSite().set(2, 28);
        allService.getHandsFromSite().set(3, 15);
        allService.getHandsFromSite().set(4, 12);
        allService.getHandsFromSite().set(5, 43);

        allService.getTableFromSite().set(0, 54);
        allService.getTableFromSite().set(1, 42);
        allService.getTableFromSite().set(2, 46);
        allService.setPlayers(0);
        allService.calculate();
        for (float a : allService.getResult()) {
            System.out.print(a + ", ");

        }
        assertEquals(0.8593577146530151, allService.getResult()[0]);
        assertEquals(0.011074197478592396, allService.getResult()[1]);
        assertEquals(0.10631228983402252, allService.getResult()[2]);
        assertEquals(0.023255813866853714, allService.getResult()[3]);

    }

    @Test
    public void testKartyVsechHracuServiceNulyVKartach() {
        AllService allService = new AllService();
        allService.getHandsFromSite().set(0, 45);
        allService.getHandsFromSite().set(1, 34);
        allService.getHandsFromSite().set(4, 28);
        allService.getHandsFromSite().set(5, 15);
        allService.getHandsFromSite().set(8, 12);
        allService.getHandsFromSite().set(9, 43);

        allService.getTableFromSite().set(0, 54);
        allService.getTableFromSite().set(1, 42);
        allService.getTableFromSite().set(3, 46);
        allService.setPlayers(1);
        allService.calculate();
        for (float a : allService.getResult()) {
            System.out.print(a + ", ");

        }
        assertEquals(0.8593577146530151, allService.getResult()[0]);
        assertEquals(0.011074197478592396, allService.getResult()[1]);
        assertEquals(0.10631228983402252, allService.getResult()[2]);
        assertEquals(0.023255813866853714, allService.getResult()[3]);

    }

    @Test
    public void NoElementError() {
        AllService allService = new AllService();
        allService.calculate();
        for (float a : allService.getResult()) {
            System.out.print(a + ", ");

        }
    }
    @Test
    public void testcalculateRandom() {
        Table table = new Table();

        Hand hand1 = new Hand();
        hand1.setCards(45, 34);
        table.addCard(54);
        table.addCard(42);
        table.addCard(46);
        table.addCard(48);
        table.addCard(12);

        List<Hand> hands = new ArrayList<Hand>();
        hands.add(hand1);


        RandomShuffle calculate = new RandomShuffle(hands, table, 2);
        float[] x = calculate.calculate();
        for (float a: x) {
            System.out.print(a+ ", ");
        }
        //exact numbers should be 0.13649899, 0.037057173, 0.82644385
        //first run                 0.13702, 0.08197, 0.78101
        // second run               0.136184, 0.081454, 0.782362,

    }

    @Test
    public void randomShuffle2() {
        Table table = new Table();
        table.addCard(54);
        table.addCard(42);
        table.addCard(46);
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        Hand hand3 = new Hand();
        hand1.setCards(45, 34);
        hand2.setCards(28, 15);
        hand3.setCards(12, 43);
        List<Hand> hands = new ArrayList<Hand>();
        hands.add(hand1);
        hands.add(hand2);
        hands.add(hand3);

        RandomShuffle calculate = new RandomShuffle(hands, table, 0);
        float[] x = calculate.calculate();
        for (float a: x) {
            System.out.print(a+ ", ");
        }
            // precise result should be 0.8593577, 0.0110741975, 0.10631229, 0.023255814, 0.0,
            // first run is             0.859449,  0.010669,     0.106848,   0.023034, 0.0,
            // second run is            0.859507,  0.010871,     0.106386,   0.023236, 0.0,


    }


    @Test
    public void testDoplneniPetiKaretRandom() {
        Hand hand = new Hand();
        hand.setCards(45, 34);
        Table table = new Table();


        List<Hand> hands = new ArrayList<Hand>();
        hands.add(hand);

        RandomShuffle calculate = new RandomShuffle(hands, table, 1);

        float[] x = calculate.calculate();
        for (float a: x) {
            System.out.print(a+ ", ");
        }
        // should be 0.49693313241004944
        // got       0.496302

    }


}


